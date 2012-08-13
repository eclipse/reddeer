package org.jboss.reddeer.junit.internal.requirement;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jboss.reddeer.junit.internal.annotation.AnnotationsFinder;
import org.jboss.reddeer.junit.internal.configuration.RequirementsConfiguration;
import org.jboss.reddeer.junit.requirement.Requirement;
import org.jboss.reddeer.junit.requirement.RequirementException;

/**
 * Builds and configures a set of requirements for the class and aggregates them into a {@link Requirements} object. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class RequirementsBuilder {
	
	private static final Logger log = Logger.getLogger(RequirementsBuilder.class);
	
	private AnnotationsFinder finder = new AnnotationsFinder(new RequirementAnnotationMatcher());
	
	public Requirements build(Class<?> clazz, RequirementsConfiguration config){
		checkArguments(clazz, config);
		List<Requirement<?>> requirements = new ArrayList<Requirement<?>>();
		
		log.info("Creating requirements for test " + clazz);
		for (Annotation annotation : finder.find(clazz)){
			requirements.add(build(annotation, config));
		}
		
		return new Requirements(requirements);
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
	
	protected void setFinder(AnnotationsFinder finder) {
		this.finder = finder;
	}
}
