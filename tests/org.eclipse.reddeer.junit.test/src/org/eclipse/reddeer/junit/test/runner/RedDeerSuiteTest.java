/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.junit.test.runner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

import org.eclipse.reddeer.common.properties.RedDeerProperties;
import org.eclipse.reddeer.junit.annotation.RequirementRestriction;
import org.eclipse.reddeer.junit.internal.configuration.SuiteConfiguration;
import org.eclipse.reddeer.junit.internal.runner.NamedSuite;
import org.eclipse.reddeer.junit.internal.runner.TestsWithoutExecutionSuite;
import org.eclipse.reddeer.junit.requirement.configuration.RequirementConfigurationPool;
import org.eclipse.reddeer.junit.requirement.matcher.RequirementVersionMatcher;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.junit.test.internal.requirement.CustomJavaTestConfiguration;
import org.eclipse.reddeer.junit.test.internal.requirement.CustomJavaTestRequirement.CustomJavaAnnotation;
import org.eclipse.reddeer.junit.test.internal.requirement.CustomServerTestRequirement.CustomServerAnnotation;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Runner;
import org.junit.runners.Suite.SuiteClasses;
import org.junit.runners.model.InitializationError;

public class RedDeerSuiteTest {

	// 3x java configuration, 2x server configuration
	public static final String REDDEER_SUITE_CONFIG = "resources" + File.separator + "reddeersuitetest-config.json";
	
	// 3x java configuration, no server configuration
	public static final String REDDEER_SUITE_CONFIG2 = "resources" + File.separator + "reddeersuitetest-config2.json";

	
	@Before
	public void setUp() {
		System.clearProperty(RedDeerProperties.CONFIG_FILE.getName());
		RequirementConfigurationPool.destroyPool();
	}
	
	@After
	public void tearDown() {
		System.clearProperty(RedDeerProperties.CONFIG_FILE.getName());
	}
	
	
	@Test
	public void testSingleTestWithoutConfiguration() throws InitializationError {
		System.setProperty(RedDeerProperties.CONFIG_FILE.getName(), REDDEER_SUITE_CONFIG);		
		SuiteConfiguration config = new SuiteConfiguration(SimpleTestClass.class);
		
		List<Runner> runners = RedDeerSuite.createSuites(SimpleTestClass.class, config);

		assertThat(runners.size(), is(1));
		assertThat(runners, hasItem(new NamedSuiteMatcher("no-configuration")));
	}
	
	@Test
	public void testSimpleSuiteWithoutConfiguration() throws InitializationError {
		SuiteConfiguration config = new SuiteConfiguration(SimpleSuite.class);
		
		List<Runner> runners = RedDeerSuite.createSuites(SimpleSuite.class, config);
		
		assertThat(runners.size(), is(1));
		assertThat(runners, hasItem(new NamedSuiteMatcher("no-configuration")));
	}
	
	@Test
	public void testSimpleSuiteWithMoreTestClassesWithoutConfiguration() throws Throwable {
		assertEquals(2, getTestCount(SimpleSuite2.class));
	}
	
	@Test
	public void testNestedSuiteWithoutConfiguration() throws Throwable {
		assertEquals(1, getTestCount(ParentSuite.class));
	}
	
	@Test
	public void testNestedSuiteAndTestClassWithoutConfiguration() throws Throwable {
		assertEquals(2, getTestCount(ParentSuite2.class));
	}
	
	@Test
	public void testRequirementsSuiteWithConfiguration() throws InitializationError {
		System.setProperty(RedDeerProperties.CONFIG_FILE.getName(), REDDEER_SUITE_CONFIG);
		
		SuiteConfiguration config = new SuiteConfiguration(RequirementSuite.class);
		
		List<Runner> runners = RedDeerSuite.createSuites(RequirementSuite.class, config);

	    String java7config = "java-1.7";
		String tomcat2 = "server-tomcat2";
		assertThat(runners.size(), is(6));
		
		assertThat(runners, hasItem(new NamedSuiteMatcher(java7config)));
		assertThat(runners, hasItem(new NamedSuiteMatcher(tomcat2)));
		assertThat(runners, hasItem(new NamedSuiteMatcher("no-configuration")));
	}
	
