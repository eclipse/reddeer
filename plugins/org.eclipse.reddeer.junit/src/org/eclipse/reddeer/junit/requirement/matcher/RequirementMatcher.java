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
package org.eclipse.reddeer.junit.requirement.matcher;

import org.eclipse.reddeer.junit.requirement.configuration.RequirementConfiguration;
import org.hamcrest.BaseMatcher;

/**
 * General Requirement Attribute matcher. Useful for matching requirement's 
 * attribute to a matching value.
 * 
 * @author mlabuda@redhat.com
 *
 */
public abstract class RequirementMatcher extends BaseMatcher<String> {

	private String attributeName;
	private String matchingValue;
	private Class<? extends RequirementConfiguration> configurationClass;

	/**
	 * Constructs a new requirement attribute matcher.
	 * 
	 * @param attribute requirement attribute name
	 * @param matchingValue matched value
	 */
	public RequirementMatcher(Class<? extends RequirementConfiguration> clazz, String attribute, String matchingValue) {
		this.attributeName = attribute;
		this.matchingValue = matchingValue;
		configurationClass = clazz;
	}

	/**
	 * Gets configuration class which should be matched with this matcher.
	 * @return configuration class to match
	 */
	public Class<? extends RequirementConfiguration> getConfigurationClass() {
		return configurationClass;
	}

	/**
	 * Sets configuration class for matching.
	 * @param configurationClass configuration class to match
	 */
	public void setConfigurationClass(Class<? extends RequirementConfiguration> configurationClass) {
		this.configurationClass = configurationClass;
	}
	
	/**
	 * Gets matched attribute name.
	 * 
	 * @return attribute name
	 */
	public String getAttributeName() {
		return attributeName;
	}

	/**
	 * Sets matched attribute name.
	 * 
	 * @param attribute
	 */
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	/**
	 * Gets matching value for attribute.
	 * 
	 * @return matching value 
	 */
	public String getMatchingValue() {
		return matchingValue;
	}

	/**
	 * Sets matching value for attribute.
	 * 
	 * @param matchingValue matching value
	 */
	public void setMatchingValue(String matchingValue) {
		this.matchingValue = matchingValue;
	}	
}
