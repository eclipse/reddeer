package org.jboss.reddeer.junit.internal.configuration.configurator;

import org.jboss.reddeer.junit.requirement.Requirement;

/**
 * Provides no action upon requirement configuration. Used for requirements that require no configuration.  
 * 
 * @author Lucia Jelinkova
 *
 */
public class NullConfigurator implements RequirementConfigurator {

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.junit.internal.configuration.configurator.RequirementConfigurator#configure(org.jboss.reddeer.junit.requirement.Requirement)
	 */
	@Override
	public void configure(Requirement<?> requirement) {
		// do nothing, no configuration required
	}
}
