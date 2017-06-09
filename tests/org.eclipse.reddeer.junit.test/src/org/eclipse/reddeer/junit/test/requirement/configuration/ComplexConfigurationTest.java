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
import static org.hamcrest.Matchers.is;

import java.io.File;

import org.eclipse.reddeer.common.properties.RedDeerProperties;
import org.eclipse.reddeer.junit.internal.configuration.SuiteConfiguration;
import org.eclipse.reddeer.junit.requirement.configuration.RequirementConfigurationPool;
import org.eclipse.reddeer.junit.test.requirement.configuration.resources.JavaRequirement.CustomConfigJavaRequirementAAnnotation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.model.InitializationError;

public class ComplexConfigurationTest {

	private static final String JAVA_CONFIG_FILE = "resources" + File.separator + "java-config.json";
	
	private SuiteConfiguration config;
	
	@Before
	public void setup() {
		System.clearProperty(RedDeerProperties.CONFIG_FILE.getName());
		RequirementConfigurationPool.destroyPool();
	}
	
	@Test
	public void numberOfTestRunsNoConfig() throws InitializationError {
		config = new SuiteConfiguration(TestSuite.class);
		assertThat(config.getConfigurationSetsSuites().keySet().size(), is(1));
	}
	
	@Test
	public void numberOfTestRunsWithConfig() throws InitializationError {
		System.setProperty(RedDeerProperties.CONFIG_FILE.getName(), JAVA_CONFIG_FILE);
		config = new SuiteConfiguration(RequirementJavaClass.class);
		assertThat(config.getConfigurationSetsSuites().keySet().size(), is(3));
		System.clearProperty(RedDeerProperties.CONFIG_FILE.getName());
	}
	
	private static class TestSuite {}
	
	@CustomConfigJavaRequirementAAnnotation
	private static class RequirementJavaClass {}
}
