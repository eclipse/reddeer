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
package org.eclipse.reddeer.junit.internal.configuration;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.reddeer.junit.requirement.configuration.RequirementConfiguration;

/**
 * Wrapper for set of requirement configurations for custom requirements.
 * 
 * @author mlabuda@redhat.com
 *
 */
public class RequirementConfigurationSet {
	
	public static final String EMPTY_SET_ID = "no-configuration";
	
	private List<RequirementConfiguration> list;

	public RequirementConfigurationSet() {
		list = new ArrayList<>();
	}

	public RequirementConfigurationSet(List<RequirementConfiguration> list) {	
		this.list = list;
	}
	
	/**
	 * Sets requirements configurations.
	 * 
	 * @param list list of requirements configurations
	 */
	public void setConfigurations(List<RequirementConfiguration> list) {
		this.list = list;
	}
	
	/**
	 * Adds a requirement configuration to requirement configuration set.
	 * 
	 * @param configuration
	 *            requirement configuration to add to this set
	 */
	public void addConfiguration(RequirementConfiguration configuration) {
		list.add(configuration);
	}

	/**
	 * Gets a list of requirement configurations encapsulated in this
	 * requirement configuration set.
	 * 
	 * @return
	 */
	public List<RequirementConfiguration> getConfigurationSet() {
		return list;
	}

	/**
	 * Gets ID of configuration set. It consist of ID of all configurations
	 * wrapped in this requirement configuration set.
	 * @return
	 */
	public String getId() {
		StringBuilder sb = new StringBuilder();
		for (RequirementConfiguration configuration: list) {
			sb.append(configuration.getId());
		}
		if (sb.length() > 0) {
			return sb.toString();
		} else {
			return EMPTY_SET_ID;
		}
		
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (o instanceof RequirementConfigurationSet) {
			RequirementConfigurationSet configSet = (RequirementConfigurationSet) o;
			if (configSet.getConfigurationSet().size() != list.size()) {
				return false;
			}
			for (RequirementConfiguration configuration: configSet.getConfigurationSet()) {
				if (!list.contains(configuration)) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}
}
