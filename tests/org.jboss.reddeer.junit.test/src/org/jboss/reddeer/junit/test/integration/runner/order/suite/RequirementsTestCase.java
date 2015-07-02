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
