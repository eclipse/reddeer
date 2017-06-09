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
package org.eclipse.reddeer.junit.requirement.configuration;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.reddeer.common.properties.RedDeerProperties;
import org.eclipse.reddeer.junit.configuration.RedDeerConfigurationException;
import org.eclipse.reddeer.junit.internal.configuration.reader.JSONConfigurationReader;

/**
 * Requirements configuration pool consist of requirements configurations loaded
 * for a test run from a source.
 * 
 * @author mlabuda@redhat.com
 *
 */
public class RequirementConfigurationPool {

	private static RequirementConfigurationPool instance;

	private List<RequirementConfiguration> requirementsConfigurations;

	/**
	 * Creates a new requirement configuration pool and initialize it -
	 * populates with data from a source.
	 */
	private RequirementConfigurationPool() {
		requirementsConfigurations = new ArrayList<RequirementConfiguration>();
		initRequirementConfigurationPool();
	}

	/**
	 * Read a configuration file specified by rd config property and fill
	 * configurations to configuration pool.
	 */
	private void initRequirementConfigurationPool() {
		if (getConfigurationFile() != null) {
			JSONConfigurationReader reader = new JSONConfigurationReader();
			requirementsConfigurations.addAll(reader.loadConfigurations(getConfigurationFile()));
		}
	}

	/**
	 * Gets RedDeer configuration file or null if it does not exists.
	 * 
	 * @return RedDeer configuration file containing requirements
	 *         configurations.
	 */
	public static File getConfigurationFile() {
		String fileLocation = RedDeerProperties.CONFIG_FILE.getValue();

		if (fileLocation == null) {
			return null;
		}

		File configFile = new File(fileLocation);
		if (!configFile.exists()) {
			throw new RedDeerConfigurationException("The configuration location " + fileLocation + " does not exist");
		}

		if (configFile.isFile()) {
			return configFile;
		} else {
			throw new RedDeerConfigurationException(
					"The configuration location " + fileLocation + " must be a regular file");
		}
	}

	/**
	 * Gets singleton of RequirementConfigurationPool
	 * 
	 * @return singleton of requirement configuration pool
	 */
	public static RequirementConfigurationPool getInstance() {
		if (instance == null) {
			instance = new RequirementConfigurationPool();
		}
		return instance;
	}

	/**
	 * Destroy configuration pool. Whole pool is erased.
	 */
	public static void destroyPool() {
		if (instance != null) {
			instance = null;
		}
	}

	/**
	 * Sets set of configurations for configuration pool.
	 * 
	 * @param configurations
	 *            configurations to set
	 */
	public void setRequirementsConfigurations(List<RequirementConfiguration> configurations) {
		requirementsConfigurations = configurations;
	}

	/**
	 * Add requirements configurations
	 * 
	 * @param configurations
	 */
	public void addRequirementsConfigurations(List<RequirementConfiguration> configurations) {
		getRequirementsConfigurations().addAll(configurations);
	}

	/**
	 * Returns unmodifiable collection of requirements configurations.
	 * 
	 * @return
	 */
	public List<RequirementConfiguration> getRequirementsConfigurations() {
		return Collections.unmodifiableList(requirementsConfigurations);
	}

	/**
	 * Gets all available configurations for a specified configuration class.
	 * 
	 * @param clazz
	 *            class of a requirement configurations
	 * 
	 * @return list of available requirement configurations for a specified
	 *         class
	 */
	public List<RequirementConfiguration> getConfigurations(Class<? extends RequirementConfiguration> configClass) {

		List<RequirementConfiguration> suitableConfigs = new ArrayList<RequirementConfiguration>();
		for (RequirementConfiguration config : requirementsConfigurations) {
			if (configClass.isAssignableFrom(config.getClass())) {
				suitableConfigs.add(config);
			}
		}
		return deepCopyList(suitableConfigs);
	}

	/**
	 * Deep copy list of requirement configurations.
	 * 
	 * @param listToCopy
	 *            list of configuration to copy
	 * @return copied list of configurations. If a configuration cannot be deep
	 *         copied, than the original one is used.
	 */
	private List<RequirementConfiguration> deepCopyList(List<RequirementConfiguration> listToCopy) {
		List<RequirementConfiguration> result = new ArrayList<>();
		for (RequirementConfiguration configuraiton : listToCopy) {
			RequirementConfiguration copiedConfig = deepCopyConfiguration(configuraiton);
			if (copiedConfig == null) {
				result.add(configuraiton);
			} else {
				result.add(copiedConfig);
			}
		}
		return result;
	}

	/**
	 * Creates a deep copy of configuration, if it has a constructor taking an
	 * instance of the same class.
	 * 
	 * @param configuration
	 *            configuration to clone
	 * @return cloned configuration or null
	 */
	@SuppressWarnings("unchecked")
	private <T extends RequirementConfiguration> T deepCopyConfiguration(T configuration) {
		try {
			return (T) configuration.getClass().getConstructor(configuration.getClass()).newInstance(configuration);
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			return null;
		}
	}
}
