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

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.reddeer.junit.internal.runner.ParameterizedRequirementsRunnerFactory;
import org.eclipse.reddeer.junit.test.integration.runner.RunnerIntegrationPropertyRequirement.RequirementAAnnotation;
import org.eclipse.reddeer.junit.test.integration.runner.RunnerIntegrationPropertyRequirement2.RequirementAAnnotation2;
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

@RunWith(ParametrizedTestRunner.class)
@UseParametersRunnerFactory(ParameterizedRequirementsRunnerFactory.class)
@RequirementAAnnotation
@RequirementAAnnotation2
public class ParametrizedTest {

	@Parameters
	public static Collection<Object> data(){
		return Arrays.asList(new Object[] {1,2});
	}

	@Parameter
	public int fInput;

	@BeforeClass
	public static void beforeClass(){
		TestSequence.addBeforeClass(ParametrizedTest.class);
	}

	@Before
	public void before(){
		TestSequence.addBefore(ParametrizedTest.class);
	}

	@Test
	public void test(){
		TestSequence.addTest(ParametrizedTest.class);
	}

	@After
	public void after(){
		TestSequence.addAfter(ParametrizedTest.class);
	}

	@AfterClass
	public static void afterClass(){
		TestSequence.addAfterClass(ParametrizedTest.class);
	}
}
