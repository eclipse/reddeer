package org.jboss.reddeer.junit.internal.runner;

import static org.hamcrest.Matchers.isA;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.jboss.reddeer.junit.internal.configuration.RequirementsConfiguration;
import org.jboss.reddeer.junit.internal.configuration.TestRunConfiguration;
import org.jboss.reddeer.junit.internal.requirement.Requirements;
import org.jboss.reddeer.junit.internal.requirement.RequirementsBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Runner;

public class RequirementsRunnerBuilderTest {

	private RequirementsRunnerBuilder runnerBuilder;
	
	private Requirements requirements;
	
	@Before
	public void setup(){
		TestRunConfiguration testRunConfig = mock(TestRunConfiguration.class);

		requirements = mock(Requirements.class);
		RequirementsBuilder requirementsBuilder = mock(RequirementsBuilder.class);
		when(requirementsBuilder.build(any(Class.class), any(RequirementsConfiguration.class), any(String.class))).thenReturn(requirements);
				
		runnerBuilder = new RequirementsRunnerBuilder(testRunConfig);
		runnerBuilder.setRequirementsBuilder(requirementsBuilder);
	}
	
	@Test
	public void canFulfill() throws Throwable {
		when(requirements.canFulfill()).thenReturn(true);
		
		Runner runner = runnerBuilder.runnerForClass(TestCase.class);
		
		assertNotNull(runner);
		assertTrue(isA(RequirementsRunner.class).matches(runner));
	}

	@Test
	public void cannotFulfill() throws Throwable {
		when(requirements.canFulfill()).thenReturn(false);
		
		Runner runner = runnerBuilder.runnerForClass(TestCase.class);
		
		assertNull(runner);
	}
	
	public static class TestCase {
		@Test
		public void fakeTest(){
			
		}
	}
}
