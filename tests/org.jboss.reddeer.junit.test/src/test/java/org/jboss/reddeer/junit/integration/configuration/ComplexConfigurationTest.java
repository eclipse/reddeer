package org.jboss.reddeer.junit.integration.configuration;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import java.util.List;

import org.jboss.reddeer.junit.internal.configuration.RequirementsConfiguration;
import org.jboss.reddeer.junit.internal.configuration.SuiteConfiguration;
import org.jboss.reddeer.junit.internal.configuration.TestRunConfiguration;
import org.junit.Before;
import org.junit.Test;

public class ComplexConfigurationTest {

	@Before
	public void setup(){
		System.setProperty(SuiteConfiguration.PROPERTY_CONFIG_LOC, "src/test/resources/org/jboss/reddeer/junit/integration/configuration");
	}
	
	@Test
	public void numberOfTestRuns(){
		SuiteConfiguration suiteConfiguration = new SuiteConfiguration();
		List<TestRunConfiguration> testRunConfigutaions = suiteConfiguration.getTestRunConfigurations();
		
		assertThat(testRunConfigutaions.size(), is(2));
	}
	
	@Test
	public void testRunA(){
		SuiteConfiguration suiteConfiguration = new SuiteConfiguration();
		List<TestRunConfiguration> testRunConfigutaions = suiteConfiguration.getTestRunConfigurations();
		RequirementsConfiguration requirementsConfiguration = getTestRunById(testRunConfigutaions, "requirementsA.xml").getRequirementConfiguration();
		
		RequirementA requirementA = new RequirementA();
		RequirementB requirementB = new RequirementB();
		JavaRequirement javaRequirement = new JavaRequirement();
		
		requirementsConfiguration.configure(requirementA);
		requirementsConfiguration.configure(requirementB);
		requirementsConfiguration.configure(javaRequirement);
		
		assertThat(requirementA.getA(), is("1"));
		
		assertThat(requirementB.getA(), is("3"));
		assertThat(requirementB.getB(), is("2"));

		assertThat(javaRequirement.getConfig().getRuntime(), is("/home/java"));
		assertThat(javaRequirement.getConfig().getVersion(), is("1.6"));
	}
	
	@Test
	public void testRunb(){
		SuiteConfiguration suiteConfiguration = new SuiteConfiguration();
		List<TestRunConfiguration> testRunConfigutaions = suiteConfiguration.getTestRunConfigurations();
		RequirementsConfiguration requirementsConfiguration = getTestRunById(testRunConfigutaions, "requirementsB.xml").getRequirementConfiguration();
		
		RequirementA requirementA = new RequirementA();
		RequirementB requirementB = new RequirementB();
		JavaRequirement javaRequirement = new JavaRequirement();
		
		requirementsConfiguration.configure(requirementA);
		requirementsConfiguration.configure(requirementB);
		requirementsConfiguration.configure(javaRequirement);
		
		assertThat(requirementA.getA(), is("10"));
		
		assertThat(requirementB.getA(), is("30"));
		assertThat(requirementB.getB(), is("20"));

		assertThat(javaRequirement.getConfig().getRuntime(), is("/java"));
		assertThat(javaRequirement.getConfig().getVersion(), nullValue());
	}
	
	private TestRunConfiguration getTestRunById(List<TestRunConfiguration> testRunConfigutaions, String id) {
		for (TestRunConfiguration config : testRunConfigutaions){
			if (id.equals(config.getId())){
				return config;
			}
		}
		throw new AssertionError("The required test run config was not present: " + id);
	}
}
