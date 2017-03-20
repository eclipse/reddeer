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

import java.io.File;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.jboss.reddeer.common.properties.RedDeerProperties;
import org.jboss.reddeer.junit.internal.configuration.SuiteConfiguration;
import org.jboss.reddeer.junit.internal.runner.NamedSuite;
import org.jboss.reddeer.junit.internal.runner.TestsWithoutExecutionSuite;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.junit.test.internal.requirement.TestCustomJavaConfiguration;
import org.jboss.reddeer.junit.test.internal.requirement.TestCustomServerConfiguration;
import org.jboss.reddeer.junit.test.internal.requirement.TestCustomJavaRequirement.CustomJavaAnnotation;
import org.jboss.reddeer.junit.test.internal.requirement.TestCustomServerRequirement.CustomServerAnnotation;
import org.jboss.reddeer.junit.test.internal.requirement.TestPropertyRequirementA.PropertyAnnotationA;
import org.jboss.reddeer.junit.test.internal.requirement.TestPropertyRequirementB.PropertyAnnotationB;
import org.jboss.reddeer.junit.test.runner.UnfulfillableRequirement.Unfulfillable;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.Runner;
import org.junit.runners.Suite.SuiteClasses;
import org.junit.runners.model.InitializationError;

public class RedDeerSuiteTest {

	private static final String LOCATIONS_ROOT_DIR;
	
	static{
		StringBuffer sbRootDir = new StringBuffer("");
		sbRootDir.append("resources");
		sbRootDir.append(File.separator);
		sbRootDir.append("org");
		sbRootDir.append(File.separator);
		sbRootDir.append("jboss");
		sbRootDir.append(File.separator);
		sbRootDir.append("reddeer");
		sbRootDir.append(File.separator);
		sbRootDir.append("junit");
		sbRootDir.append(File.separator);
		sbRootDir.append("test");
		sbRootDir.append(File.separator);
		sbRootDir.append("runner");
		LOCATIONS_ROOT_DIR = sbRootDir.toString();
	}
	
	@BeforeClass
	public static void setUp() {
		System.clearProperty(RedDeerProperties.CONFIG_FILE.getName());
	}
	
	@After
	public void tearDown() {
		System.clearProperty(RedDeerProperties.CONFIG_FILE.getName());
	}
	
	@Test
	public void getChildren_noConfiguration() throws InitializationError {
		SuiteConfiguration config = new SuiteConfiguration(SimpleTest.class);
		
		List<Runner> runners = RedDeerSuite.createSuites(SimpleSuite.class, config);
		
		// will create one test run defined by NullTestRunConfiguration
		assertThat(runners.size(), is(1));
	}
	
	@Test
	public void getChildren_suiteWithRequirements() throws InitializationError {
		System.setProperty(RedDeerProperties.CONFIG_FILE.getName(), 
				LOCATIONS_ROOT_DIR + File.separator + "requirements.xml");
		
		SuiteConfiguration config = new SuiteConfiguration(CustomRequirementsSuite.class);
		
		List<Runner> runners = RedDeerSuite.createSuites(CustomRequirementsSuite.class, config);

		assertThat(runners.size(), is(4));
		
		String server = TestCustomServerConfiguration.class.getSimpleName();
		String java = TestCustomJavaConfiguration.class.getSimpleName();
		
		boolean hasComplexSuite = false;
		for (Runner run : runners) {
			String suiteName = run.getDescription().getDisplayName();
			if ((server + " " + java).equalsIgnoreCase(suiteName) ||
				(java + " " + server).equalsIgnoreCase(suiteName)) {
				hasComplexSuite = true;
				break;
			}
		}
		
		assertTrue("Test runners do not contain suite with name " + server + " " + java + " or vice versa", hasComplexSuite);
		assertThat(runners, hasItem(new NamedSuiteMatcher(server)));
		assertThat(runners, hasItem(new NamedSuiteMatcher(java)));
		assertThat(runners, hasItem(new NamedSuiteMatcher("default")));
	}
	
