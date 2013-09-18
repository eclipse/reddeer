package org.jboss.reddeer.junit.internal.configuration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertSame;

import org.junit.Test;

public class NullTestRunConfigurationTest {

	
	@Test
	public void getId(){
		String id = new NullTestRunConfiguration().getId();
		
		assertThat(id, is("default"));
	}
	
	@Test
	public void getRequirementConfiguration_caching(){
		TestRunConfiguration config = new NullTestRunConfiguration();
		
		RequirementsConfiguration configuration1 = config.getRequirementConfiguration();
		RequirementsConfiguration configuration2 = config.getRequirementConfiguration();
		
		assertSame(configuration1, configuration2);
		assertThat(configuration1, instanceOf(NullRequirementsConfiguration.class));
	}
	
}
