package org.jboss.reddeer.junit.integration.runner.order.testcase;

import org.jboss.reddeer.junit.integration.runner.RunnerIntegrationPropertyRequirement.RequirementAAnnotation;
import org.jboss.reddeer.junit.integration.runner.RunnerIntegrationPropertyRequirement2.RequirementAAnnotation2;
import org.jboss.reddeer.junit.integration.runner.order.TestSequence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RequirementsRunner.class)
@RequirementAAnnotation
@RequirementAAnnotation2
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
	public void test(){
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
