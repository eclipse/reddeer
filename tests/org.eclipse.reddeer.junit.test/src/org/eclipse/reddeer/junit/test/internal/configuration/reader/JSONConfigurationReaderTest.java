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
package org.eclipse.reddeer.junit.test.internal.configuration.reader;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.eclipse.reddeer.junit.configuration.RedDeerConfigurationException;
import org.eclipse.reddeer.junit.internal.configuration.reader.JSONConfigurationReader;
import org.eclipse.reddeer.junit.requirement.configuration.RequirementConfiguration;
import org.junit.Before;
import org.junit.Test;

public class JSONConfigurationReaderTest {

	private static final String RESOURCES_DIR = "." + File.separator + "resources" + File.separator;
	public static final String CONFIG_FILE = RESOURCES_DIR + "SimpleAndComplexTestConfigurations.json";
	public static final String NONEXISTING_FILE = RESOURCES_DIR + "nada.json";
	public static final String INVALID_FILE = RESOURCES_DIR + "InvalidTestConfiguration.json";
	
	private JSONConfigurationReader reader;
	
	@Before
	public void setup() {
		reader = new JSONConfigurationReader();
	}
	
	@Test(expected=RedDeerConfigurationException.class)
	public void testLoadConfigsFromNonexistingFile() {
		reader.loadConfigurations(new File(NONEXISTING_FILE));
	}

	@Test(expected=RedDeerConfigurationException.class)
	public void testLoadConfigsFromInvalidJSONFile() {
		reader.loadConfigurations(new File(INVALID_FILE));
	}
	
	@Test
	public void testLoadConfigFromFile() {
		List<RequirementConfiguration> configs = reader.loadConfigurations(new File(CONFIG_FILE));
		assertTrue("There should be 8 configs in configuration file", configs.size() == 8);
	}
}
