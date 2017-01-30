/******************************************************************************* 
 * Copyright (c) 2017 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.junit.test.internal.configuration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsCollectionContaining;
import org.jboss.reddeer.common.properties.RedDeerProperties;
import org.jboss.reddeer.junit.configuration.RedDeerConfigurationException;
import org.jboss.reddeer.junit.internal.configuration.SuiteConfiguration;
import org.jboss.reddeer.junit.internal.configuration.TestClassRequirementMap;
import org.jboss.reddeer.junit.internal.configuration.TestRunConfiguration;
import org.jboss.reddeer.junit.internal.configuration.TestRunConfigurationReader;
import org.jboss.reddeer.junit.test.internal.requirement.TestCustomJavaConfiguration;
import org.jboss.reddeer.junit.test.internal.requirement.TestCustomJavaRequirement.CustomJavaAnnotation;
import org.jboss.reddeer.junit.test.internal.requirement.TestCustomServerConfiguration;
import org.jboss.reddeer.junit.test.internal.requirement.TestCustomServerRequirement.CustomServerAnnotation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.Suite.SuiteClasses;
import org.junit.runners.model.InitializationError;

public class SuiteConfigurationTest {

	private static final String RD_CONFIG_DIR;

	private SuiteConfiguration config;

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
		sbRootDir.append("internal");
		sbRootDir.append(File.separator);
		sbRootDir.append("configuration");
		sbRootDir.append(File.separator);
		RD_CONFIG_DIR = sbRootDir.toString();
	}
	
	@Before
	public void setup() throws InitializationError {
		config = new SuiteConfiguration(TestSuite.class);
	}
	
	@Test
	public void getTestRunConfigurations_noConfig() throws InitializationError {
		Map<TestClassRequirementMap, List<TestRunConfiguration>> testRuns = config.getTestRunConfigurations();
		
		assertThat(testRuns.keySet().size(), is(1));
		assertThat(testRuns.values().size(), is(1));
	}
	
	@Test
	public void getTestRunConfigurations_withConfig() throws InitializationError {
		System.setProperty(RedDeerProperties.CONFIG_FILE.getName(), 
				SuiteConfigurationTest.RD_CONFIG_DIR + "dirWithConfig" + File.separator + "requirements.xml");
		Map<TestClassRequirementMap, List<TestRunConfiguration>> testRuns = config.getTestRunConfigurations();
		
		String javaConfig = TestCustomJavaConfiguration.class.getSimpleName();
		String serverConfig = TestCustomServerConfiguration.class.getSimpleName();
		
		assertThat(testRuns.keySet().size(), is(4));
		assertThat(testRuns.keySet(), hasItem(new TestClassRequirementSetMatcher(
				asSet(CustomJavaAnnotation.class))));
		assertThat(testRuns.keySet(), hasItem(new TestClassRequirementSetMatcher(
				asSet(CustomServerAnnotation.class))));		
		assertThat(testRuns.keySet(), hasItem(new TestClassRequirementSetMatcher(
						asSet(CustomJavaAnnotation.class, CustomServerAnnotation.class))));
		assertThat(testRuns.keySet(), hasItem(new TestClassRequirementSetMatcher(
				asSet())));	
		assertThat(testRuns.values(), hasItem(new TestRunConfigurationListMatcher(
				Arrays.asList(javaConfig))));
		assertThat(testRuns.values(), hasItem(new TestRunConfigurationListMatcher(
				Arrays.asList(serverConfig))));
		
		assertThat(testRuns.values(), IsCollectionContaining.hasItem(
				new TestRunConfigurationListMatcher(
						Arrays.asList(
								javaConfig + " " + serverConfig, 
								serverConfig + " " + javaConfig))
				));
		assertThat(testRuns.values(), hasItem(new TestRunConfigurationListMatcher(
				Arrays.asList("default"))));
	}
	
	@Test
	public void getTestRunConfigurations_caching() {
		
	}
	
	@Test
	public void constructor() {
		try {
			config = new SuiteConfiguration(null);
			fail("Cannot accept null object");
		} catch (InitializationError e) {
		}
	}
	
	@Test
	public void getConfigurationFile_file() {
		System.setProperty(RedDeerProperties.CONFIG_FILE.getName(), 
				SuiteConfigurationTest.RD_CONFIG_DIR + "dirWithConfig" + File.separator + "correct_file.xml");
	
		File file = config.getConfigurationFile();
		assertThat(file.getName(), is("correct_file.xml"));
	}
	
	@Test(expected=RedDeerConfigurationException.class)
	public void getConfigurationFile_directory() {
		System.setProperty(RedDeerProperties.CONFIG_FILE.getName(), 
				SuiteConfigurationTest.RD_CONFIG_DIR + "dirWithConfig");
	
		config.getConfigurationFile();
	}
	
	@Test(expected=RedDeerConfigurationException.class)
	public void getconfigurationFile_notXMLFile() {
		System.setProperty(RedDeerProperties.CONFIG_FILE.getName(), 
				SuiteConfigurationTest.RD_CONFIG_DIR + "emptyFile");
	
		config.getConfigurationFile();
	}
	
	@Test
	public void getConfigurationFile_noRedDeerConfig() {
		File file = config.getConfigurationFile();
		assertSame(null, file);
	}
	
	@Test
	public void getConfigurationFromFile() {
		SuiteConfiguration suiteConfig = mock(SuiteConfiguration.class);
		when(suiteConfig.getConfigurationFile()).thenReturn(mock(File.class));
		when(suiteConfig.getConfigurationFromFile()).thenReturn(mock(TestRunConfigurationReader.class));
		assertThat(suiteConfig.getConfigurationFromFile(), isA(TestRunConfigurationReader.class));
		verify(suiteConfig).getConfigurationFromFile();
	}
	
	@Test 
	public void getAnnotatedTestClasses() {
		List<TestClassRequirementMap> result = config.getAnnotatedTestClasses();
		
		assertThat(result.size(), is(4));
		assertThat(result.get(0).getClasses().get(0).getSimpleName(), is("TestClass1"));
		assertThat(result.get(0).getClasses().get(1).getSimpleName(), is("TestClass5"));
		assertThat(result.get(1).getClasses().get(0).getSimpleName(), is("TestClass2"));
		assertThat(result.get(2).getClasses().get(0).getSimpleName(), is("TestClass3"));
		assertThat(result.get(3).getClasses().get(0).getSimpleName(), is("TestClass4"));
		
		Set<Class<?>> requirementConfig = result.get(0).getRequirementConfiguration();
		assertThat(requirementConfig.size(), is(1));
		assertTrue(requirementConfig.containsAll(Arrays.asList(CustomJavaAnnotation.class)));
		requirementConfig = result.get(1).getRequirementConfiguration();
		assertThat(requirementConfig.size(), is(1));
		assertTrue(requirementConfig.containsAll(Arrays.asList(CustomServerAnnotation.class)));
		requirementConfig = result.get(2).getRequirementConfiguration();
		assertThat(requirementConfig.size(), is(2));
		assertTrue(requirementConfig.containsAll(Arrays.asList(CustomServerAnnotation.class, CustomJavaAnnotation.class)));
		requirementConfig = result.get(3).getRequirementConfiguration();
		assertThat(requirementConfig.size(), is(0));
	}

	@After
	public void cleanProperties(){
		System.clearProperty(RedDeerProperties.CONFIG_FILE.getName());
	}
	
	class TestClassRequirementSetMatcher extends TypeSafeMatcher<TestClassRequirementMap> {

		private Set<Class<?>> set;
		
		public TestClassRequirementSetMatcher(Set<Class<?>> set) {
			this.set = set;
		}
		
		public void describeTo(Description description) {
			
		}

		@Override
		public boolean matchesSafely(TestClassRequirementMap item) {
			return item instanceof TestClassRequirementMap && item.equalAnnotationSet(set);
		}
	}
	
	class TestRunConfigurationListMatcher extends TypeSafeMatcher<List<TestRunConfiguration>> {

		private List<String> testRunIds;
		
		public TestRunConfigurationListMatcher(List<String> ids) {
			this.testRunIds = ids;
		}
		
		public void describeTo(Description description) {
			
		}

		@Override
		public boolean matchesSafely(List<TestRunConfiguration> item) {
			return item.stream().anyMatch(e -> this.testRunIds.contains(e.getId()));
		}
	}
	
	@SafeVarargs
	public static <T> Set<T> asSet(T... items) {
		return Stream.of(items).collect(Collectors.toCollection(HashSet::new));
	}
	
	@SuiteClasses({
		TestClass1.class,
		TestClass2.class,
		TestClass3.class,
		TestClass4.class,
		TestClass5.class
	})
	private static class TestSuite {}
	
	@CustomJavaAnnotation
	private static class TestClass1 {}
	
	@CustomServerAnnotation
	private static class TestClass2 {}
	
	@CustomJavaAnnotation
	@CustomServerAnnotation
	private static class TestClass3 {}
	
	private static class TestClass4 {}
	
	@CustomJavaAnnotation
	private static class TestClass5 {}
}
