package org.jboss.reddeer.junit.internal.runner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.jboss.reddeer.junit.internal.requirement.Requirements;
import org.jboss.reddeer.junit.internal.requirement.inject.RequirementsInjector;
import org.junit.Test;

public class RequirementsRunnerTest {

	private RequirementsRunner requirementsRunner;
	
	@Test
	public void testInjectMethodInvocation() throws Exception {
		
		Requirements requirements = mock(Requirements.class);
		requirementsRunner = new RequirementsRunner(SimpleTest.class, requirements, "no config");
		
		RequirementsInjector reqInjector = mock(RequirementsInjector.class);
		requirementsRunner.setRequirementsInjector(reqInjector);
		
		Object testInstance = requirementsRunner.createTest();
		verify(reqInjector, times(1)).inject(testInstance, requirements);
		
	}
	
	public static class SimpleTest {
		@Test
		public void testNothing() {
			
		}
	}
	
}
