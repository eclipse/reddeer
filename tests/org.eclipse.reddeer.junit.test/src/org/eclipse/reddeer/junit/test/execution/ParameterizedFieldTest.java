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

import static org.junit.Assert.assertTrue;

import java.util.LinkedList;

import org.eclipse.reddeer.junit.internal.runner.ParameterizedRequirementsRunnerFactory;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.junit.test.integration.runner.IAfterTestImpl;
import org.eclipse.reddeer.junit.test.integration.runner.IBeforeTestImpl;
import org.eclipse.reddeer.junit.test.integration.runner.order.TestSequence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.Parameterized.UseParametersRunnerFactory;

/**
 * Tests parameterized test with parameters injected to field
 * @author vlado pakan
 *
 */
@RunWith(RedDeerSuite.class)
@UseParametersRunnerFactory(ParameterizedRequirementsRunnerFactory.class)
public class ParameterizedFieldTest {
	
	private static final String[] PARAMS = new String[] {"param0","param1"};
	private static final LinkedList<Object> expectedSequence = new LinkedList<Object>();
	
	@Parameter
	public String param;
	
	static{
		expectedSequence.addLast(TestSequence.createBeforeClass(ParameterizedFieldTest.class));
		expectedSequence.addLast(TestSequence.createIBefore(IBeforeTestImpl.class));
		expectedSequence.addLast(TestSequence.createBefore(ParameterizedFieldTest.class));
		expectedSequence.addLast(TestSequence.createTestWithParam(ParameterizedFieldTest.class,params()[0]));
		expectedSequence.addLast(TestSequence.createAfter(ParameterizedFieldTest.class));
		expectedSequence.addLast(TestSequence.createIAfter(IAfterTestImpl.class));
		expectedSequence.addLast(TestSequence.createIBefore(IBeforeTestImpl.class));
		expectedSequence.addLast(TestSequence.createBefore(ParameterizedFieldTest.class));
		expectedSequence.addLast(TestSequence.createTestWithParam(ParameterizedFieldTest.class,params()[1]));
		expectedSequence.addLast(TestSequence.createAfter(ParameterizedFieldTest.class));
		expectedSequence.addLast(TestSequence.createIAfter(IAfterTestImpl.class));
		expectedSequence.addLast(TestSequence.createAfterClass(ParameterizedFieldTest.class));
	}
	
	@Parameters(name = "{0}")
	public static String[] params() {
		return PARAMS;
	}

	@BeforeClass
	public static void beforeClass(){
		TestSequence.getRealSequence().clear();
		TestSequence.addBeforeClass(ParameterizedFieldTest.class);
	}

	@Before
	public void before(){
		TestSequence.addBefore(ParameterizedFieldTest.class);
	}

	@Test
	public void test(){
		TestSequence.addTestWithParam(ParameterizedFieldTest.class , this.param);
	}

	@After
	public void after(){
		TestSequence.addAfter(ParameterizedFieldTest.class);
	}

	@AfterClass
	public static void afterClass(){
		TestSequence.addAfterClass(ParameterizedFieldTest.class);
		String sequenceDiff = TestSequence.diffRealSequence(expectedSequence);
		TestSequence.getRealSequence().clear();
		assertTrue("Test sequence is different than expected. " + sequenceDiff , sequenceDiff == null);
	}
}
