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
package org.jboss.reddeer.junit.test.runner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.jboss.reddeer.junit.internal.configuration.SuiteConfiguration;
import org.jboss.reddeer.junit.internal.configuration.TestRunConfiguration;
import org.jboss.reddeer.junit.internal.requirement.Requirements;
import org.jboss.reddeer.junit.internal.runner.NamedSuite;
import org.jboss.reddeer.junit.internal.runner.RequirementsRunnerBuilder;
import org.jboss.reddeer.junit.internal.runner.TestsWithoutExecutionSuite;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.junit.Test;
import org.junit.runner.Runner;
import org.junit.runners.Suite;
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
	
	@Test
	public void testsWithoutExecution_suite() throws InitializationError {
		SuiteConfiguration config = mock(SuiteConfiguration.class);
		List<TestRunConfiguration> testRunConfigurations = Arrays.asList(
				mockTestRunConfig("A"), mockTestRunConfig("B"),
				mockUnfulfillableTestRunConfig("C"));
		when(config.getTestRunConfigurations()).thenReturn(testRunConfigurations);
		
		List<Runner> runners = RedDeerSuite.createSuite(SimpleSuite.class, config);
		
		assertThat(runners, hasItem(new NamedSuiteMatcher("A")));
		assertThat(runners, hasItem(new NamedSuiteMatcher("B")));
		
		Runner runner = runners.get(runners.size()-1);
		assertTrue(runner.getClass().toString(), runner instanceof TestsWithoutExecutionSuite);
	}

	@Test
	public void testsWithoutExecution_test() throws InitializationError {
		SuiteConfiguration config = mock(SuiteConfiguration.class);
		List<TestRunConfiguration> testRunConfigurations = Arrays.asList(
				mockTestRunConfig("A"), mockTestRunConfig("B"),
				mockUnfulfillableTestRunConfig("C"));
		when(config.getTestRunConfigurations()).thenReturn(testRunConfigurations);
		
		List<Runner> runners = RedDeerSuite.createSuite(SimpleTest.class, config);
		
		assertThat(runners, hasItem(new NamedSuiteMatcher("A")));
		assertThat(runners, hasItem(new NamedSuiteMatcher("B")));
		
		Runner runner = runners.get(runners.size()-1);
		assertTrue(runner instanceof TestsWithoutExecutionSuite);
	}
	
	@Test
	public void testNesteSuite() throws Throwable{
		assertEquals(1,getTestCount(ParentSuite.class));
	}
	
	@Test
	public void testNesteSuiteWithTests() throws Throwable{
		assertEquals(2,getTestCount(ParentSuite1.class));
	}
	
	private int getTestCount(@SuppressWarnings("rawtypes") Class suiteClass) throws Throwable{
		SuiteConfiguration config = mock(SuiteConfiguration.class);
		List<TestRunConfiguration> testRunConfigurations = Arrays.asList(
				mockTestRunConfig("A"));
		when(config.getTestRunConfigurations()).thenReturn(testRunConfigurations);
		
		List<Runner> runners = RedDeerSuite.createSuite(suiteClass, config);
		RequirementsRunnerBuilder builder = (RequirementsRunnerBuilder)((NamedSuite)runners.get(0)).getRunnerBuilder();
		
		Runner testRunner = builder.runnerForClass(suiteClass);
		assertTrue(testRunner.getClass().equals(Suite.class));
		return testRunner.testCount();
	}
	
	private TestRunConfiguration mockTestRunConfig(String id){
		TestRunConfiguration c = mock(TestRunConfiguration.class);
		when(c.getId()).thenReturn(id);
		return c;
	}

	private TestRunConfiguration mockUnfulfillableTestRunConfig(String id){
		TestRunConfiguration c = mock(UnfulfillableTestRunConfiguration.class);
		when(c.getId()).thenReturn(id);
		return c;
	}
	
	@UnfulfillableRequirement.Unfulfillable
	public static abstract class UnfulfillableTestRunConfiguration implements TestRunConfiguration {

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
	
	@SuiteClasses({SimpleTest.class})
	public static class SimpleSuite {
		
	}
	
	public static class SimpleTest {
		
	}
	
	public static class SimpleTest1 {
		
	}
	
	//suite that includes only other suites
	@SuiteClasses({NestedSuite.class})
	public static class ParentSuite {
		
	}
	
	@SuiteClasses({SimpleTest.class})
	public static class NestedSuite {
		
	
	}
	
	//suite that includes other suites & test classes
	@SuiteClasses({NestedSuite.class, SimpleTest1.class})
	public static class ParentSuite1 {
		
	}
}
