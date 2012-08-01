package org.jboss.reddeer.junit.runner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.jboss.reddeer.junit.internal.configuration.SuiteConfiguration;
import org.jboss.reddeer.junit.internal.configuration.TestRunConfiguration;
import org.jboss.reddeer.junit.internal.runner.NamedSuite;
import org.junit.Test;
import org.junit.runner.Runner;
import org.junit.runners.Suite.SuiteClasses;
import org.junit.runners.model.InitializationError;

public class RedDeerSuiteTest {

	@Test
	public void getChildren_noConfiguration() throws InitializationError {
		SuiteConfiguration config = mock(SuiteConfiguration.class);
		when(config.getTestRunConfigurations()).thenReturn(new ArrayList<TestRunConfiguration>());
		
		List<Runner> runners = RedDeerSuite.createSuite(SimpleSuite.class, config);
		
		assertThat(runners.size(), is(0));
	}
	
	@Test
	public void getChildren_suite() throws InitializationError {
		SuiteConfiguration config = mock(SuiteConfiguration.class);
		List<TestRunConfiguration> testRunConfigurations = Arrays.asList(mockTestRunConfig("A"), mockTestRunConfig("B"), mockTestRunConfig("C")); 
		when(config.getTestRunConfigurations()).thenReturn(testRunConfigurations);
		
		List<Runner> runners = RedDeerSuite.createSuite(SimpleSuite.class, config);
		
		assertThat(runners, hasItem(new NamedSuiteMatcher("A")));
		assertThat(runners, hasItem(new NamedSuiteMatcher("B")));
		assertThat(runners, hasItem(new NamedSuiteMatcher("C")));
	}

	@Test
	public void getChildren_test() throws InitializationError {
		SuiteConfiguration config = mock(SuiteConfiguration.class);
		List<TestRunConfiguration> testRunConfigurations = Arrays.asList(mockTestRunConfig("A"), mockTestRunConfig("B"), mockTestRunConfig("C")); 
		when(config.getTestRunConfigurations()).thenReturn(testRunConfigurations);
		
		List<Runner> runners = RedDeerSuite.createSuite(SimpleTest.class, config);
		
		assertThat(runners, hasItem(new NamedSuiteMatcher("A")));
		assertThat(runners, hasItem(new NamedSuiteMatcher("B")));
		assertThat(runners, hasItem(new NamedSuiteMatcher("C")));
	}
	
	private TestRunConfiguration mockTestRunConfig(String id){
		TestRunConfiguration c = mock(TestRunConfiguration.class);
		when(c.getId()).thenReturn(id);
		return c;
	}
	
	class NamedSuiteMatcher extends TypeSafeMatcher<Runner> {

		private String name;
		
		public NamedSuiteMatcher(String name) {
			this.name = name;
		}
		
		public void describeTo(Description description) {
			
		}

		@Override
		public boolean matchesSafely(Runner item) {
			return item instanceof NamedSuite && ((NamedSuite) item).getName().equals(name);
		}
	}
	
	@SuiteClasses({SimpleSuite.class})
	public static class SimpleSuite {
		
	}
	
	public static class SimpleTest {
		
	}
}
