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
package org.jboss.reddeer.junit.test.integration.runner.order.suite;

import org.jboss.reddeer.junit.test.integration.runner.RunnerIntegrationPropertyRequirement.RequirementAAnnotation;
import org.jboss.reddeer.junit.test.integration.runner.order.TestSequence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

@RequirementAAnnotation
public class RequirementsTestCase {

	@BeforeClass
	public static void beforeClass(){
		TestSequence.addBeforeClass(RequirementsTestCase.class);
	}
	
	@Before
	public void before(){
		TestSequence.addBefore(RequirementsTestCase.class);
	}
	
	@Test
	public void test1(){
		TestSequence.addTest(RequirementsTestCase.class);
	}
	
	@Test
	public void test2(){
		TestSequence.addTest(RequirementsTestCase.class);
	}

	@After
	public void after(){
		TestSequence.addAfter(RequirementsTestCase.class);
	}
	
	@AfterClass
	public static void afterClass(){
		TestSequence.addAfterClass(RequirementsTestCase.class);
	}
}
