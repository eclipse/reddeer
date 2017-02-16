/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.junit.internal.runner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.junit.extensionpoint.IAfterTest;
import org.jboss.reddeer.junit.extensionpoint.IBeforeTest;
import org.jboss.reddeer.junit.internal.requirement.Requirements;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.model.FrameworkField;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.junit.runners.parameterized.TestWithParameters;

/**
 * This runner represents one test class with one specific parameter.
 * 
 * @author rhopp
 *
 */

public class ParameterizedRequirementsRunner extends RequirementsRunner {
	
    private final Object[] parameters;

    private final String name;

	private static final Logger log = Logger.getLogger(ParameterizedRequirementsRunner.class);

	/**
	 * Instantiates a new parameterized requirements runner.
	 *
	 * @param test the test
	 * @throws InitializationError the initialization error
	 */
	public ParameterizedRequirementsRunner(TestWithParameters test) throws InitializationError {
		super(test.getTestClass().getJavaClass());
        parameters = test.getParameters().toArray(
                new Object[test.getParameters().size()]);
        name = test.getName();
	}
	
	@Override
	protected Statement withBeforeClasses(Statement statement) {
		log.debug("Injecting fulfilled requirements into static fields of test class");
		requirementsInjector.inject(getTestClass().getJavaClass(), requirements);
		return super.withBeforeClasses(statement);
	}
	
	/**
	 * Sets the config id.
	 *
	 * @param configId the new config id
	 */
	public void setConfigId(String configId) {
		this.configId = configId;
	}

	/**
	 * Sets the requirements.
	 *
	 * @param requirements the new requirements
	 */
	public void setRequirements(Requirements requirements) {
		this.requirements = requirements;
	}

	/**
	 * Sets the run listeners.
	 *
	 * @param runListeners the new run listeners
	 */
	public void setRunListeners(RunListener[] runListeners) {
		this.runListeners = runListeners;
	}
	
	/**
	 * Sets the before test extensions.
	 *
	 * @param beforeTestsExtensions the new before test extensions
	 */
	public void setBeforeTestExtensions(List<IBeforeTest> beforeTestsExtensions){
		this.beforeTestExtensions = beforeTestsExtensions;
	}
	
	/**
	 * Sets the after test extensions.
	 *
	 * @param afterTestsExtensions the new after test extensions
	 */
	public void setAfterTestExtensions(List<IAfterTest> afterTestsExtensions){
		this.afterTestExtensions = afterTestsExtensions;
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.junit.internal.runner.RequirementsRunner#getName()
	 */
	@Override
	protected String getName() {
		return super.getName() + " " + name;
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.junit.internal.runner.RequirementsRunner#createTest()
	 */
	@Override
	public Object createTest() throws Exception {
		Object testInstance;
        if (fieldsAreAnnotated()) {
            testInstance = createTestUsingFieldInjection();
        } else {
            testInstance = createTestUsingConstructorInjection();
        }
        log.debug("Injecting fulfilled requirements into test instance");
		requirementsInjector.inject(testInstance, requirements);
        return testInstance;
	}

    /* (non-Javadoc)
     * @see org.jboss.reddeer.junit.internal.runner.RequirementsRunner#testName(org.junit.runners.model.FrameworkMethod)
     */
    @Override
    protected String testName(FrameworkMethod method) {
        return method.getName() + " " +configId+ " " +name;
    }
	
	//Following code was copied from BlockJunit4ClassWithParameters class from JUnit 4.12
	
    private Object createTestUsingConstructorInjection() throws Exception {
        return getTestClass().getOnlyConstructor().newInstance(parameters);
    }

    private Object createTestUsingFieldInjection() throws Exception {
        List<FrameworkField> annotatedFieldsByParameter = getAnnotatedFieldsByParameter();
        if (annotatedFieldsByParameter.size() != parameters.length) {
            throw new Exception(
                    "Wrong number of parameters and @Parameter fields."
                            + " @Parameter fields counted: "
                            + annotatedFieldsByParameter.size()
                            + ", available parameters: " + parameters.length
                            + ".");
        }
        Object testClassInstance = getTestClass().getJavaClass().newInstance();
        for (FrameworkField each : annotatedFieldsByParameter) {
            Field field = each.getField();
            Parameter annotation = field.getAnnotation(Parameter.class);
            int index = annotation.value();
            try {
                field.set(testClassInstance, parameters[index]);
            } catch (IllegalArgumentException iare) {
                throw new Exception(getTestClass().getName()
                        + ": Trying to set " + field.getName()
                        + " with the value " + parameters[index]
                        + " that is not the right type ("
                        + parameters[index].getClass().getSimpleName()
                        + " instead of " + field.getType().getSimpleName()
                        + ").", iare);
            }
        }
        return testClassInstance;
    }

    /* (non-Javadoc)
     * @see org.junit.runners.BlockJUnit4ClassRunner#validateConstructor(java.util.List)
     */
    @Override
    protected void validateConstructor(List<Throwable> errors) {
        validateOnlyOneConstructor(errors);
        if (fieldsAreAnnotated()) {
            validateZeroArgConstructor(errors);
        }
    }

    /* (non-Javadoc)
     * @see org.junit.runners.BlockJUnit4ClassRunner#validateFields(java.util.List)
     */
    @Override
    protected void validateFields(List<Throwable> errors) {
        super.validateFields(errors);
        if (fieldsAreAnnotated()) {
            List<FrameworkField> annotatedFieldsByParameter = getAnnotatedFieldsByParameter();
            int[] usedIndices = new int[annotatedFieldsByParameter.size()];
            for (FrameworkField each : annotatedFieldsByParameter) {
                int index = each.getField().getAnnotation(Parameter.class)
                        .value();
                if (index < 0 || index > annotatedFieldsByParameter.size() - 1) {
                    errors.add(new Exception("Invalid @Parameter value: "
                            + index + ". @Parameter fields counted: "
                            + annotatedFieldsByParameter.size()
                            + ". Please use an index between 0 and "
                            + (annotatedFieldsByParameter.size() - 1) + "."));
                } else {
                    usedIndices[index]++;
                }
            }
            for (int index = 0; index < usedIndices.length; index++) {
                int numberOfUse = usedIndices[index];
                if (numberOfUse == 0) {
                    errors.add(new Exception("@Parameter(" + index
                            + ") is never used."));
                } else if (numberOfUse > 1) {
                    errors.add(new Exception("@Parameter(" + index
                            + ") is used more than once (" + numberOfUse + ")."));
                }
            }
        }
    }

    /* (non-Javadoc)
     * @see org.junit.runners.ParentRunner#classBlock(org.junit.runner.notification.RunNotifier)
     */
    @Override
    protected Statement classBlock(RunNotifier notifier) {
        return childrenInvoker(notifier);
    }

    /* (non-Javadoc)
     * @see org.junit.runners.ParentRunner#getRunnerAnnotations()
     */
    @Override
    protected Annotation[] getRunnerAnnotations() {
        return new Annotation[0];
    }

    private List<FrameworkField> getAnnotatedFieldsByParameter() {
        return getTestClass().getAnnotatedFields(Parameter.class);
    }

    private boolean fieldsAreAnnotated() {
        return !getAnnotatedFieldsByParameter().isEmpty();
    }
	
}