	@Test
	public void getChildren_suite() throws InitializationError {
		SuiteConfiguration config = new SuiteConfiguration(SimpleSuite.class);
		
		List<Runner> runners = RedDeerSuite.createSuites(SimpleSuite.class, config);
		
		assertThat(runners, hasItem(new NamedSuiteMatcher("default")));
	}
	
	@Test
	public void getChildren_nestedSuite() throws InitializationError {
		SuiteConfiguration config = new SuiteConfiguration(NestedSuite.class);
		
		List<Runner> runners = RedDeerSuite.createSuites(SimpleSuite.class, config);
		
		assertThat(runners, hasItem(new NamedSuiteMatcher("default")));
	}

	@Test
	public void getChildren_test() throws InitializationError {
		System.setProperty(RedDeerProperties.CONFIG_FILE.getName(), 
				LOCATIONS_ROOT_DIR + File.separator + "requirements.xml");		
		SuiteConfiguration config = new SuiteConfiguration(SimpleTest.class);
		
		List<Runner> runners = RedDeerSuite.createSuites(SimpleTest.class, config);

		assertThat(runners.size(), is(1));
		assertThat(runners, hasItem(new NamedSuiteMatcher("default")));
		
		config = null;
		runners.clear();
		
		config = new SuiteConfiguration(CustomRequirementTest1.class);
		
		runners = RedDeerSuite.createSuites(CustomRequirementTest1.class, config);

		assertThat(runners.size(), is(1));
		assertThat(runners, hasItem(new NamedSuiteMatcher(TestCustomJavaConfiguration.class.getSimpleName())));
	}
	
	@Test
	public void testsWithoutExecution_suite() throws InitializationError {
		System.setProperty(RedDeerProperties.CONFIG_FILE.getName(), 
				LOCATIONS_ROOT_DIR + File.separator + "requirements.xml");
		SuiteConfiguration config = new SuiteConfiguration(UnfillableSuite.class);
		
		List<Runner> runners = RedDeerSuite.createSuites(UnfillableSuite.class, config);
		
		assertThat(runners.size(), is(3));
		assertThat(runners, hasItem(new NamedSuiteMatcher("default")));
		assertThat(runners, hasItem(new NamedSuiteMatcher(TestCustomJavaConfiguration.class.getSimpleName())));
		
		Runner runner = runners.get(runners.size()-1);
		assertTrue(runner.getClass().toString(), runner instanceof TestsWithoutExecutionSuite);
	}

	@Test
	public void testsWithoutExecution_test() throws InitializationError {
		SuiteConfiguration config = new SuiteConfiguration(SimpleTest.class);
		
		List<Runner> runners = RedDeerSuite.createSuites(SimpleTest.class, config);
		
		assertThat(runners.size(), is(1));
		assertThat(runners, hasItem(new NamedSuiteMatcher("default")));
		
		Runner runner = runners.get(runners.size()-1);
		assertFalse(runner instanceof TestsWithoutExecutionSuite);
		
		config = new SuiteConfiguration(UnfillableTestClass.class);
		runners = RedDeerSuite.createSuites(UnfillableTestClass.class, config);
		
		assertThat(runners.size(), is(2));
		assertThat(runners, hasItem(new NamedSuiteMatcher("default")));
		
		runner = runners.get(runners.size()-1);
		assertTrue(runner instanceof TestsWithoutExecutionSuite);
	}
	
	@Test
	public void testNestedSuite() throws Throwable {
		assertEquals(1, getTestCount(ParentSuite.class));
	}
	
	@Test
	public void testNestedSuiteWithTests() throws Throwable {
		assertEquals(2, getTestCount(ParentSuite1.class));
	}
	
	@Test
	public void testRequirementsSuite() throws Throwable {
		assertEquals(4, getTestCount(CustomRequirementsSuite.class));
	}
	
	@Test
	public void testRequirementsSuite_withConfig() throws Throwable {
		System.setProperty(RedDeerProperties.CONFIG_FILE.getName(), 
				LOCATIONS_ROOT_DIR + File.separator + "requirements2.xml");
		
		assertEquals(9, getTestCount(CustomRequirementsSuite.class));
	}
	
