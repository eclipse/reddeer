package org.jboss.reddeer.junit.internal.configuration;

/**
 * Represents configuration for one test run.
 * 
 * @author rhopp
 *
 */

public interface TestRunConfiguration {
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId();
	
	/**
	 * Gets the requirement configuration.
	 *
	 * @return the requirement configuration
	 */
	public RequirementsConfiguration getRequirementConfiguration();

}
