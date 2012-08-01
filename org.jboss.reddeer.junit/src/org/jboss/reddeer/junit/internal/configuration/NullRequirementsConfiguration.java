package org.jboss.reddeer.junit.internal.configuration;

import org.jboss.reddeer.junit.configuration.RedDeerConfigurationException;
import org.jboss.reddeer.junit.internal.configuration.configurator.NullConfigurator;
import org.jboss.reddeer.junit.requirement.CustomConfiguration;
import org.jboss.reddeer.junit.requirement.PropertyConfiguration;
import org.jboss.reddeer.junit.requirement.Requirement;

/**
 * Configures requirements when no configuration file was specified.
 * 
 * @author rhopp
 * @see RequirementsConfiguration
 */

public class NullRequirementsConfiguration implements
		RequirementsConfiguration {
	
	private NullConfigurator voidConfigurator;

	public NullRequirementsConfiguration() {
		super();
		this.voidConfigurator = new NullConfigurator();
	}
	
	@Override
	public void configure(Requirement<?> requirement) {
		if (requirement instanceof PropertyConfiguration || requirement instanceof CustomConfiguration){
			throw new RedDeerConfigurationException("Class "+requirement.getClass().toString()+" requires configuration file, but no configuration file was specified");
		}
		voidConfigurator.configure(requirement);
	}
}
