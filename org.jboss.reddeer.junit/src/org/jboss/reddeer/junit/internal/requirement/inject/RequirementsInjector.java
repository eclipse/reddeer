package org.jboss.reddeer.junit.internal.requirement.inject;

import java.lang.reflect.Field;
import java.util.Iterator;

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

	public void inject(Object testInstance, Requirements requirements) {
		for (Field field : testInstance.getClass().getDeclaredFields()) {
			if (field.isAnnotationPresent(InjectRequirement.class)) {
				Requirement<?> requirement = loadProperRequirement(field, requirements);
				if (requirement != null) {
					field.setAccessible(true);
					try {
						field.set(testInstance, requirement);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				} else {
					throw new RequirementInjectionException("Field type \"" + 
								field.getType() + "\" cannot be injected. " +
								"No corresponding requirement exists");
				}
			}
		}
	}
	
	private Requirement<?> loadProperRequirement(Field field, Requirements requirements) {
		Iterator<Requirement<?>> iter = requirements.iterator();
		while (iter.hasNext()) {
			Requirement<?> requirement = iter.next();
			if (field.getType().equals(requirement.getClass())) {
				return requirement;
			}
		}
		return null;
	}

}
