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
package org.eclipse.reddeer.junit.internal.configuration;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.reddeer.junit.requirement.configuration.RequirementConfiguration;

/**
 * Wrapper for set of requirement configurations for custom requirements.
 * 
 * @author mlabuda@redhat.com
 *
 */
public class RequirementConfigurationSet {
	
	public static final String EMPTY_SET_ID = "no-configuration";
	
	private Set<RequirementConfiguration> set;

	public RequirementConfigurationSet() {
		set = new HashSet<>();
	}

	public RequirementConfigurationSet(Set<RequirementConfiguration> set) {	
		this.set = set;
	}
	
	/**
	 * Sets requirements configurations.
	 * 
	 * @param list list of requirements configurations
	 */
	public void setConfigurations(Set<RequirementConfiguration> set) {
		this.set = set;
	}
	
	/**
	 * Adds a requirement configuration to requirement configuration set.
	 * 
	 * @param configuration
	 *            requirement configuration to add to this set
	 */
	public void addConfiguration(RequirementConfiguration configuration) {
		set.add(configuration);
	}

	/**
	 * Gets a list of requirement configurations encapsulated in this
	 * requirement configuration set.
	 * 
	 * @return
	 */
	public Set<RequirementConfiguration> getConfigurationSet() {
		return set;
	}

	/**
	 * Gets ID of configuration set. It consist of ID of all configurations
	 * wrapped in this requirement configuration set.
	 * @return
	 */
	public String getId() {
		StringBuilder sb = new StringBuilder();
		Iterator<RequirementConfiguration> iterator = set.iterator();
		while (iterator.hasNext()) {
			RequirementConfiguration configuration = iterator.next();
			sb.append(configuration.getId());
			if (iterator.hasNext()) {
				sb.append("_");
			}
		}
		if (sb.length() > 0) {
			return sb.toString();
		} else {
			return EMPTY_SET_ID;
		}
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((set == null) ? 0 : set.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} if (obj == null) {
			return false;
		} if (getClass() != obj.getClass()) {
			return false;
		}
		RequirementConfigurationSet other = (RequirementConfigurationSet) obj;
		if (set == null) {
			if (other.set != null) {
				return false;
			}
		} else if (!set.equals(other.set)) {
			return false;
		}
		return true;
	}
}
