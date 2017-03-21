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

import static org.mockito.Mockito.mock;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.MatcherAssert.*;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;

import org.jboss.reddeer.junit.configuration.RedDeerConfigurationException;
import org.jboss.reddeer.junit.internal.configuration.RequirementConfiguration;
import org.jboss.reddeer.junit.internal.configuration.entity.PropertyBasedConfiguration;
import org.jboss.reddeer.junit.internal.configuration.reader.XMLReader;
import org.jboss.reddeer.junit.requirement.Requirement;
import org.jboss.reddeer.junit.requirement.RequirementException;
import org.jboss.reddeer.junit.test.internal.requirement.TestCustomJavaConfiguration;
import org.jboss.reddeer.junit.test.internal.requirement.TestCustomServerConfiguration;
import org.jboss.reddeer.junit.test.internal.requirement.TestCustomJavaRequirement;
import org.jboss.reddeer.junit.test.internal.requirement.TestPropertyRequirementA;
import org.junit.Before;
import org.junit.Test;


public class RequirementConfigurationTest {

	private XMLReader reader;
	
	private RequirementConfiguration configuration;
	
	@Before
	public void setup(){
		reader = mock(XMLReader.class);
	}
	
	@Test(expected=RequirementException.class)
	public void readXML_nullEnclosingRequirementClass() {
		configuration = new RequirementConfiguration(Object.class, reader);
	}
	
	@Test(expected=ClassCastException.class)
	public void readXML_notRequirementClass() {
		configuration = new RequirementConfiguration(TestEnclosingClass.class, reader);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void readXML_notProperConfigurationRequirementClass() {
		configuration = new RequirementConfiguration(TestEnclosingClass.TestInnerClass.class, reader);
	}

	@Test
	public void readXML_customConfigurationClass() {
		when(reader.getConfiguration(any())).thenReturn(
				Arrays.asList(
						TestCustomJavaConfiguration.class, 
						TestCustomJavaConfiguration.class)
				);
		configuration = new RequirementConfiguration(TestCustomJavaRequirement.CustomJavaAnnotation.class, reader);
		
		assertThat(configuration.getConfiguration().size(), is(2));
		assertTrue(configuration.getConfiguration().get(0).equals(TestCustomJavaConfiguration.class));
		assertTrue(configuration.getConfiguration().get(1).equals(TestCustomJavaConfiguration.class));
	}
	
	@Test
	public void readXML_propertyConfigurationClass() {
		PropertyBasedConfiguration property1 = mock(PropertyBasedConfiguration.class);
		PropertyBasedConfiguration property2 = mock(PropertyBasedConfiguration.class);
		when(property1.getRequirementClassName()).thenReturn("org.jboss.reddeer.junit.test.internal.requirement.TestPropertyRequirementA");
		when(property2.getRequirementClassName()).thenReturn("org.jboss.reddeer.junit.test.internal.requirement.TestPropertyRequirementA");
		when(reader.getConfiguration(any())).thenReturn(
				Arrays.asList(
						property1,
						property2)
				);
		configuration = new RequirementConfiguration(TestPropertyRequirementA.PropertyAnnotationA.class, reader);
		
		assertThat(configuration.getConfiguration().size(), is(2));
		assertThat(configuration.getConfiguration().get(0), instanceOf(PropertyBasedConfiguration.class));
		assertThat(configuration.getConfiguration().get(1), instanceOf(PropertyBasedConfiguration.class));
	}
	
	@Test(expected=RedDeerConfigurationException.class)
	public void getConfiguration_empty() {
		when(reader.getConfiguration(any())).thenReturn(new ArrayList<>());
		configuration = new RequirementConfiguration(TestCustomJavaRequirement.CustomJavaAnnotation.class, reader);
		
		configuration.getConfiguration();
	}
	
	@Test
	public void getConfiguration() {
		when(reader.getConfiguration(any())).thenReturn(
				Arrays.asList(
						TestCustomServerConfiguration.class, 
						TestCustomServerConfiguration.class)
				);
		configuration = new RequirementConfiguration(TestCustomJavaRequirement.CustomJavaAnnotation.class, reader);
		
		assertThat(configuration.getConfiguration().size(), is(2));
		assertTrue(configuration.getConfiguration().get(0).equals(TestCustomServerConfiguration.class));
	}
	
	public static class TestEnclosingClass implements Requirement<Annotation> {
		
		public static class TestInnerClass {
		}

		@Override
		public boolean canFulfill() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void fulfill() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setDeclaration(Annotation declaration) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void cleanUp() {
			// TODO Auto-generated method stub
			
		}
	}
	
}
