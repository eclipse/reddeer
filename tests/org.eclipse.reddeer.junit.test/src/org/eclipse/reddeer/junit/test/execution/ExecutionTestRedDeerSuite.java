/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.junit.test.execution;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.reddeer.junit.extensionpoint.IAfterTest;
import org.eclipse.reddeer.junit.extensionpoint.IBeforeTest;
import org.eclipse.reddeer.junit.internal.configuration.RequirementConfigurationSet;
import org.eclipse.reddeer.junit.internal.configuration.SuiteConfiguration;
import org.eclipse.reddeer.junit.internal.runner.EmptySuite;
import org.eclipse.reddeer.junit.internal.runner.NamedSuite;
import org.eclipse.reddeer.junit.internal.runner.RequirementsRunnerBuilder;
import org.eclipse.reddeer.junit.internal.runner.TestsExecutionManager;
import org.eclipse.reddeer.junit.internal.runner.TestsWithoutExecutionSuite;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunListener;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

public class ExecutionTestRedDeerSuite extends Suite {

	protected static RunListener[] runListeners;
	
	public ExecutionTestRedDeerSuite(Class<?> clazz, RunnerBuilder builder)
			throws InitializationError {
		this(clazz, builder, new SuiteConfiguration(clazz));
	}

	protected ExecutionTestRedDeerSuite(Class<?> clazz, RunnerBuilder builder,
			SuiteConfiguration config) throws InitializationError {
		super(EmptySuite.class, createSuite(clazz, config));
	}

	protected static List<Runner> createSuite(Class<?> clazz,
			SuiteConfiguration config) throws InitializationError {
		TestsExecutionManager testsManager = new TestsExecutionManager();
		List<Runner> configuredSuites = new ArrayList<Runner>();

		configuredSuites.add(new NamedSuite(new Class[] { clazz },
				new RequirementsRunnerBuilder(new RequirementConfigurationSet(), runListeners, new ArrayList<IBeforeTest>(),
						new ArrayList<IAfterTest>(), testsManager), "noconfig"));

		if (!testsManager.allTestsAreExecuted()) {
			configuredSuites.add(new TestsWithoutExecutionSuite(
					new Class[] { clazz }, testsManager));
		}
		return configuredSuites;
	}

	@Override
	protected String getName() {
		return "Execution Test RedDeer Suite";
	}
}
