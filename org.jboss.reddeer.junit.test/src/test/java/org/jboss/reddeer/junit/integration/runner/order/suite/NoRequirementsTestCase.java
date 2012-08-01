package org.jboss.reddeer.junit.integration.runner.order.suite;

import org.jboss.reddeer.junit.integration.runner.order.TestSequence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class NoRequirementsTestCase {

	@BeforeClass
	public static void beforeClass(){
		TestSequence.addBeforeClass(NoRequirementsTestCase.class);
	}
	
	@Before
	public void before(){
		TestSequence.addBefore(NoRequirementsTestCase.class);
	}
	
	@Test
	public void test(){
		TestSequence.addTest(NoRequirementsTestCase.class);
	}
	
	@After
	public void after(){
		TestSequence.addAfter(NoRequirementsTestCase.class);
	}
	
	@AfterClass
	public static void afterClass(){
		TestSequence.addAfterClass(NoRequirementsTestCase.class);
	}
}
