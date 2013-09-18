package org.jboss.reddeer.junit.internal.configuration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;

import java.io.File;

import org.junit.Test;

public class TestRunConfigurationImplTest {

	@Test
	public void getId(){
		File file = new File("aaa/bbb.ccc");
		
		String id = new TestRunConfigurationImpl(file).getId();
		
		assertThat(id, is("bbb.ccc"));
	}
	
	@Test
	public void getRequirementConfiguration_caching(){
		File file = mock(File.class);
		TestRunConfiguration config = new TestRunConfigurationImpl(file);
		
		RequirementsConfiguration configuration1 = config.getRequirementConfiguration();
		RequirementsConfiguration configuration2 = config.getRequirementConfiguration();
		
		assertSame(configuration1, configuration2);
		assertThat(configuration1, instanceOf(RequirementsConfigurationImpl.class));
	}
	
}
