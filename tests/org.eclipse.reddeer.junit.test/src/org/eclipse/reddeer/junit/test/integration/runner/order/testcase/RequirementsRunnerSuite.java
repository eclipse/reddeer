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
package org.eclipse.reddeer.junit.test.integration.runner.order.testcase;

import static org.eclipse.reddeer.junit.test.integration.runner.order.TestSequence.createAfter;
import static org.eclipse.reddeer.junit.test.integration.runner.order.TestSequence.createAfterClass;
import static org.eclipse.reddeer.junit.test.integration.runner.order.TestSequence.createBefore;
import static org.eclipse.reddeer.junit.test.integration.runner.order.TestSequence.createBeforeClass;
import static org.eclipse.reddeer.junit.test.integration.runner.order.TestSequence.createCleanup;
import static org.eclipse.reddeer.junit.test.integration.runner.order.TestSequence.createFulfill;
import static org.eclipse.reddeer.junit.test.integration.runner.order.TestSequence.createIAfter;
import static org.eclipse.reddeer.junit.test.integration.runner.order.TestSequence.createIAfterClass;
import static org.eclipse.reddeer.junit.test.integration.runner.order.TestSequence.createIBefore;
import static org.eclipse.reddeer.junit.test.integration.runner.order.TestSequence.createIBeforeClass;
import static org.eclipse.reddeer.junit.test.integration.runner.order.TestSequence.createReqAfter;
import static org.eclipse.reddeer.junit.test.integration.runner.order.TestSequence.createReqAfterClass;
import static org.eclipse.reddeer.junit.test.integration.runner.order.TestSequence.createReqBefore;
import static org.eclipse.reddeer.junit.test.integration.runner.order.TestSequence.createReqBeforeClass;
import static org.eclipse.reddeer.junit.test.integration.runner.order.TestSequence.createTest;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.reddeer.junit.internal.configuration.SuiteConfiguration;
import org.eclipse.reddeer.junit.test.integration.runner.IAfterTestImpl;
import org.eclipse.reddeer.junit.test.integration.runner.IBeforeTestImpl;
import org.eclipse.reddeer.junit.test.integration.runner.injection.RunnerIntegrationRequirement;
import org.eclipse.reddeer.junit.test.integration.runner.order.TestSequenceRedDeerSuite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

public class RequirementsRunnerSuite extends TestSequenceRedDeerSuite {

	private static final List<Object> expectedSequence;

	static {
		expectedSequence = new ArrayList<Object>();
		expectedSequence.add(createIBeforeClass(IBeforeTestImpl.class));
		expectedSequence.add(createFulfill(RunnerIntegrationRequirement.class));
		expectedSequence.add(createReqBeforeClass(RunnerIntegrationRequirement.class));
		expectedSequence.add(createBeforeClass(RequirementsRunnerTest.class));
		
		expectedSequence.add(createIBefore(IBeforeTestImpl.class));
		expectedSequence.add(createReqBefore(RunnerIntegrationRequirement.class));
		expectedSequence.add(createBefore(RequirementsRunnerTest.class));
		expectedSequence.add(createTest(RequirementsRunnerTest.class));
		expectedSequence.add(createAfter(RequirementsRunnerTest.class));
		expectedSequence.add(createReqAfter(RunnerIntegrationRequirement.class));
		expectedSequence.add(createIAfter(IAfterTestImpl.class));
		
		expectedSequence.add(createAfterClass(RequirementsRunnerTest.class));
		expectedSequence.add(createReqAfterClass(RunnerIntegrationRequirement.class));
		expectedSequence.add(createCleanup(RunnerIntegrationRequirement.class));
		expectedSequence.add(createIAfterClass(IAfterTestImpl.class));
	}
	
	public RequirementsRunnerSuite(Class<?> clazz, RunnerBuilder builder,
			SuiteConfiguration config) throws InitializationError {
		super(clazz, builder, config);
	}

	public RequirementsRunnerSuite(Class<?> clazz, RunnerBuilder builder)
			throws InitializationError {
		super(clazz, builder);
	}
	
	@Override
	protected List<?> getExpectedSequence() {
		return expectedSequence;
	}
}
