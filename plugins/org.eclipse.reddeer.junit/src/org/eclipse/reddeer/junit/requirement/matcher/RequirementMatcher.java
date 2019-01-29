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
package org.eclipse.reddeer.junit.requirement.matcher;

import java.lang.annotation.Annotation;

import org.eclipse.reddeer.junit.requirement.configuration.RequirementConfiguration;
import org.eclipse.reddeer.junit.util.ReflectionUtil;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsEqual;

/**
 * General Requirement Attribute matcher. 
 * 
 * @author mlabuda@redhat.com
 *
 */
public class RequirementMatcher extends TypeSafeMatcher<RequirementConfiguration> {
	
	private Matcher<?> attributeValueMatcher;
	private String attributeName;
	private Class<? extends Annotation> configurationClass;

	/**
	 * Constructs a new requirement matcher.
	 * 
	 * @param clazz requirement class
	 * @param matcher matching requirement
	 */
	public RequirementMatcher(Class<? extends Annotation> clazz, String attributeName, Matcher<?> attributeValue) {
		this.attributeValueMatcher = attributeValue;
		this.attributeName = attributeName;
		configurationClass = clazz;
	}
	
	public RequirementMatcher(Class<? extends Annotation> clazz, String attributeName, String attributeValue) {
		this(clazz, attributeName, new IsEqual<String>(attributeValue));
	}

	/**
	 * Gets configuration class which should be matched with this matcher.
	 * @return configuration class to match
	 */
	public Class<? extends Annotation> getConfigurationClass() {
		return configurationClass;
	}
	
	/**
	 * Returns attribute attributeName, this method is required to be public to be used in
	 * req. matcher.
	 * @return attributeName string
	 */
	public String getAttributeName() {
		return this.attributeName;
	}

	/**
	 * Sets configuration class for matching.
	 * @param configurationClass configuration class to match
	 */
	public void setConfigurationClass(Class<? extends Annotation> configurationClass) {
		this.configurationClass = configurationClass;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("matches requirement attribute '"+attributeName+"' against matcher "+attributeValueMatcher);
		
	}

	@Override
	protected boolean matchesSafely(RequirementConfiguration item) {
		Object value = ReflectionUtil.getValue(item, attributeName);
		return attributeValueMatcher.matches(value);
	}	
}
