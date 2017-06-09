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
package org.eclipse.reddeer.junit.test.requirement.configuration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.eclipse.reddeer.common.properties.RedDeerProperties;
import org.eclipse.reddeer.junit.configuration.RedDeerConfigurationException;
import org.eclipse.reddeer.junit.requirement.configuration.RequirementConfiguration;
import org.eclipse.reddeer.junit.requirement.configuration.RequirementConfigurationPool;
import org.eclipse.reddeer.junit.test.internal.configuration.reader.JSONConfigurationReaderTest;
import org.eclipse.reddeer.junit.test.requirement.configuration.resources.ComplexConfiguration;
import org.eclipse.reddeer.junit.test.requirement.configuration.resources.SimpleConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RequirementConfigurationPoolTest {

	@Before
	public void setup() {
		RequirementConfigurationPool.destroyPool();
	}
	
	@Test(expected=RedDeerConfigurationException.class)
	public void testGetConfigurationsFromConfigPoolWithNonexistingConfigFile() {
		System.setProperty(RedDeerProperties.CONFIG_FILE.getName(), JSONConfigurationReaderTest.NONEXISTING_FILE);
		RequirementConfigurationPool.getInstance();
	}
	
	@Test(expected=RedDeerConfigurationException.class)
	public void testGetNonexistingConfigurationFile() {
		System.setProperty(RedDeerProperties.CONFIG_FILE.getName(), JSONConfigurationReaderTest.NONEXISTING_FILE);
		RequirementConfigurationPool.getConfigurationFile();
		RequirementConfigurationPool.getInstance();
	}
	
	@Test(expected=RedDeerConfigurationException.class)
	public void testGetInvalidConfigurationFile() {
		System.setProperty(RedDeerProperties.CONFIG_FILE.getName(), JSONConfigurationReaderTest.INVALID_FILE);
		RequirementConfigurationPool.getConfigurationFile();
		RequirementConfigurationPool.getInstance();
	}
	
	@Test
	public void testGetConfigurationFile() {
		System.setProperty(RedDeerProperties.CONFIG_FILE.getName(), JSONConfigurationReaderTest.CONFIG_FILE);
		assertNotNull("Configuration file should not be null", RequirementConfigurationPool.getConfigurationFile());
	}
	
	@Test
	public void getRequirementConfigurationsFromPool() {
		System.setProperty(RedDeerProperties.CONFIG_FILE.getName(), JSONConfigurationReaderTest.CONFIG_FILE);
		int configAmount = RequirementConfigurationPool.getInstance().getRequirementsConfigurations().size();
		assertThat("There should be 8 configurations in configuration pool, but there is/are " + configAmount + " configs", 
				configAmount == 8);
	}
	
	@Test
	public void getSimpleRequirementConfigurationsFromPool() {
		System.setProperty(RedDeerProperties.CONFIG_FILE.getName(), JSONConfigurationReaderTest.CONFIG_FILE);
		int size = RequirementConfigurationPool.getInstance().getConfigurations(SimpleConfiguration.class).size();
		assertThat("There should be 5 simple requirement configurations, but there are " + size, size == 5);
	}
	
	@Test
	public void getComplexRequirementConfigurationsFromPool() {
		System.setProperty(RedDeerProperties.CONFIG_FILE.getName(), JSONConfigurationReaderTest.CONFIG_FILE);
		List<RequirementConfiguration> list = RequirementConfigurationPool.getInstance().getConfigurations(ComplexConfiguration.class);
		assertThat("There should be 3 complex requirement configurations, but there are " + list.size(), list.size() == 3);
	}
	
	@After
	public void cleanup() {
		System.clearProperty(RedDeerProperties.CONFIG_FILE.getName());
		RequirementConfigurationPool.destroyPool();
	}
}
