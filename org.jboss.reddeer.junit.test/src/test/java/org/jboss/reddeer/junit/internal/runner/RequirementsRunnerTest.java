package org.jboss.reddeer.junit.internal.runner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Arrays;
import java.util.List;

import org.jboss.reddeer.junit.internal.configuration.RequirementsConfiguration;
import org.jboss.reddeer.junit.internal.configuration.SuiteConfiguration;
import org.jboss.reddeer.junit.internal.configuration.TestRunConfiguration;
import org.jboss.reddeer.junit.internal.requirement.Requirements;
import org.jboss.reddeer.junit.internal.requirement.inject.MultiplePropertyRequirementTestMock;
import org.jboss.reddeer.junit.internal.requirement.inject.NoRequirementConfigTestMock;
import org.jboss.reddeer.junit.internal.requirement.inject.NoRequirementTestMock;
import org.jboss.reddeer.junit.internal.requirement.inject.NonExistedRequirementTestMock;
import org.jboss.reddeer.junit.internal.requirement.inject.PropertyRequirementTestMock;
import org.jboss.reddeer.junit.internal.requirement.inject.RequirementA;
import org.jboss.reddeer.junit.requirement.Requirement;
import org.jboss.reddeer.junit.requirement.inject.RequirementInjectionException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runners.model.InitializationError;

public class RequirementsRunnerTest {

	private RequirementsRunner requirementsRunner;
	
	private static final String TEST_CONFIG_LOC = "src/test/resources/org/jboss/" +
			"reddeer/junit/internal/requirement/inject";
	
	private static final RequirementA requirement1 = new RequirementA(); 
	
	private Requirements requirements = new Requirements(asList(requirement1));
	
	@BeforeClass
	public static void initConfig() {
		System.setProperty(SuiteConfiguration.PROPERTY_CONFIG_LOC, TEST_CONFIG_LOC);
		SuiteConfiguration suiteConfiguration = new SuiteConfiguration();
		List<TestRunConfiguration> testRunConfigurations = suiteConfiguration.getTestRunConfigurations();
		RequirementsConfiguration requirementsConfiguration = 
				getTestRunById(testRunConfigurations, "requirementsA.xml").getRequirementConfiguration();
		requirementsConfiguration.configure(requirement1);
	}
	
	@Test
	public void testNonAnnotatedFields() {
		
		requirementsRunner = initRunner(NoRequirementTestMock.class, requirements);
		NoRequirementTestMock testInstance = 
				(NoRequirementTestMock) createTestInstance(requirementsRunner);
		assertThat(testInstance.getParam1(), is("0"));
		
	}
	
	@Test
	public void testPropertyRequirementInjection() {
		
		requirementsRunner = initRunner(PropertyRequirementTestMock.class, requirements);
		PropertyRequirementTestMock testInstance = 
				(PropertyRequirementTestMock) createTestInstance(requirementsRunner);
		assertThat(testInstance.getRequirementA().getA(), is("1"));
	}
	
	@Test
	public void testMultiplePropertyRequirementInjection() {
		
		requirementsRunner = initRunner(MultiplePropertyRequirementTestMock.class, requirements);
		MultiplePropertyRequirementTestMock testInstance = 
				(MultiplePropertyRequirementTestMock) createTestInstance(requirementsRunner);
		assertThat(testInstance.getRequirementA1().getA(), is("1"));
		assertThat(testInstance.getRequirementA2().getA(), is("1"));
		
	}
	
	@Test(expected=org.jboss.reddeer.junit.requirement.inject.RequirementInjectionException.class)
	public void testNonConfiguredRequirementInjection() {
		
		requirementsRunner = initRunner(NoRequirementConfigTestMock.class, requirements);
		createTestInstance(requirementsRunner);
		
	}
	
	@Test(expected=org.jboss.reddeer.junit.requirement.inject.RequirementInjectionException.class)
	public void testNoRequirementFieldInjection() {
		
		requirementsRunner = initRunner(NonExistedRequirementTestMock.class, requirements);
		createTestInstance(requirementsRunner);
		
	}
	
	private static TestRunConfiguration getTestRunById(List<TestRunConfiguration> testRunConfigutaions, String id) {
		for (TestRunConfiguration config : testRunConfigutaions){
			if (id.equals(config.getId())){
				return config;
			}
		}
		throw new AssertionError("The required test run config was not present: " + id);
	}
	
	private List<Requirement<?>> asList(Requirement<?>... requirements) {
		return Arrays.asList(requirements);
	}
	
	private RequirementsRunner initRunner(Class<?> clazz, Requirements requirements) {
		try {
			requirementsRunner = new RequirementsRunner(clazz, requirements);
			return requirementsRunner;
		} catch (InitializationError e) {
			throw new RequirementInjectionException("Cannot create runner for class \"" + clazz + 
					"\". " + e.getLocalizedMessage());
		}
	}
	
	private Object createTestInstance(RequirementsRunner requirementsRunner) {
		try {
			return requirementsRunner.createTest();
		} catch (Exception e) {
			throw new RequirementInjectionException("Cannot create test instance for class \"" + 
					requirementsRunner.getTestClass().getClass() + 
					"\". " + e.getLocalizedMessage());
		}
	}
	
}