	@Test
	public void testJavaServerTestWithConfiguration() throws Throwable {
		System.setProperty(RedDeerProperties.CONFIG_FILE.getName(), REDDEER_SUITE_CONFIG);	
		assertEquals(6, getTestCount(JavaServerTestClass.class));
	}
	
	@Test
	public void testJavaServerTestWithRestrictedConfiguration() throws Throwable {
		System.setProperty(RedDeerProperties.CONFIG_FILE.getName(), REDDEER_SUITE_CONFIG);	
		assertEquals(2, getTestCount(RestrictedJavaTestClass.class));
	}
	
	@Test
	public void testJavaServerTestWithRestrictedConfiguration2() throws Throwable {
		System.setProperty(RedDeerProperties.CONFIG_FILE.getName(), REDDEER_SUITE_CONFIG);	
		assertEquals(2, getTestCount(RestrictedJavaTestClass2.class));
	}
	
	@Test
	public void testJavaServerTestWithPartialConfiguration() throws Throwable {
		System.setProperty(RedDeerProperties.CONFIG_FILE.getName(), REDDEER_SUITE_CONFIG2);	
		assertEquals(0, getTestCount(JavaServerTestClass.class));
	}
	
	@Test
	public void testRequirementsSuite_withPartialConfig() throws Throwable {
		System.setProperty(RedDeerProperties.CONFIG_FILE.getName(), REDDEER_SUITE_CONFIG2);
		
		assertEquals(4, getTestCount(RequirementSuite.class));
	}
	
	private int getTestCount(@SuppressWarnings("rawtypes") Class suiteClass) throws Throwable {
		SuiteConfiguration config = new SuiteConfiguration(suiteClass);
		
		List<Runner> runners = RedDeerSuite.createSuites(suiteClass, config);
		short testCount = 0;
		for (Runner runner : runners) {
			if (!(runner instanceof TestsWithoutExecutionSuite)) {
				testCount += runner.getDescription().testCount();
			}
		}
		
		return testCount;
	}
	
	class NamedSuiteMatcher extends TypeSafeMatcher<Runner> {

		private String name;
		
		/**
		 * Check whether named suite contains a name
		 * 
		 * @param name name to check whether named suite contains it or not
		 */
		public NamedSuiteMatcher(String name) {
			this.name = name;
		}
		
		public void describeTo(Description description) {
			
		}

		@Override
		public boolean matchesSafely(Runner item) {
			return item instanceof NamedSuite && ((NamedSuite) item).getName().contains(name);
		}
	}

	////////////////////////////////
	// TEST CLASSES AND SUITES ////
	///////////////////////////////

	public static class SimpleTestClass {}
	
	public static class SimpleTestClass2 {}

	@CustomJavaAnnotation
	public static class JavaTestClass {}
	
	@CustomServerAnnotation
	public static class ServerTestClass {}
	
	@CustomJavaAnnotation
	@CustomServerAnnotation
	public static class JavaServerTestClass {}
	
	@CustomJavaAnnotation
	public static class RestrictedJavaTestClass {
		@RequirementRestriction
		public static Matcher getRestrictionMatcher() {
			return new RequirementVersionMatcher(CustomJavaTestConfiguration.class, "version" , ">1.7");
		}
	}
	
	@CustomJavaAnnotation
	public static class RestrictedJavaTestClass2 {
		@RequirementRestriction
		public static Matcher getRestrictionMatcher() {
			return new RequirementVersionMatcher(CustomJavaTestConfiguration.class, "version" , "[1.8;1.9]");
		}
	}

	@SuiteClasses({SimpleTestClass.class})
	public static class SimpleSuite {}
	
	@SuiteClasses({SimpleTestClass.class, SimpleTestClass2.class})
	public static class SimpleSuite2 {}
	
	//suite that includes only other suites
	@SuiteClasses({SimpleSuite.class})
	public static class ParentSuite {}
	
	//suite that includes other suites & test classes
	@SuiteClasses({SimpleSuite.class, SimpleTestClass.class})
	public static class ParentSuite2 {}
	
	@SuiteClasses({
		JavaTestClass.class,
		ServerTestClass.class,
		SimpleTestClass.class
	})
	public static class RequirementSuite {}
}
