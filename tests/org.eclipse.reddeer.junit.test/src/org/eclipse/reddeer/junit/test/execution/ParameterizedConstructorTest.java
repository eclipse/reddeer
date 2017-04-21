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
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.Parameterized.UseParametersRunnerFactory;

/**
 * Tests parameterized test with parameters injected to constructor
 * @author vlado pakan
 *
 */
@RunWith(RedDeerSuite.class)
@UseParametersRunnerFactory(ParameterizedRequirementsRunnerFactory.class)
public class ParameterizedConstructorTest {

	private String param;
	
	private static final String[] PARAMS = new String[] {"param0","param1"};
	
	private static final LinkedList<Object> expectedSequence = new LinkedList<Object>(); 
	
	static{
		expectedSequence.addLast(TestSequence.createBeforeClass(ParameterizedConstructorTest.class));
		expectedSequence.addLast(TestSequence.constructTestWithParam(ParameterizedConstructorTest.class,params()[0]));
		expectedSequence.addLast(TestSequence.createIBefore(IBeforeTestImpl.class));
		expectedSequence.addLast(TestSequence.createBefore(ParameterizedConstructorTest.class));
		expectedSequence.addLast(TestSequence.createTestWithParam(ParameterizedConstructorTest.class,params()[0]));
		expectedSequence.addLast(TestSequence.createAfter(ParameterizedConstructorTest.class));
		expectedSequence.addLast(TestSequence.createIAfter(IAfterTestImpl.class));
		expectedSequence.addLast(TestSequence.constructTestWithParam(ParameterizedConstructorTest.class,params()[1]));
		expectedSequence.addLast(TestSequence.createIBefore(IBeforeTestImpl.class));
		expectedSequence.addLast(TestSequence.createBefore(ParameterizedConstructorTest.class));
		expectedSequence.addLast(TestSequence.createTestWithParam(ParameterizedConstructorTest.class,params()[1]));
		expectedSequence.addLast(TestSequence.createAfter(ParameterizedConstructorTest.class));
		expectedSequence.addLast(TestSequence.createIAfter(IAfterTestImpl.class));
		expectedSequence.addLast(TestSequence.createAfterClass(ParameterizedConstructorTest.class));
	}
	
	@Parameters(name = "param: {0}")
	public static String[] params() {
		return PARAMS;
	}

	public ParameterizedConstructorTest(String param) {
		this.param = param;
		TestSequence.addConstructTestWithParam(ParameterizedConstructorTest.class, this.param);
	}

	@BeforeClass
	public static void beforeClass(){
		TestSequence.getRealSequence().clear();
		TestSequence.addBeforeClass(ParameterizedConstructorTest.class);
	}

	@Before
	public void before(){
		TestSequence.addBefore(ParameterizedConstructorTest.class);
	}

	@Test
	public void test(){
		TestSequence.addTestWithParam(ParameterizedConstructorTest.class , this.param);
	}

	@After
	public void after(){
		TestSequence.addAfter(ParameterizedConstructorTest.class);
	}

	@AfterClass
	public static void afterClass(){
		TestSequence.addAfterClass(ParameterizedConstructorTest.class);
		String sequenceDiff = TestSequence.diffRealSequence(expectedSequence);
		TestSequence.getRealSequence().clear();
		assertTrue("Test sequence is different than expected. " + sequenceDiff , sequenceDiff == null);
	}
}
