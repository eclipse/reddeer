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

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.junit.extensionpoint.IAfterTest;
import org.jboss.reddeer.junit.extensionpoint.IBeforeTest;
import org.jboss.reddeer.junit.internal.configuration.TestRunConfiguration;
import org.jboss.reddeer.junit.internal.requirement.Requirements;
import org.jboss.reddeer.junit.internal.requirement.RequirementsBuilder;
import org.junit.Ignore;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunListener;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.model.RunnerBuilder;

/**
 * Checks if the requirements on the test class can be fulfilled and creates a runner for the test class or
 * ignores the test. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class RequirementsRunnerBuilder extends RunnerBuilder {

	public TestsExecutionManager testsManager;

	private Logger log = Logger.getLogger(RequirementsRunnerBuilder.class);
	
	private RequirementsBuilder requirementsBuilder = new RequirementsBuilder();
	
	private List<IBeforeTest> beforeTestExtensions;
	private List<IAfterTest> afterTestExtensions;
	
	private TestRunConfiguration config;
	
	private RunListener[] runListeners;
	
	/**
	 * Instantiates a new requirements runner builder.
	 *
	 * @param config the config
	 */
	public RequirementsRunnerBuilder(TestRunConfiguration config) {
		this(config, null, null, null, null);
	}
	
	/**
	 * Instantiates a new requirements runner builder.
	 *
	 * @param config the config
	 * @param runListeners the run listeners
	 * @param beforeTestExtensions the before test extensions
	 */
	public RequirementsRunnerBuilder(TestRunConfiguration config , RunListener[] runListeners , List<IBeforeTest> beforeTestExtensions) {
		this(config, runListeners, beforeTestExtensions, null, null);
	}
	
	/**
	 * Instantiates a new requirements runner builder.
	 *
	 * @param config the config
	 * @param runListeners the run listeners
	 * @param beforeTestExtensions the before test extensions
	 * @param afterTestExtensions the after test extensions
	 * @param testsManager the tests manager
	 */
	public RequirementsRunnerBuilder(TestRunConfiguration config , RunListener[] runListeners , List<IBeforeTest> beforeTestExtensions, List<IAfterTest> afterTestExtensions, TestsExecutionManager testsManager) {
		this.config = config;
		this.runListeners = runListeners;
		this.beforeTestExtensions = beforeTestExtensions;
		this.afterTestExtensions = afterTestExtensions;
		this.testsManager = testsManager;
	}

	/* (non-Javadoc)
	 * @see org.junit.runners.model.RunnerBuilder#runnerForClass(java.lang.Class)
	 */
	@Override
	public Runner runnerForClass(Class<?> clazz) throws Throwable {
		log.info("Found test " + clazz);
		if (clazz.getAnnotation(Ignore.class) != null) {
			 return new IgnoredClassRunner(clazz);
		}
		if(testsManager != null) {
			testsManager.addTest(clazz);
		}
		Requirements requirements = requirementsBuilder.build(clazz, config.getRequirementConfiguration(), config.getId());
		if (requirements.canFulfill()){
			log.info("All requirements can be fulfilled, the test will run");
			if(testsManager != null) {
				testsManager.addExecutedTest(clazz);
			}
			if (isParameterized(clazz)){
				return new ParameterizedRunner(clazz, requirements, config.getId(),runListeners, beforeTestExtensions, afterTestExtensions);
			}else{
				return new RequirementsRunner(clazz, requirements, config.getId(),runListeners, beforeTestExtensions, afterTestExtensions);
			}
		} else {
			log.info("All requirements cannot be fulfilled, the test will NOT run");
			return null;
		}
	}

	/**
	 * Sets the requirements builder.
	 *
	 * @param requirementsBuilder the new requirements builder
	 */
	public void setRequirementsBuilder(RequirementsBuilder requirementsBuilder) {
		this.requirementsBuilder = requirementsBuilder;
	}
	
	/**
	 * Check, whether class is parameterized or not.
	 * @param clazz class to check for.
	 * @return true if some of class methods has @Parameters annotation.
	 */
	private boolean isParameterized(Class<?> clazz){
		for (Method method : clazz.getDeclaredMethods()){
			if (method.getAnnotation(Parameters.class) != null){
				if (Modifier.isPublic(method.getModifiers()) && Modifier.isStatic(method.getModifiers())){
					return true;
				}
			}
		}
		return false;
	}
}