	@Test
	public void testPropertyRequirementsSuite() throws Throwable {
		assertEquals(4, getTestCount(PropertyRequirementsSuite.class));
	}

	@Test
	public void testPropertyRequirementsSuite_withConfig() throws Throwable {
		System.setProperty(RedDeerProperties.CONFIG_FILE.getName(), 
				LOCATIONS_ROOT_DIR + File.separator + "requirements3.xml");
		
		assertEquals(6, getTestCount(PropertyRequirementsSuite.class));
	}
	
	@Test
	public void testMixedRequirementsSuite() throws Throwable {
		assertEquals(6, getTestCount(MixedRequirementsSuite.class));
		// set rd.config property
		System.setProperty(RedDeerProperties.CONFIG_FILE.getName(), 
				LOCATIONS_ROOT_DIR + File.separator + "requirements4.xml");
		assertEquals(23, getTestCount(MixedRequirementsSuite.class));
	}
	
	@Test
	public void testUnfillableSuite() throws Throwable {
		assertEquals(3, getTestCount(UnfillableSuite.class));
	}
	
	@Test
	public void testMixedTestClasses() throws Throwable {
		System.setProperty(RedDeerProperties.CONFIG_FILE.getName(), 
				LOCATIONS_ROOT_DIR + File.separator + "requirements4.xml");
		assertEquals(2, getTestCount(MixedTest1.class));
		assertEquals(4, getTestCount(MixedTest2.class));
		assertEquals(4, getTestCount(MixedTest3.class));
		assertEquals(8, getTestCount(MixedTest4.class));
		assertEquals(8, getTestCount(MixedTest5.class));
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
	
	// TEST SUITES
	
	@SuiteClasses({SimpleTest.class})
	public static class SimpleSuite {
		
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
	
	@SuiteClasses({
		CustomRequirementTest1.class,
		CustomRequirementTest2.class,
		CustomRequirementTest3.class,
		NoRequirementTest4.class
	})
	public static class CustomRequirementsSuite {
		
	}
	
	@SuiteClasses({
		SimpleTest.class,
		SimpleTest1.class,
		UnfillableTestClass.class,
		CustomRequirementTest1.class
	})
	public static class UnfillableSuite {}
	
	@SuiteClasses({
		PropertyTest1.class,
		PropertyTest2.class,
		PropertyTest3.class,
		NoRequirementTest4.class
	})
	public static class PropertyRequirementsSuite {

	}
	
	@SuiteClasses({
		MixedTest1.class,
		MixedTest2.class,
		MixedTest3.class,
		MixedTest4.class,
		MixedTest5.class,
		NoRequirementTest4.class
	})
	public static class MixedRequirementsSuite {}
	
	// TEST CLASSES
	
	@Unfulfillable
	public static class UnfillableTestClass {}
	
	public static class SimpleTest {}
	
	public static class SimpleTest1 {}
	
	@CustomJavaAnnotation
	public static class CustomRequirementTest1 {}
	
	@CustomServerAnnotation
	public static class CustomRequirementTest2 {}
	
	@CustomJavaAnnotation
	@CustomServerAnnotation
	public static class CustomRequirementTest3 {}
	
	public static class NoRequirementTest4 {}
	
	@PropertyAnnotationA
	public static class PropertyTest1 {}
	
	@PropertyAnnotationB
	public static class PropertyTest2 {}
	
	@PropertyAnnotationA
	@PropertyAnnotationB
	public static class PropertyTest3 {}
	
	@PropertyAnnotationA
	@CustomJavaAnnotation
	public static class MixedTest1 {}
	
	@PropertyAnnotationA
	@PropertyAnnotationB
	@CustomJavaAnnotation
	public static class MixedTest2 {}
	
	@PropertyAnnotationA
	@PropertyAnnotationB
	@CustomJavaAnnotation
	public static class MixedTest3 {}
	
	@PropertyAnnotationB
	@CustomJavaAnnotation
	@CustomServerAnnotation
	public static class MixedTest4 {}
	
	@PropertyAnnotationA
	@PropertyAnnotationB
	@CustomJavaAnnotation
	@CustomServerAnnotation
	public static class MixedTest5 {}
	
}
