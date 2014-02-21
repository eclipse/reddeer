package org.jboss.reddeer.junit.internal.requirement.inject;

import java.lang.reflect.Field;

import org.jboss.reddeer.junit.internal.requirement.Requirements;
import org.jboss.reddeer.junit.requirement.Requirement;
import org.jboss.reddeer.junit.requirement.inject.InjectRequirement;
import org.jboss.reddeer.junit.requirement.inject.RequirementInjectionException;

/**
 * Finds requirements corresponding to all injection point of test class and injects them
 * 
 * @author jjankovi
 *
 */
public class RequirementsInjector {

	/**
	 * Injects requirements to the test instance.
	 * @param testInstance
	 * @param requirements
	 */
	public void inject(Object testInstance, Requirements requirements) {
		for (Field field : testInstance.getClass().getDeclaredFields()) {
			if (field.isAnnotationPresent(InjectRequirement.class)) {
				Requirement<?> requirement = loadProperRequirement(field, requirements);
				field.setAccessible(true);
				setField(field, testInstance, requirement);
			}
		}
	}
	
	private Requirement<?> loadProperRequirement(Field field, Requirements requirements) {
		for (Requirement<?> requirement : requirements) {
			if (field.getType().equals(requirement.getClass())) {
				return requirement;
			}
		}
		throw new RequirementInjectionException("Field type \"" + 
				field.getType() + "\" cannot be injected. " +
				"No corresponding requirement exists");
	}
	
	private void setField(Field field, Object testInstance, Requirement<?> requirement) {
		try {
			field.set(testInstance, requirement);
		} catch (IllegalArgumentException e) {
			throw new RequirementInjectionException("Cannot set field \"" + field +
					"\" due to illegal argument issue. " + e.getLocalizedMessage());
		} catch (IllegalAccessException e) {
			throw new RequirementInjectionException("Cannot set field \"" + field +
					"\" due to illegal access issue. " + e.getLocalizedMessage());
		}
	}

}
