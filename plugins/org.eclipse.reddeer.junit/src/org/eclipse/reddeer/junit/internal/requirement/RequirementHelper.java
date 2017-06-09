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
package org.eclipse.reddeer.junit.internal.requirement;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.reddeer.junit.annotation.AnnotationUtils;
import org.eclipse.reddeer.junit.requirement.ConfigurableRequirement;
import org.eclipse.reddeer.junit.requirement.Requirement;
import org.eclipse.reddeer.junit.requirement.RequirementException;
import org.eclipse.reddeer.junit.requirement.configuration.RequirementConfiguration;
import org.eclipse.reddeer.junit.requirement.configuration.RequirementConfigurationPool;
import org.hamcrest.Matcher;

/**
 * Class providing many useful methods for building and managing requirements
 * 
 * @author mlabuda@redhat.com
 *
 */
public class RequirementHelper {

	/**
	 * Crates a new requirement instance from a requirement class.
	 * 
	 * @param clazz
	 *            class of requirement to create its instance
	 * @return instance of requirement of specified type
	 */
	public static <T extends Object> T createInstance(Class<T> clazz) {
		try {
			return (T) clazz.newInstance();
		} catch (InstantiationException e) {
			throw new RequirementException("Error during instantiation of a requirement", e);
		} catch (IllegalAccessException e) {
			throw new RequirementException("Error during instantiation of a requirement", e);
		} catch (NullPointerException e) {
			throw new RequirementException("Error during instantiation of a null requirement object", e);
		}
	}
	
	/**
	 * Gets all requirements defined on a class and its parents up to Object
	 * class.
	 * 
	 * @param clazz
	 *            class to gets its requirements
	 * @return set of requirements defined for a class
	 */
	public static List<Requirement<?>> getRequirements(Class<?> clazz) {
		List<Requirement<?>> requirements = new ArrayList<>();
		for (Annotation annotation : AnnotationUtils.getRequirementAnnotations(clazz)) {
			Class<Requirement<Annotation>> requirementClass = AnnotationUtils
					.getEnclosingRequirementClass(annotation.annotationType());
			if (requirementClass != null) {
				Requirement<Annotation> requirement = RequirementHelper.createInstance(requirementClass);
				requirement.setDeclaration(annotation);
				requirements.add(requirement);
			}
		}
		return requirements;
	}
	
	/**
	 * Gets suitable configurations for a custom requirement matching specified
	 * matcher, if it is provided. Otherwise get all available configurations
	 * from configuration pool for a custom requirement. Custom requirement 
	 * should/could be without config (otherwise this method does not have 
	 * purpose).
	 * 
	 * @param requirement
	 *            requirement to get its available configurations
	 * @param matcher
	 *            matcher to match configurations
	 * @return if matcher provided, return matching configuration, if matcher is
	 *         null, return or suitable configuration for requirement
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<RequirementConfiguration> getRequirementConfigurations(ConfigurableRequirement requirement,
			Matcher matcher) {
		List<RequirementConfiguration> result = new ArrayList<>();
		List<org.eclipse.reddeer.junit.requirement.configuration.RequirementConfiguration> configurations = RequirementConfigurationPool
				.getInstance().getConfigurations(requirement.getConfigurationClass());
		for (RequirementConfiguration configuration : configurations) {
			if (matcher == null || (matcher != null && matcher.matches(configuration))) {
				result.add(configuration);
			}
		}
		return result;
	}
	
	/**
	 * Gets a list of custom requirements for a custom requirement (without
	 * configuration) matching configurations located in configuration pool.
	 * Configuration pool has to be filled in before calling this methods.
	 * 
	 * Purpose of this method is to be used to create list of custom
	 * requirements based on a scaffold of custom requirement.
	 * 
	 * @param customRequirement
	 *            requirement without configuration to get custom requirements
	 *            with matchin configurations
	 * @param configurationMatcher
	 *            configuration matcher or null, if there is no matcher to match
	 *            configuration
	 * @return list of custom requirements with matching configurations
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<ConfigurableRequirement> buildCustomRequirements(ConfigurableRequirement customRequirement,
			Matcher configurationMatcher) {

		List<ConfigurableRequirement> result = new ArrayList<>();
		List<org.eclipse.reddeer.junit.requirement.configuration.RequirementConfiguration> configurations = RequirementConfigurationPool
				.getInstance().getConfigurations(customRequirement.getConfigurationClass());
		Annotation declaration = customRequirement.getDeclaration();
		for (org.eclipse.reddeer.junit.requirement.configuration.RequirementConfiguration configuration : configurations) {
			if (configurationMatcher == null
					|| (configurationMatcher != null && configurationMatcher.matches(configuration))) {
				ConfigurableRequirement requirement = RequirementHelper.createInstance(customRequirement.getClass());
				requirement.setDeclaration(declaration);
				requirement.setConfiguration(configuration);
				result.add(requirement);
			}
		}
		return result;
	}
}
