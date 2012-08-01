package org.jboss.reddeer.junit.internal.configuration;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;

import org.jboss.reddeer.junit.configuration.RedDeerConfigurationException;
import org.jboss.reddeer.junit.requirement.CustomConfiguration;
import org.jboss.reddeer.junit.requirement.PropertyConfiguration;
import org.jboss.reddeer.junit.requirement.Requirement;
import org.junit.Test;


public class NullRequirementsConfigurationTest{
	
	private NullRequirementsConfiguration config = new NullRequirementsConfiguration();

	//TODO create tests - rhopp
	
	@Test(expected=RedDeerConfigurationException.class)
	public void propertyBased(){
		Requirement<?> requirement = mock(Requirement.class, withSettings().extraInterfaces(PropertyConfiguration.class));
		config.configure(requirement);
	}
	
	@Test(expected=RedDeerConfigurationException.class)
	public void custom(){
		Requirement<?> requirement = mock(Requirement.class, withSettings().extraInterfaces(CustomConfiguration.class));
		config.configure(requirement);
	}
	
	@Test
	public void void_caching(){
		Requirement<?> requirement = mock(Requirement.class);
		config.configure(requirement);
	}

}
