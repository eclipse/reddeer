/*******************************************************************************
 * Copyright (c) 2017, 2018 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.junit.test.integration.runner.order.testcase;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.eclipse.reddeer.junit.internal.configuration.SuiteConfiguration;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.junit.test.integration.runner.order.RequirementsOrderRunnerSuite;
import org.eclipse.reddeer.junit.test.integration.runner.order.RequirementsOrderRunnerSuite.RequirementOrderErroneousTest;
import org.eclipse.reddeer.junit.test.integration.runner.order.RequirementsOrderRunnerSuite.RequirementOrderFailedTest;
import org.eclipse.reddeer.junit.test.integration.runner.order.RequirementsOrderRunnerSuite.RequirementOrderPassedTest;
import org.eclipse.reddeer.junit.test.integration.runner.order.TestSequence;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;

/**
 * Tests order of calling befores and afters of test class 
 * combined with requirement calling of those methods during passed/failed/erroneous tests.
 * @author odockal
 *
 */
@RunWith(RedDeerSuite.class)
public class RequirementOrderCallWithFailsTest {
	
	@Before
	public void setupSequence() {
		TestSequence.getRealSequence().clear();
	}
	
	@Test
	public void testPassingTest() throws InitializationError {
		SuiteConfiguration config = new SuiteConfiguration(RequirementOrderPassedTest.class);
		List<Runner> runners = RedDeerSuite.createSuites(RequirementOrderPassedTest.class, config);
		
		Runner myRunner = runners.get(0);
		RunNotifier notifier = new RunNotifier();
		myRunner.run(notifier); // runner.run executes the test and static values could be retrieved
		String sequenceDiff = TestSequence.diffRealSequence(RequirementsOrderRunnerSuite.getStaticExpectedSequence());
		assertTrue("Test sequence is different than expected. " + sequenceDiff , sequenceDiff == null);
	}
	
	@Test
	public void testFailingTest() throws InitializationError {
		SuiteConfiguration config = new SuiteConfiguration(RequirementOrderFailedTest.class);
		List<Runner> runners = RedDeerSuite.createSuites(RequirementOrderFailedTest.class, config);
		
		Runner myRunner = runners.get(0);
		RunNotifier notifier = new RunNotifier();
		myRunner.run(notifier); // runner.run executes the test and static values could be retrieved
		String sequenceDiff = TestSequence.diffRealSequence(RequirementsOrderRunnerSuite.getStaticExpectedSequence());
		assertTrue("Test sequence is different than expected. " + sequenceDiff , sequenceDiff == null);
	}
	
	@Test
	public void testErroneousTest() throws InitializationError {
		SuiteConfiguration config = new SuiteConfiguration(RequirementOrderErroneousTest.class);
		List<Runner> runners = RedDeerSuite.createSuites(RequirementOrderErroneousTest.class, config);
		
		Runner myRunner = runners.get(0);
		RunNotifier notifier = new RunNotifier();
		myRunner.run(notifier); // runner.run executes the test and static values could be retrieved
		String sequenceDiff = TestSequence.diffRealSequence(RequirementsOrderRunnerSuite.getStaticExpectedSequence());
		assertTrue("Test sequence is different than expected. " + sequenceDiff , sequenceDiff == null);
	}
	
	@After
	public void clearSequence() {
		TestSequence.getRealSequence().clear();
	}
}
