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
package org.eclipse.reddeer.junit.test.integration.runner.order.testcase;

import org.eclipse.reddeer.junit.test.integration.runner.injection.RunnerIntegrationRequirement.RequirementAAnnotation;
import org.eclipse.reddeer.junit.test.integration.runner.order.TestSequence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RequirementsRunnerSuite.class)
@RequirementAAnnotation
public class RequirementsRunnerTest {

	@BeforeClass
	public static void beforeClass(){
		TestSequence.addBeforeClass(RequirementsRunnerTest.class);
	}
	
	@Before
	public void before(){
		TestSequence.addBefore(RequirementsRunnerTest.class);
	}
	
	@Test
	public void test(){
		TestSequence.addTest(RequirementsRunnerTest.class);
	}
	
	@After
	public void after(){
		TestSequence.addAfter(RequirementsRunnerTest.class);
	}
	
	@AfterClass
	public static void afterClass(){
		TestSequence.addAfterClass(RequirementsRunnerTest.class);
	}
}
