package org.jboss.reddeer.junit.internal.configuration;

import org.jboss.reddeer.junit.requirement.Requirement;

/**
 * Interface for configuring requirements.
 * 
 * @author rhopp
 *
 */

public interface RequirementsConfiguration {

	
	/**
	 * Configure.
	 *
	 * @param requirement the requirement
	 */
	public void configure(Requirement<?> requirement);
	
}
