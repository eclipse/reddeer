/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.junit.internal.runner;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.junit.extensionpoint.IAfterTest;
import org.eclipse.reddeer.junit.extensionpoint.IBeforeTest;
import org.eclipse.reddeer.junit.internal.configuration.RequirementConfigurationSet;
import org.eclipse.reddeer.junit.internal.requirement.Requirements;
import org.eclipse.reddeer.junit.internal.requirement.RequirementsBuilder;
import org.junit.Ignore;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunListener;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.Suite;
import org.junit.runners.model.RunnerBuilder;

/**
 * Checks if the requirements on the test class can be fulfilled and creates a
 * runner for the test class or ignores the test.
 * 
 * @author Lucia Jelinkova, mlabuda@redhat.com
 *
 */
public class RequirementsRunnerBuilder extends RunnerBuilder {

	public TestsExecutionManager testsManager;

	private Logger log = Logger.getLogger(RequirementsRunnerBuilder.class);

	private RequirementsBuilder requirementsBuilder = new RequirementsBuilder();

	private List<IBeforeTest> beforeTestExtensions;
	private List<IAfterTest> afterTestExtensions;

	private RunListener[] runListeners;

	private RequirementConfigurationSet configurationSet;

	/**
	 * Instantiates a new requirements runner builder
	 * 
	 * @param configurationSet
	 */
	public RequirementsRunnerBuilder(RequirementConfigurationSet configurationSet) {
		this(configurationSet, null, null, null, null);
	}

	/**
	 * Instantiates a new requirements runner builder.
	 *
	 * @param configurationSet
	 *            configuration set
	 * @param runListeners
	 *            the run listeners
	 * @param beforeTestExtensions
	 *            the before test extensions
	 */
	public RequirementsRunnerBuilder(RequirementConfigurationSet configurationSet, RunListener[] runListeners,
			List<IBeforeTest> beforeTestExtensions) {
		this(configurationSet, runListeners, beforeTestExtensions, null, null);
	}

	/**
	 * Instantiates a new requirements runner builder.
	 *
	 * @param configurationSet
	 *            configuration set
	 * @param runListeners
	 *            the run listeners
	 * @param beforeTestExtensions
	 *            the before test extensions
	 * @param afterTestExtensions
	 *            the after test extensions
	 * @param testsManager
	 *            the tests manager
	 */
	public RequirementsRunnerBuilder(RequirementConfigurationSet configurationSet, RunListener[] runListeners,
			List<IBeforeTest> beforeTestExtensions, List<IAfterTest> afterTestExtensions,
			TestsExecutionManager testsManager) {
		this.configurationSet = configurationSet;
		this.runListeners = runListeners;
		this.beforeTestExtensions = beforeTestExtensions;
		this.afterTestExtensions = afterTestExtensions;
		this.testsManager = testsManager;
	}

	@Override
	public Runner runnerForClass(Class<?> clazz) throws Throwable {
		if (clazz.getAnnotation(Ignore.class) != null) {
			return new IgnoredClassRunner(clazz);
		}
		if (clazz.getAnnotation(Suite.SuiteClasses.class) != null) {
			return new Suite(clazz, this);
		}
		log.info("Found test " + clazz);
		if (testsManager != null) {
			testsManager.addTest(clazz);
		}
		Requirements requirements = requirementsBuilder.build(configurationSet, clazz);
		if (testsManager != null) {
			testsManager.addExecutedTest(clazz);
		}
		if (isParameterized(clazz)) {
			return new ParameterizedRunner(clazz, requirements, configurationSet.getId(), runListeners, beforeTestExtensions,
					afterTestExtensions);
		} else {
			return new RequirementsRunner(clazz, requirements, configurationSet.getId(), runListeners, beforeTestExtensions,
					afterTestExtensions);
		}
	}

	/**
	 * Check, whether class is parameterized or not.
	 * 
	 * @param clazz
	 *            class to check for.
	 * @return true if some of class methods has @Parameters annotation.
	 */
	private boolean isParameterized(Class<?> clazz) {
		for (Method method : clazz.getDeclaredMethods()) {
			if (method.getAnnotation(Parameters.class) != null) {
				if (Modifier.isPublic(method.getModifiers()) && Modifier.isStatic(method.getModifiers())) {
					return true;
				}
			}
		}
		return false;
	}
}
