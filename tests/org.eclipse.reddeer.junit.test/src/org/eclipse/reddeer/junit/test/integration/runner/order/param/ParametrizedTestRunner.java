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
package org.eclipse.reddeer.junit.test.integration.runner.order.param;

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
import static org.eclipse.reddeer.junit.test.integration.runner.order.TestSequence.createTest;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.reddeer.junit.internal.configuration.SuiteConfiguration;
import org.eclipse.reddeer.junit.test.integration.runner.IAfterTestImpl;
import org.eclipse.reddeer.junit.test.integration.runner.IBeforeTestImpl;
import org.eclipse.reddeer.junit.test.integration.runner.RunnerIntegrationPropertyRequirement;
import org.eclipse.reddeer.junit.test.integration.runner.order.TestSequenceRedDeerSuite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

public class ParametrizedTestRunner extends TestSequenceRedDeerSuite {

	private static final List<Object> expectedSequence;

	static {
		expectedSequence = new ArrayList<Object>();
		// suite 1
		expectedSequence.add(createIBeforeClass(IBeforeTestImpl.class));
		expectedSequence.add(createFulfill(RunnerIntegrationPropertyRequirement.class));
		expectedSequence.add(createBeforeClass(ParametrizedTest.class));
		// param1
		expectedSequence.add(createIBefore(IBeforeTestImpl.class));
		expectedSequence.add(createBefore(ParametrizedTest.class));
		expectedSequence.add(createTest(ParametrizedTest.class));
		expectedSequence.add(createAfter(ParametrizedTest.class));
		expectedSequence.add(createIAfter(IAfterTestImpl.class));
		// param2
		expectedSequence.add(createIBefore(IBeforeTestImpl.class));
		expectedSequence.add(createBefore(ParametrizedTest.class));
		expectedSequence.add(createTest(ParametrizedTest.class));
		expectedSequence.add(createAfter(ParametrizedTest.class));
		expectedSequence.add(createIAfter(IAfterTestImpl.class));
		expectedSequence.add(createAfterClass(ParametrizedTest.class));
		expectedSequence.add(createCleanup(RunnerIntegrationPropertyRequirement.class));
		expectedSequence.add(createIAfterClass(IAfterTestImpl.class));
	}
	
	public ParametrizedTestRunner(Class<?> clazz, RunnerBuilder builder,
			SuiteConfiguration config) throws InitializationError {
		super(clazz, builder, config);
	}

	public ParametrizedTestRunner(Class<?> clazz, RunnerBuilder builder)
			throws InitializationError {
		super(clazz, builder);
	}
	
	@Override
	protected List<?> getExpectedSequence() {
		return expectedSequence;
	}
}
