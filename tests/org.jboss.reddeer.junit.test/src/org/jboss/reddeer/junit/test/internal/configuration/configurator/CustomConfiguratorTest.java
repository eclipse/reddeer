package org.jboss.reddeer.junit.test.internal.configuration.configurator;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

import java.util.Arrays;

import org.jboss.reddeer.junit.configuration.RedDeerConfigurationException;
import org.jboss.reddeer.junit.internal.configuration.configurator.CustomConfigurator;
import org.jboss.reddeer.junit.internal.configuration.reader.XMLReader;
import org.jboss.reddeer.junit.requirement.CustomConfiguration;
import org.jboss.reddeer.junit.requirement.Requirement;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class CustomConfiguratorTest {

	private CustomConfigurator configurator;
	
	@Test(expected=IllegalArgumentException.class)
	public void wrongArgument() {
		configurator = new CustomConfigurator(mock(XMLReader.class));
		configurator.configure(mock(Requirement.class));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void customConfig() {
		ArgumentCaptor<Object> argument = ArgumentCaptor.forClass(Object.class);
		Object configurationObject = mock(Object.class);
		XMLReader reader = createReader(configurationObject);
		configurator = new CustomConfigurator(reader);
		
		CustomConfiguration<Object> requirement = mock(CustomConfiguration.class, withSettings().extraInterfaces(Requirement.class));		
		configurator.configure((Requirement) requirement);
		
		verify(requirement).setConfiguration(argument.capture());
		assertEquals(configurationObject, argument.getValue());
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void customConfig_readerArgument() {
		ArgumentCaptor<Class> argument = ArgumentCaptor.forClass(Class.class);
		XMLReader reader = createReader(mock(Object.class));
		configurator = new CustomConfigurator(reader);
		
		CustomConfiguration requirement = mock(CustomConfiguration.class, withSettings().extraInterfaces(Requirement.class));
		when(requirement.getConfigurationClass()).thenReturn(Integer.class);
		configurator.configure((Requirement) requirement);
		
		verify(reader).getConfiguration(argument.capture());
		assertEquals(Integer.class, argument.getValue());
	}
	
	@SuppressWarnings("rawtypes")
	@Test(expected=RedDeerConfigurationException.class)
	public void customConfig_noConfigFound() {
		XMLReader reader = createReader();
		configurator = new CustomConfigurator(reader);
		
		CustomConfiguration<?> requirement = mock(CustomConfiguration.class, withSettings().extraInterfaces(Requirement.class));
		configurator.configure((Requirement) requirement);
	}
	
	@SuppressWarnings("rawtypes")
	@Test(expected=RedDeerConfigurationException.class)
	public void customConfig_moreConfigsFound() {
		XMLReader reader = createReader(mock(Object.class), mock(Object.class));
		configurator = new CustomConfigurator(reader);
		
		CustomConfiguration requirement = mock(CustomConfiguration.class, withSettings().extraInterfaces(Requirement.class));
		configurator.configure((Requirement) requirement);
	}

	@SuppressWarnings("unchecked")
	private XMLReader createReader(Object...objects) {
		XMLReader reader = mock(XMLReader.class);
		when(reader.getConfiguration(any(Class.class))).thenReturn(Arrays.asList(objects));
		return reader;
	}
}
