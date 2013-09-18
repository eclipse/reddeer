package org.jboss.reddeer.junit.internal.configuration;

/**
 * Represents configuration for one test run.
 * 
 * @author rhopp
 *
 */

public interface TestRunConfiguration {
	
	public String getId();
	
	public RequirementsConfiguration getRequirementConfiguration();

}
