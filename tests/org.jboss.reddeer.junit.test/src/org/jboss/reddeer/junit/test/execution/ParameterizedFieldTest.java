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

import static org.junit.Assert.assertTrue;

import java.util.LinkedList;

import org.jboss.reddeer.junit.internal.runner.ParameterizedRequirementsRunnerFactory;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.junit.test.integration.runner.IAfterTestImpl;
import org.jboss.reddeer.junit.test.integration.runner.IBeforeTestImpl;
import org.jboss.reddeer.junit.test.integration.runner.order.TestSequence;
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
