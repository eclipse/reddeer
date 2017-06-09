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
import org.hamcrest.Description;

/**
 * Requirement regex matcher matches attributes of a requirement configuration
 * to a specific value
 * 
 * @author mlabuda@redhat.com
 *
 */
public class RequirementRegexMatcher extends RequirementMatcher {

	public RequirementRegexMatcher(Class<? extends RequirementConfiguration> clazz, String attribute, String matchingValue) {
		super(clazz, attribute, matchingValue);
	}

	/**
	 * Matches a requirement configuration whether it matches this requirement 
	 * attribute regex matcher or not. If Object passed to the method is not 
	 * instance of RequirementConfiguration, it returns false. Otherwise it 
	 * checks match on an attribute of the configuration.
	 */
	@Override
	public boolean matches(Object item) {
		if (getConfigurationClass().isAssignableFrom(item.getClass())) {
			RequirementConfiguration config = (RequirementConfiguration) item;
			return config.getNestedAttribute(getAttributeName()).matches(getMatchingValue());
		}
		return false;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(" matches requirement's attribute to regex.");
	}
}
