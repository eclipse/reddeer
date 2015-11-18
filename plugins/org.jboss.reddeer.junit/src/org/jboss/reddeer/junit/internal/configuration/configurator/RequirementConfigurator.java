package org.jboss.reddeer.junit.internal.configuration.configurator;

import org.jboss.reddeer.junit.requirement.Requirement;

/**
 * Configures the instance of {@link Requirement}
 * 
 * @author Lucia Jelinkova
 *
 */
public interface RequirementConfigurator {

	/**
	 * Configure.
	 *
	 * @param requirement the requirement
	 */
	void configure(Requirement<?> requirement);
}
