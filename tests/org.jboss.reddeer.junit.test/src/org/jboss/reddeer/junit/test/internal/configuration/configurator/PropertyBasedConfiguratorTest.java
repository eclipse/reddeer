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
package org.jboss.reddeer.junit.test.internal.configuration.configurator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.junit.configuration.RedDeerConfigurationException;
import org.jboss.reddeer.junit.internal.configuration.configurator.PropertyBasedConfigurator;
import org.jboss.reddeer.junit.internal.configuration.entity.Property;
import org.jboss.reddeer.junit.internal.configuration.entity.PropertyBasedConfiguration;
import org.jboss.reddeer.junit.internal.configuration.reader.XMLReader;
import org.jboss.reddeer.junit.internal.configuration.setter.ConfigurationSetter;
import org.jboss.reddeer.junit.requirement.PropertyConfiguration;
import org.jboss.reddeer.junit.requirement.Requirement;
import org.jboss.reddeer.junit.test.internal.requirement.TestCustomJavaConfiguration;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class PropertyBasedConfiguratorTest {

	private PropertyBasedConfigurator configurator;
	
	@Test(expected=IllegalArgumentException.class)
	public void wrongArgument() {
		List<Object> configurations = new ArrayList<>();
		configurations.add(mock(PropertyBasedConfiguration.class));
		configurator = new PropertyBasedConfigurator(configurations, mock(ConfigurationSetter.class));
		configurator.configure(mock(Requirement.class));
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void propertyBasedConfig() {
		ArgumentCaptor<Requirement> requirementArgument = ArgumentCaptor.forClass(Requirement.class);
		ArgumentCaptor<PropertyBasedConfiguration> configurationArgument = ArgumentCaptor.forClass(PropertyBasedConfiguration.class);
		ConfigurationSetter setter = mock(ConfigurationSetter.class);
		List<Object> configurations = new ArrayList<>();
		PropertyBasedConfiguration config1 = mock(PropertyBasedConfiguration.class);
		PropertyBasedConfiguration config2 = mock(PropertyBasedConfiguration.class);
		TestCustomJavaConfiguration config3 = mock(TestCustomJavaConfiguration.class);
		when(config1.getRequirementClassName()).thenReturn(PropertyBasedRequirementB.class.getCanonicalName());
		when(config2.getRequirementClassName()).thenReturn(PropertyBasedRequirementA.class.getCanonicalName());
		configurations.add(config1);
		configurations.add(config2);
		configurations.add(config3);
		configurator = new PropertyBasedConfigurator(configurations, setter);

		PropertyBasedRequirementA requirement = new PropertyBasedRequirementA();
		configurator.configure(requirement);

		verify(setter).set(requirementArgument.capture(), configurationArgument.capture());
		assertThat((PropertyBasedRequirementA) requirementArgument.getValue(), is(requirement));
		assertThat(configurationArgument.getValue(), isA(PropertyBasedConfiguration.class));
	}
	
	@Test(expected=RedDeerConfigurationException.class)
	public void propertyBasedConfig_noConfigFound() {
		configurator = new PropertyBasedConfigurator(new ArrayList<>(), mock(ConfigurationSetter.class));
		configurator.configure(new PropertyBasedRequirementB());
	}
	
	@Test(expected=RedDeerConfigurationException.class)
	public void propertyBasedConfig_onlyCustomConfigFound() {
		TestCustomJavaConfiguration customConfig = mock(TestCustomJavaConfiguration.class);
		List<Object> configurations = new ArrayList<>();
		configurations.add(customConfig);
		
		configurator = new PropertyBasedConfigurator(configurations, mock(ConfigurationSetter.class));
		configurator.configure(new PropertyBasedRequirementA());
	}
	
	@Test(expected=RedDeerConfigurationException.class)
	public void propertyBasedConfig_notConfigForClassFound() {
		PropertyBasedConfiguration config = mock(PropertyBasedConfiguration.class);
		List<Property> properties = new ArrayList<>();
		properties.add(new Property("key1", "value1"));
		// mock the behavior of config object to simulate some concrete configuration object
		when(config.getProperties()).thenReturn(properties);
		List<Object> configurations = new ArrayList<>();
		configurations.add(config);
		
		configurator = new PropertyBasedConfigurator(configurations, new ConfigurationSetter());
		configurator.configure(new PropertyBasedRequirementB());
	}
	
	public void propertyBasedConfig_moreConfigsFoundForSameClass() {
		List<Object> configurations = new ArrayList<>();
		configurations.add(mock(PropertyBasedConfiguration.class));
		configurations.add(mock(PropertyBasedConfiguration.class));
		
		configurator = new PropertyBasedConfigurator(configurations, mock(ConfigurationSetter.class));
		configurator.configure(new PropertyBasedRequirementA());
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	private XMLReader createReader(Class<?>...classes) {
		List<PropertyBasedConfiguration> configs = new ArrayList<PropertyBasedConfiguration>();
		
		for (Class clazz : classes){
			PropertyBasedConfiguration config = mock(PropertyBasedConfiguration.class);
			//when(config.getRequirementClassName()).thenReturn(clazz.getCanonicalName());
			configs.add(config);
		}
		
		XMLReader reader = mock(XMLReader.class);
		when(reader.getConfiguration(any(Class.class))).thenReturn(configs);
		return reader;
	}
	
	public static class PropertyBasedRequirementA implements Requirement<Annotation>, PropertyConfiguration {

	    private String key1;
	    
	    public String getKey1() {
	    	return key1;
	    }
		
	    public void setKey1(String value) {
	    	this.key1 = value;
	    }
	    
		public boolean canFulfill() {
			return false;
		}

		public void fulfill() {
		}
		
		@Override
		public void setDeclaration(Annotation declaration) {
		}

		@Override
		public void cleanUp() {
			// TODO Auto-generated method stub
			
		}
	}
	
	public static class PropertyBasedRequirementB implements Requirement<Annotation>, PropertyConfiguration {

		public boolean canFulfill() {
			return false;
		}

		public void fulfill() {
		}
		
		@Override
		public void setDeclaration(Annotation declaration) {
		}

		@Override
		public void cleanUp() {
			// TODO Auto-generated method stub
			
		}
	}
	
	public static class PropertyBasedRequirementC implements Requirement<Annotation>, PropertyConfiguration {

		public boolean canFulfill() {
			return false;
		}

		public void fulfill() {
		}
		
		@Override
		public void setDeclaration(Annotation declaration) {
		}

		@Override
		public void cleanUp() {
			// TODO Auto-generated method stub
			
		}
	}
}
