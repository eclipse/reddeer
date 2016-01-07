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
package org.jboss.reddeer.junit.test.execution;

import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.junit.extensionpoint.IAfterTest;
import org.jboss.reddeer.junit.extensionpoint.IBeforeTest;
import org.jboss.reddeer.junit.internal.configuration.NullTestRunConfiguration;
import org.jboss.reddeer.junit.internal.configuration.SuiteConfiguration;
import org.jboss.reddeer.junit.internal.configuration.TestRunConfiguration;
import org.jboss.reddeer.junit.internal.runner.EmptySuite;
import org.jboss.reddeer.junit.internal.runner.NamedSuite;
import org.jboss.reddeer.junit.internal.runner.RequirementsRunnerBuilder;
import org.jboss.reddeer.junit.internal.runner.TestsExecutionManager;
import org.jboss.reddeer.junit.internal.runner.TestsWithoutExecutionSuite;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunListener;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

public class ExecutionTestRedDeerSuite extends Suite {

	protected static RunListener[] runListeners;
	
	public ExecutionTestRedDeerSuite(Class<?> clazz, RunnerBuilder builder)
			throws InitializationError {
		this(clazz, builder, new SuiteConfiguration());
	}

	protected ExecutionTestRedDeerSuite(Class<?> clazz, RunnerBuilder builder,
			SuiteConfiguration config) throws InitializationError {
		super(EmptySuite.class, createSuite(clazz, config));
	}

	protected static List<Runner> createSuite(Class<?> clazz,
			SuiteConfiguration config) throws InitializationError {
		TestsExecutionManager testsManager = new TestsExecutionManager();
		List<Runner> configuredSuites = new ArrayList<Runner>();

		TestRunConfiguration nullTestRunConfig = new NullTestRunConfiguration();
		configuredSuites.add(new NamedSuite(new Class[] { clazz },
				new RequirementsRunnerBuilder(nullTestRunConfig, runListeners, new ArrayList<IBeforeTest>(),
						new ArrayList<IAfterTest>(), testsManager), nullTestRunConfig.getId()));

		if (!testsManager.allTestsAreExecuted()) {
			configuredSuites.add(new TestsWithoutExecutionSuite(
					new Class[] { clazz }, testsManager));
		}
		return configuredSuites;
	}

	@Override
	protected String getName() {
		return "Execution Test Red Deer Suite";
	}
}
