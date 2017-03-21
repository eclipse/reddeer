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
package org.jboss.reddeer.junit.test.internal.configuration.configurator;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.junit.configuration.RedDeerConfigurationException;
import org.jboss.reddeer.junit.internal.configuration.configurator.CustomConfigurator;
import org.jboss.reddeer.junit.internal.configuration.entity.PropertyBasedConfiguration;
import org.jboss.reddeer.junit.requirement.Requirement;
import org.jboss.reddeer.junit.test.internal.requirement.TestCustomJavaConfiguration;
import org.jboss.reddeer.junit.test.internal.requirement.TestCustomServerConfiguration;
import org.jboss.reddeer.junit.test.internal.requirement.TestCustomJavaRequirement;
import org.jboss.reddeer.junit.test.internal.requirement.TestCustomServerRequirement;
import org.jboss.reddeer.junit.test.internal.requirement.TestPropertyRequirementA;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class CustomConfiguratorTest {

	private CustomConfigurator configurator;
	private List<Object> configs = new ArrayList<>();
	
	@Test(expected=IllegalArgumentException.class)
	public void wrongArgument() {
		configurator = new CustomConfigurator(configs);
		configurator.configure(mock(Requirement.class));
	}

	@Test
	public void customConfig() {
		ArgumentCaptor<Object> argument = ArgumentCaptor.forClass(Object.class);
		Object configurationObject = mock(TestCustomJavaConfiguration.class);
		configs.add(configurationObject);
		configurator = new CustomConfigurator(configs);
			
		TestCustomJavaRequirement requirement = mock(TestCustomJavaRequirement.class);
		when(requirement.getConfigurationClass()).thenReturn(TestCustomJavaConfiguration.class);
		configurator.configure(requirement);
		
		verify(requirement).setConfiguration((TestCustomJavaConfiguration)argument.capture());
		assertEquals(configurationObject, argument.getValue());
	}
	
	@Test(expected=RedDeerConfigurationException.class)
	public void customConfig_noConfigFound() {
		configs.add(mock(TestCustomServerConfiguration.class));
		configurator = new CustomConfigurator(configs);
		
		TestCustomJavaRequirement requirement = mock(TestCustomJavaRequirement.class);
		when(requirement.getConfigurationClass()).thenReturn(TestCustomJavaConfiguration.class);
		configurator.configure(requirement);
	}
	
	@Test(expected=RedDeerConfigurationException.class)
	public void customConfig_noConfigFits() {
		configs.add(mock(TestCustomJavaConfiguration.class));
		configs.add(mock(PropertyBasedConfiguration.class));
		configurator = new CustomConfigurator(configs);
		
		TestCustomServerRequirement requirement = mock(TestCustomServerRequirement.class);
		when(requirement.getConfigurationClass()).thenReturn(TestCustomServerConfiguration.class);
		configurator.configure(requirement);
	}

	@Test(expected=RedDeerConfigurationException.class)
	public void customConfig_noCustomConfig() {
		configs.add(mock(TestPropertyRequirementA.class));
		configurator = new CustomConfigurator(configs);
		
		TestCustomJavaRequirement requirement = mock(TestCustomJavaRequirement.class);
		when(requirement.getConfigurationClass()).thenReturn(TestCustomJavaConfiguration.class);
		configurator.configure(requirement);
	}
	
	@Test
	public void customConfig_moreConfigs() {
		configs.add(mock(TestCustomJavaConfiguration.class));
		configs.add(mock(TestCustomServerConfiguration.class));
		configurator = new CustomConfigurator(configs);
		
		TestCustomJavaRequirement requirement = mock(TestCustomJavaRequirement.class);
		when(requirement.getConfigurationClass()).thenReturn(TestCustomJavaConfiguration.class);
		TestCustomServerRequirement requirement2 = mock(TestCustomServerRequirement.class);
		when(requirement2.getConfigurationClass()).thenReturn(TestCustomServerConfiguration.class);
		configurator.configure(requirement);
		configurator.configure(requirement2);
	}
}
