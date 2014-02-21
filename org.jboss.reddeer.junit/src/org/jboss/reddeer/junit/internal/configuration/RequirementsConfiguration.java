package org.jboss.reddeer.junit.internal.configuration;

import org.jboss.reddeer.junit.requirement.Requirement;

/**
 * Interface for configuring requirements.
 * 
 * @author rhopp
 *
 */
public interface RequirementsConfiguration {

	
	public void configure(Requirement<?> requirement);
	
}
