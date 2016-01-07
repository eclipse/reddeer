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
package org.jboss.reddeer.junit.test.integration.runner.order.testcase;

import org.jboss.reddeer.junit.test.integration.runner.RunnerIntegrationPropertyRequirement.RequirementAAnnotation;
import org.jboss.reddeer.junit.test.integration.runner.RunnerIntegrationPropertyRequirement2.RequirementAAnnotation2;
import org.jboss.reddeer.junit.test.integration.runner.order.TestSequence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RequirementsRunnerSuite.class)
@RequirementAAnnotation
@RequirementAAnnotation2
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
