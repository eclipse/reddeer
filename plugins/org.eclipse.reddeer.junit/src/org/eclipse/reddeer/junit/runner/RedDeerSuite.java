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
package org.eclipse.reddeer.junit.runner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.junit.extensionpoint.IAfterTest;
import org.eclipse.reddeer.junit.extensionpoint.IBeforeTest;
import org.eclipse.reddeer.junit.extensionpoint.IIssueTracker;
import org.eclipse.reddeer.junit.internal.configuration.RequirementConfigurationSet;
import org.eclipse.reddeer.junit.internal.configuration.SuiteConfiguration;
import org.eclipse.reddeer.junit.internal.extensionpoint.AfterTestInitialization;
import org.eclipse.reddeer.junit.internal.extensionpoint.BeforeTestInitialization;
import org.eclipse.reddeer.junit.internal.extensionpoint.IssueTrackerInitialization;
import org.eclipse.reddeer.junit.internal.runner.EmptySuite;
import org.eclipse.reddeer.junit.internal.runner.NamedSuite;
import org.eclipse.reddeer.junit.internal.runner.RequirementsRunnerBuilder;
import org.eclipse.reddeer.junit.internal.runner.TestsExecutionManager;
import org.eclipse.reddeer.junit.internal.runner.TestsWithoutExecutionSuite;
import org.eclipse.reddeer.junit.requirement.configuration.MissingRequirementConfiguration;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunListener;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

/**
 * 
 * Allows to run the tests (single or a suite) for each configuration file
 * provided.
 * 
 * @author Lucia Jelinkova
 * @author Ondrej Dockal
 * @author mlabuda@redhat.com
 * 
 */
public class RedDeerSuite extends Suite {

	private static final Logger log = Logger.getLogger(RedDeerSuite.class);
	// this variable has to set within static initialization block in child
	// class
	// in order to add custom listeners
	protected static RunListener[] runListeners;

	private static List<IBeforeTest> beforeTestExtensions = RedDeerSuite.initializeBeforeTestExtensions();

	private static List<IAfterTest> afterTestExtensions = RedDeerSuite.initializeAfterTestExtensions();

	private static List<IIssueTracker> issueTrackerExtensions;
	
	private String suiteName;

	/**
	 * Called by the JUnit framework.
	 *
	 * @param clazz
	 *            the clazz
	 * @param builder
	 *            the builder
	 * @throws InitializationError
	 *             the initialization error
	 */
	public RedDeerSuite(Class<?> clazz, RunnerBuilder builder) throws InitializationError {
		this(clazz, builder, new SuiteConfiguration(clazz));
	}

	/**
	 * The {@link EmptySuite} makes sure that the @BeforeClass and @AfterClass
	 * are not called on the suite class too often.
	 *
	 * @param clazz
	 *            the clazz
	 * @param builder
	 *            the builder
	 * @param config
	 *            the config
	 * @throws InitializationError
	 *             the initialization error
	 */
	protected RedDeerSuite(Class<?> clazz, RunnerBuilder builder, SuiteConfiguration config)
			throws InitializationError {
		super(EmptySuite.class, createSuites(clazz, config));
		this.suiteName = clazz.getName();
	}

	/**
	 * Creates a new suite for each configuration set obtained from suite configuration.
	 *
	 * @param clazz
	 *            class to build tests without execution suite (if class should not be executed)
	 * @param config
	 *            suite configuration
	 * @return list of runners
	 * @throws InitializationError
	 *             the initialization error
	 */
	public static List<Runner> createSuites(Class<?> clazz, SuiteConfiguration config) throws InitializationError {
		TestsExecutionManager testsManager = new TestsExecutionManager();
		List<Runner> configuredSuites = new ArrayList<Runner>();
		boolean isSuite = isSuite(clazz);

		// Revamp creation of suites
		Map<RequirementConfigurationSet, List<Class<?>>> configurationSetsMap = config.getConfigurationSetsSuites();
		for (RequirementConfigurationSet configurationSet : configurationSetsMap.keySet()) {
			List<Class<?>> testClasses = configurationSetsMap.get(configurationSet);
			RequirementsRunnerBuilder requirementsRunnerBuilder = new RequirementsRunnerBuilder(configurationSet,
					runListeners, beforeTestExtensions, afterTestExtensions, testsManager);
			if (configurationSet.getConfigurationSet().contains(Arrays.asList(new MissingRequirementConfiguration()))) {
				configuredSuites.add(new TestsWithoutExecutionSuite((Class[]) testClasses.toArray(), testsManager));
			} else {
				configuredSuites.add(new NamedSuite((Class[]) testClasses.toArray(), requirementsRunnerBuilder,
						configurationSet.getId()));
			}
		}

		if (!testsManager.allTestsAreExecuted()) {
			if (isSuite) {
				configuredSuites.add(new TestsWithoutExecutionSuite(clazz, testsManager));
			} else {
				configuredSuites.add(new TestsWithoutExecutionSuite(new Class[] { clazz }, testsManager));
			}
		}
		log.info("RedDeer suite created");
		return configuredSuites;
	}

	private static boolean isSuite(Class<?> clazz) {
		SuiteClasses annotation = clazz.getAnnotation(SuiteClasses.class);
		return annotation != null;
	}

	@Override
	protected String getName() {
		return suiteName;
	}

	/**
	 * Initializes all Before Test extensions
	 */
	private static List<IBeforeTest> initializeBeforeTestExtensions() {
		List<IBeforeTest> beforeTestExts;
		// check if eclipse is running
		try {
			Class.forName("org.eclipse.core.runtime.Platform");
			log.debug("Eclipse is running");
			beforeTestExts = BeforeTestInitialization.initialize();
		} catch (ClassNotFoundException e) {
			// do nothing extension is implemented only for eclipse right now
			log.debug("Eclipse is not running");
			beforeTestExts = new LinkedList<IBeforeTest>();
		}
		return beforeTestExts;
	}

	/**
	 * Initializes all After Test extensions
	 */
	private static List<IAfterTest> initializeAfterTestExtensions() {
		List<IAfterTest> afterTestExts;
		// check if eclipse is running
		try {
			Class.forName("org.eclipse.core.runtime.Platform");
			log.debug("Eclipse is running");
			afterTestExts = AfterTestInitialization.initialize();
		} catch (ClassNotFoundException e) {
			// do nothing extension is implemented only for eclipse right now
			log.debug("Eclipse is not running");
			afterTestExts = new LinkedList<IAfterTest>();
		}
		return afterTestExts;
	}

	/**
	 * Gets a list of issue tracker extensions.
	 * 
	 * @return a list of issue tracker extensions
	 */
	public static List<IIssueTracker> getIssueTrackerExtensions() {
		if (issueTrackerExtensions == null) {
			issueTrackerExtensions = initializeIssueTrackerExtensions();
		}
		return issueTrackerExtensions;
	}

	/**
	 * Initializes all Issue tracker extensions
	 */
	private static List<IIssueTracker> initializeIssueTrackerExtensions() {
		List<IIssueTracker> issueTrackerExts;
		// check if eclipse is running
		try {
			Class.forName("org.eclipse.core.runtime.Platform");
			log.debug("Eclipse is running");
			issueTrackerExts = IssueTrackerInitialization.initialize();
		} catch (ClassNotFoundException e) {
			// do nothing extension is implemented only for eclipse right now
			log.debug("Eclipse is not running");
			issueTrackerExts = new LinkedList<IIssueTracker>();
		}
		return issueTrackerExts;
	}

}
