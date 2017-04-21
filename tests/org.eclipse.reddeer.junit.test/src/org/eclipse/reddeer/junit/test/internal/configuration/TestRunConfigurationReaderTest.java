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
package org.eclipse.reddeer.junit.test.internal.configuration;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.*;

import org.eclipse.reddeer.junit.configuration.RedDeerConfigurationException;
import org.eclipse.reddeer.junit.internal.configuration.RequirementConfiguration;
import org.eclipse.reddeer.junit.internal.configuration.TestRunConfigurationReader;
import org.eclipse.reddeer.junit.internal.configuration.entity.PropertyBasedConfiguration;
import org.eclipse.reddeer.junit.internal.configuration.reader.XMLReader;
import org.eclipse.reddeer.junit.test.internal.requirement.TestCustomJavaConfiguration;
import org.eclipse.reddeer.junit.test.internal.requirement.TestCustomJavaRequirement.CustomJavaAnnotation;
import org.eclipse.reddeer.junit.test.internal.requirement.TestCustomServerConfiguration;
import org.eclipse.reddeer.junit.test.internal.requirement.TestCustomServerRequirement.CustomServerAnnotation;
import org.eclipse.reddeer.junit.test.internal.requirement.TestPropertyRequirementA.PropertyAnnotationA;
import org.eclipse.reddeer.junit.test.internal.requirement.TestPropertyRequirementB.PropertyAnnotationB;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runners.Suite.SuiteClasses;

public class TestRunConfigurationReaderTest {
	
	private TestRunConfigurationReader reader; 
	
	private List<Annotation> requirements = new ArrayList<>();
	
	@BeforeClass
	public static void setupBefore() {
		
	}
	@Test
	public void test_getConfigurationList_customConfig() {
		XMLReader xmlReader = mock(XMLReader.class);
		TestCustomJavaConfiguration config1 = mock(TestCustomJavaConfiguration.class);
		when(xmlReader.getConfiguration(TestCustomJavaConfiguration.class)).thenReturn(Arrays.asList(config1));
		requirements.addAll(getClassAnnotations(TestClass1.class));
		
		reader = new TestRunConfigurationReader(xmlReader, requirements);
		
		assertThat(reader.getConfigurationList().size(), is(1));
		RequirementConfiguration reqConf1 = reader.getConfigurationList().iterator().next();
		assertThat(reqConf1.getConfiguration().iterator().next(), instanceOf(TestCustomJavaConfiguration.class));
		assertThat(reqConf1.getConfiguration().size(), is(1));
	}
	
	@Test(expected=RedDeerConfigurationException.class)
	public void test_getConfigurationList_noConfig() {
		XMLReader xmlReader = mock(XMLReader.class);
		PropertyBasedConfiguration config1 = mock(PropertyBasedConfiguration.class);
		when(xmlReader.getConfiguration(PropertyBasedConfiguration.class)).thenReturn(Arrays.asList(config1));
		requirements.addAll(getClassAnnotations(TestClass1.class));
		
		reader = new TestRunConfigurationReader(xmlReader, requirements);
		
		assertThat(reader.getConfigurationList().size(), is(1));
		RequirementConfiguration reqConf1 = reader.getConfigurationList().iterator().next();
		reqConf1.getConfiguration();
	}
	
	@Test
	public void test_getConfigurationList_propertyBasedConfig() {
		XMLReader xmlReader = mock(XMLReader.class);
		PropertyBasedConfiguration config1 = mock(PropertyBasedConfiguration.class);
		when(config1.getRequirementClassName()).thenReturn("org.eclipse.reddeer.junit.test.internal.requirement.TestPropertyRequirementA");
		when(xmlReader.getConfiguration(any())).thenReturn(Arrays.asList(config1));
		requirements.addAll(getClassAnnotations(TestClass3.class));
				
		reader = new TestRunConfigurationReader(xmlReader, requirements);
		
		assertThat(reader.getConfigurationList().size(), is(1));
		RequirementConfiguration reqConf1 = reader.getConfigurationList().iterator().next();
		assertTrue(reqConf1.getConfiguration().iterator().next() instanceof PropertyBasedConfiguration);
		assertThat(reqConf1.getConfiguration().size(), is(1));
	}
	
	@Test
	public void test_getConfigurationList_multipleConfigs() {
		XMLReader xmlReader = mock(XMLReader.class);
		TestCustomJavaConfiguration config1 = mock(TestCustomJavaConfiguration.class);
		TestCustomServerConfiguration config2 = mock(TestCustomServerConfiguration.class);
		PropertyBasedConfiguration property1 = mock(PropertyBasedConfiguration.class);
		when(property1.getRequirementClassName()).thenReturn("org.eclipse.reddeer.junit.test.internal.requirement.TestPropertyRequirementA");
		when(xmlReader.getConfiguration(TestCustomJavaConfiguration.class)).thenReturn(Arrays.asList(config1));
		when(xmlReader.getConfiguration(TestCustomServerConfiguration.class)).thenReturn(Arrays.asList(config2));
		when(xmlReader.getConfiguration(PropertyBasedConfiguration.class)).thenReturn(Arrays.asList(property1));
		requirements.addAll(getClassAnnotations(TestSuite.class));
		
		reader = new TestRunConfigurationReader(xmlReader, requirements);
		
		assertThat(reader.getConfigurationList().size(), is(3));
		RequirementConfiguration reqConf1 = reader.getConfigurationList().iterator().next();
		RequirementConfiguration reqConf2 = reader.getConfigurationList().iterator().next();
		RequirementConfiguration reqConf3 = reader.getConfigurationList().iterator().next();
		assertThat(reqConf1, instanceOf(RequirementConfiguration.class));
		assertThat(reqConf2, instanceOf(RequirementConfiguration.class));
		assertThat(reqConf3, instanceOf(RequirementConfiguration.class));
		assertThat(reqConf1.getConfiguration().size(), is(1));
		assertThat(reqConf2.getConfiguration().size(), is(1));
		assertThat(reqConf3.getConfiguration().size(), is(1));
	}
	
	private static List<Annotation> getClassAnnotations(Class<?> clazz) {
		List<Annotation> annotations = new ArrayList<>();
		SuiteClasses suite = clazz.getAnnotation(SuiteClasses.class);
		if (suite != null) {
			for (Class<?> clazzy : suite.value()) {
				annotations.addAll(getClassAnnotations(clazzy));
			}
		} else {
			for (Annotation annotation : clazz.getAnnotations()) {
				annotations.add(annotation);
			}
		}
		return annotations;
	}
	
	@CustomJavaAnnotation
	private static class TestClass1 {}
	
	@CustomServerAnnotation
	private static class TestClass2 {}
	
	@PropertyAnnotationA
	private static class TestClass3 {}
	
	@PropertyAnnotationA
	@PropertyAnnotationB
	private static class TestClass4 {}
	
	@SuiteClasses({
		TestClass1.class,
		TestClass2.class,
		TestClass3.class
	})
	private static class TestSuite {}
}
