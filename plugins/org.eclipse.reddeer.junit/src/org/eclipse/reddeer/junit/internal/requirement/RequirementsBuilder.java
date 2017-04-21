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

import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.junit.internal.annotation.AnnotationsFinder;
import org.eclipse.reddeer.junit.internal.configuration.RequirementsConfiguration;
import org.eclipse.reddeer.junit.requirement.Requirement;
import org.eclipse.reddeer.junit.requirement.RequirementException;

/**
 * Builds and configures a set of requirements for the class and aggregates them into a {@link Requirements} object. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class RequirementsBuilder {
	
	private static final Logger log = Logger.getLogger(RequirementsBuilder.class);
	
	private AnnotationsFinder finder = new AnnotationsFinder(new RequirementAnnotationMatcher());
	
	/**
	 * Builds the.
	 *
	 * @param clazz the clazz
	 * @param config the config
	 * @param configID the config id
	 * @return the requirements
	 */
	public Requirements build(Class<?> clazz, RequirementsConfiguration config, String configID){
		checkArguments(clazz, config);
		List<Requirement<?>> requirements = new ArrayList<Requirement<?>>();
		
		log.info("Build method - Creating requirements for test " + clazz);
		for (Annotation annotation : this.finder.find(clazz)){
			log.info("### adding builded configuration for annotation " + annotation.toString());
			requirements.add(build(annotation, config));
		}
		
		return new Requirements(requirements, clazz, configID);
	}
	
	private Requirement<?> build(Annotation annotation, RequirementsConfiguration requirementConfig){
		Requirement<?> requirement = getRequirement(annotation);
		requirementConfig.configure(requirement);
		return requirement;
	}

	@SuppressWarnings("unchecked")
	private Requirement<?> getRequirement(Annotation annotation){
		Class<?> clazz = annotation.annotationType().getEnclosingClass();
		Requirement<Annotation> requirement = (Requirement<Annotation>) createInstance(clazz);
		requirement.setDeclaration(annotation);
		log.debug("Found requirement " + requirement.getClass() + " for annotation " + annotation.annotationType());
		return requirement;
	}
	
	private Requirement<?> createInstance(Class<?> clazz) {
		try {
			return (Requirement<?>) clazz.newInstance();
		} catch (InstantiationException e) {
			throw new RequirementException("Error during instantiation of a requirement", e);
		} catch (IllegalAccessException e) {
			throw new RequirementException("Error during instantiation of a requirement", e);
		}
	}
	
	private void checkArguments(Class<?> clazz, RequirementsConfiguration config) {
		if (clazz == null){
			throw new IllegalArgumentException("The class was null");
		}
		if (config == null){
			throw new IllegalArgumentException("The configuration was null");
		}
	}
	
	/**
	 * Sets the finder.
	 *
	 * @param finder the new finder
	 */
	public void setFinder(AnnotationsFinder finder) {
		this.finder = finder;
	}
}
