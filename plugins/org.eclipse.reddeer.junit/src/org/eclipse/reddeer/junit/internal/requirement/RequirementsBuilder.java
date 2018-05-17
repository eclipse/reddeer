/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.junit.internal.requirement;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.junit.internal.configuration.RequirementConfigurationSet;
import org.eclipse.reddeer.junit.requirement.ConfigurableRequirement;
import org.eclipse.reddeer.junit.requirement.Requirement;
import org.eclipse.reddeer.junit.requirement.configuration.MissingRequirementConfiguration;
import org.eclipse.reddeer.junit.requirement.configuration.RequirementConfiguration;

/**
 * Builds and configures a set of requirements for the class and aggregates them
 * into a {@link Requirements} object.
 * 
 * @author mlabuda@redhat.com
 *
 */
public class RequirementsBuilder {

	private static final Logger log = Logger.getLogger(RequirementsBuilder.class);

	/**
	 * Builds requirements for a specified configuration set and a class.
	 * 
	 * @param configurationSet
	 *            configuration set to build requirements for a class
	 * @param clazz
	 *            class to build its requirements
	 * @return requirements set for a specified class, if configuration set
	 *         contains only MissingRequirementConfiguration, then empty
	 *         requirements is created
	 */
	@SuppressWarnings("unchecked")
	public Requirements build(RequirementConfigurationSet configurationSet, Class<?> clazz) {
		if (configurationSet == null || clazz == null) {
			throw new IllegalArgumentException("Configuration set nor clazz cannot be null.");
		}
		List<Requirement<?>> requirements = new ArrayList<>();
		if (configurationSet.getConfigurationSet().contains(new MissingRequirementConfiguration())) {
			// Do nothing
		} else {
			List<Requirement<?>> rawRequirements = RequirementHelper.getRequirements(clazz);
			for (Requirement<?> requirement : rawRequirements) {
				if (ConfigurableRequirement.class.isAssignableFrom(requirement.getClass())) {
					@SuppressWarnings("rawtypes")
					ConfigurableRequirement customRequirement = (ConfigurableRequirement) requirement;
					for (RequirementConfiguration configuration: configurationSet.getConfigurationSet()) {
						if (customRequirement.getConfigurationClass().isAssignableFrom(configuration.getClass())) {
							customRequirement.setConfiguration(configuration);
							break;
						}
					}
				}
				requirements.add(requirement);
			}
		}
		return new Requirements(requirements, clazz, configurationSet.getId());
	}
}
