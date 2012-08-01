package org.jboss.reddeer.junit.internal.configuration;

/**
 * Represents Run configuration, when no config file is specified.
 * 
 * @author rhopp
 *
 */


public class NullTestRunConfiguration implements TestRunConfiguration {

	private RequirementsConfiguration requirementsConfiguration;
	
	@Override
	public String getId() {
		return "default";
	}

	@Override
	public RequirementsConfiguration getRequirementConfiguration() {
		if (requirementsConfiguration == null){
			requirementsConfiguration = new NullRequirementsConfiguration();
		}
		return requirementsConfiguration;
	}

}
