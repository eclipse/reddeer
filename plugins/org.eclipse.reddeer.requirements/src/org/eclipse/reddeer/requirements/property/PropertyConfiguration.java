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
package org.eclipse.reddeer.requirements.property;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.reddeer.junit.requirement.configuration.RequirementConfiguration;

/**
 * Property configuration is configuration used for key-value pairs.
 * Property configuration does not support deep clone yet.
 * 
 * @author mlabuda@redhat.com
 *
 */
public class PropertyConfiguration implements RequirementConfiguration {

	private Map<String, String> properties;
	
	public PropertyConfiguration() {
		properties = new HashMap<>();
	}
	
	/**
	 * Gets all properties.
	 * 
	 * @return map of key-value pairs
	 */
	public Map<String, String> getProperties() {
		return properties;
	}

	/**
	 * Sets properties.
	 * @param properties properties to set
	 */
	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

	/**
	 * Add a property to property configuration
	 * 
	 * @param key property name
	 * @param value property value
	 */
	public void addProperty(String key, String value) {
		properties.put(key, value);
	}
	
	/**
	 * Gets a specific property value based on a key/property name.
	 * @param propertyName key to get value
	 * @return value of property
	 */
	public String getPropertyValue(String propertyName) {
		return properties.get(propertyName);
	}
	
	@Override
	public String getId() {
		return "property-config";
	}
}
