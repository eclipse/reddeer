package org.jboss.reddeer.junit.internal.configuration.configurator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.junit.configuration.RedDeerConfigurationException;
import org.jboss.reddeer.junit.internal.configuration.entity.PropertyBasedConfiguration;
import org.jboss.reddeer.junit.internal.configuration.reader.XMLReader;
import org.jboss.reddeer.junit.internal.configuration.setter.ConfigurationSetter;
import org.jboss.reddeer.junit.requirement.PropertyConfiguration;
import org.jboss.reddeer.junit.requirement.Requirement;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class PropertyBasedConfiguratorTest {

	private PropertyBasedConfigurator configurator;
	
	@Test(expected=IllegalArgumentException.class)
	public void wrongArgument() {
		configurator = new PropertyBasedConfigurator(mock(XMLReader.class), mock(ConfigurationSetter.class));
		configurator.configure(mock(Requirement.class));
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void propertyBasedConfig() {
		ArgumentCaptor<Requirement> requirementArgument = ArgumentCaptor.forClass(Requirement.class);
		ArgumentCaptor<PropertyBasedConfiguration> configurationArgument = ArgumentCaptor.forClass(PropertyBasedConfiguration.class);
		ConfigurationSetter setter = mock(ConfigurationSetter.class);
		
		XMLReader reader = createReader(PropertyBasedRequirementA.class, PropertyBasedRequirementB.class, PropertyBasedRequirementC.class);
		configurator = new PropertyBasedConfigurator(reader, setter);

		PropertyBasedRequirementA requirement = new PropertyBasedRequirementA(); 
		configurator.configure(requirement);

		verify(setter).set(requirementArgument.capture(), configurationArgument.capture());
		assertThat((PropertyBasedRequirementA) requirementArgument.getValue(), is(requirement));
		assertThat(configurationArgument.getValue(), is(PropertyBasedConfiguration.class));
	}
	
	@Test
	public void propertyBasedConfig_caching() {
		XMLReader reader = createReader(PropertyBasedRequirementA.class, PropertyBasedRequirementB.class, PropertyBasedRequirementC.class);
		configurator = new PropertyBasedConfigurator(reader, mock(ConfigurationSetter.class));

		PropertyBasedRequirementA requirement = new PropertyBasedRequirementA(); 
		configurator.configure(requirement);
		
		Object returned1 = configurator.getPropertyConfigurations().get(PropertyBasedRequirementA.class);
		Object returned2 = configurator.getPropertyConfigurations().get(PropertyBasedRequirementA.class);

		assertSame(returned1, returned2);
	}
	
	@Test(expected=RedDeerConfigurationException.class)
	public void propertyBasedConfig_noConfigFound() {
		XMLReader reader = createReader();
		
		configurator = new PropertyBasedConfigurator(reader, mock(ConfigurationSetter.class));
		configurator.configure(new PropertyBasedRequirementB());
	}
	
	@Test(expected=RedDeerConfigurationException.class)
	public void propertyBasedConfig_notConfigForClassFound() {
		XMLReader reader = createReader(PropertyBasedRequirementA.class);
		
		configurator = new PropertyBasedConfigurator(reader, mock(ConfigurationSetter.class));
		configurator.configure(new PropertyBasedRequirementB());
	}
	
	@Test(expected=RedDeerConfigurationException.class)
	public void propertyBasedConfig_moreConfigsFoundForSameClass() {
		XMLReader reader = createReader(PropertyBasedRequirementA.class, PropertyBasedRequirementA.class);
		
		configurator = new PropertyBasedConfigurator(reader, mock(ConfigurationSetter.class));
		configurator.configure(new PropertyBasedRequirementB());
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private XMLReader createReader(Class<?>...classes) {
		List<PropertyBasedConfiguration> configs = new ArrayList<PropertyBasedConfiguration>();
		
		for (Class clazz : classes){
			PropertyBasedConfiguration config = mock(PropertyBasedConfiguration.class);
			when(config.getRequirementClass()).thenReturn(clazz);
			configs.add(config);
		}
		
		XMLReader reader = mock(XMLReader.class);
		when(reader.getConfiguration(any(Class.class))).thenReturn(configs);
		return reader;
	}
	
	public static class PropertyBasedRequirementA implements Requirement<Annotation>, PropertyConfiguration {

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
