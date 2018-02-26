/*******************************************************************************
 * Copyright (c) 2018 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.junit.test.integration.runner.order;

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
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.reddeer.common.exception.RedDeerException;
import org.eclipse.reddeer.junit.internal.configuration.SuiteConfiguration;
import org.eclipse.reddeer.junit.test.integration.runner.IAfterTestImpl;
import org.eclipse.reddeer.junit.test.integration.runner.IBeforeTestImpl;
import org.eclipse.reddeer.junit.test.integration.runner.order.OrderRunnerRequirement.RequirementOrderAnnotation;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

public class RequirementsOrderRunnerSuite extends TestSequenceRedDeerSuite {

	private static final List<Object> expectedSequence;

	static {
		expectedSequence = new ArrayList<Object>();
		expectedSequence.add(createIBeforeClass(IBeforeTestImpl.class));
		expectedSequence.add(createFulfill(OrderRunnerRequirement.class));
		expectedSequence.add(createReqBeforeClass(OrderRunnerRequirement.class));
		expectedSequence.add(createBeforeClass(RequirementsOrderRunnerSuite.class));
		
		expectedSequence.add(createIBefore(IBeforeTestImpl.class));
		expectedSequence.add(createReqBefore(OrderRunnerRequirement.class));
		expectedSequence.add(createBefore(RequirementsOrderRunnerSuite.class));
		expectedSequence.add(createTest(RequirementsOrderRunnerSuite.class));
		expectedSequence.add(createAfter(RequirementsOrderRunnerSuite.class));
		expectedSequence.add(createReqAfter(OrderRunnerRequirement.class));
		expectedSequence.add(createIAfter(IAfterTestImpl.class));
		
		expectedSequence.add(createAfterClass(RequirementsOrderRunnerSuite.class));
		expectedSequence.add(createReqAfterClass(OrderRunnerRequirement.class));
		expectedSequence.add(createCleanup(OrderRunnerRequirement.class));
		expectedSequence.add(createIAfterClass(IAfterTestImpl.class));
	}

	@Override
	protected List<?> getExpectedSequence() {
		return expectedSequence;
	}
	
	public RequirementsOrderRunnerSuite(Class<?> clazz, RunnerBuilder builder,
			SuiteConfiguration config) throws InitializationError {
		super(clazz, builder, config);
	}

	public RequirementsOrderRunnerSuite(Class<?> clazz, RunnerBuilder builder)
			throws InitializationError {
		super(clazz, builder);
	}
	
	public static List<?> getStaticExpectedSequence() {
		return expectedSequence;
	}
	
	@RunWith(RequirementsOrderRunnerSuite.class)
	@RequirementOrderAnnotation
	public static class RequirementOrderParentTest {
		
		@BeforeClass
		public static void beforeClass(){
			TestSequence.addBeforeClass(RequirementsOrderRunnerSuite.class);
		}

		@Before
		public void before(){
			TestSequence.addBefore(RequirementsOrderRunnerSuite.class);
		}
		
		@After
		public void after(){
			TestSequence.addAfter(RequirementsOrderRunnerSuite.class);
		}

		@AfterClass
		public static void afterClass(){
			TestSequence.addAfterClass(RequirementsOrderRunnerSuite.class);
		}
	}

	public static class RequirementOrderPassedTest extends RequirementOrderParentTest {

		@Test
		public void test(){
			TestSequence.addTest(RequirementsOrderRunnerSuite.class);
		}
	}
	
	public static class RequirementOrderFailedTest extends RequirementOrderParentTest {

		@Test
		public void test(){
			TestSequence.addTest(RequirementsOrderRunnerSuite.class);
			assertTrue("Test in failure", false);
		}
	}
	
	public static class RequirementOrderErroneousTest extends RequirementOrderParentTest {
		
		@Test
		public void test(){
			TestSequence.addTest(RequirementsOrderRunnerSuite.class);
			throw new RedDeerException("Test in error");
		}
	}
	
	
}
