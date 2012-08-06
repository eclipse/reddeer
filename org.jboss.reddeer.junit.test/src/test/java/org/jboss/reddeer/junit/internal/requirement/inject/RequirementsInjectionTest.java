package org.jboss.reddeer.junit.internal.requirement.inject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import org.jboss.reddeer.junit.internal.configuration.RequirementsConfiguration;
import org.jboss.reddeer.junit.internal.configuration.SuiteConfiguration;
import org.jboss.reddeer.junit.internal.configuration.TestRunConfiguration;
import org.jboss.reddeer.junit.internal.requirement.Requirements;
import org.jboss.reddeer.junit.requirement.Requirement;
import org.jboss.reddeer.junit.requirement.inject.RequirementInjectionException;
import org.junit.BeforeClass;
import org.junit.Test;

public class RequirementsInjectionTest {
	
	private RequirementsInjector requirementsInjection = new RequirementsInjector();
	
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
		NoRequirementTestMock testInstance = new NoRequirementTestMock();
		
		assertThat(numberOfAnnotatatedFieldsInClass(testInstance), is(0));
		
		Object param1 = getValueOfField("param1", testInstance);
		assertThat((String)param1, is("0"));
		
		requirementsInjection.inject(testInstance, requirements);
		
		param1 = getValueOfField("param1", testInstance);
		assertThat((String)param1, is("0"));
	}
	
	@Test
	public void testPropertyRequirementInjection() {
		PropertyRequirementTestMock testInstance = new PropertyRequirementTestMock();
		
		assertThat(numberOfAnnotatatedFieldsInClass(testInstance), is(1));
		
		requirementsInjection.inject(testInstance, requirements);
		
		Object param1 = getValueOfField("requirementA", testInstance);
		assertThat(((RequirementA)param1).getA(), is("1"));
	}
	
	@Test
	public void testMultiplePropertyRequirementInjection() {
		MultiplePropertyRequirementTestMock testInstance = new MultiplePropertyRequirementTestMock();
		
		assertThat(numberOfAnnotatatedFieldsInClass(testInstance), is(2));
		
		requirementsInjection.inject(testInstance, requirements);
		
		Object param1 = getValueOfField("requirementA1", testInstance);
		assertThat(((RequirementA)param1).getA(), is("1"));
		
		param1 = getValueOfField("requirementA2", testInstance);
		assertThat(((RequirementA)param1).getA(), is("1"));
	}
	
	@Test(expected=org.jboss.reddeer.junit.requirement.inject.RequirementInjectionException.class)
	public void testNonConfiguredRequirementInjection() {
		NoRequirementConfigTestMock testInstance = new NoRequirementConfigTestMock();
		
		assertThat(numberOfAnnotatatedFieldsInClass(testInstance), is(1));
		
		requirementsInjection.inject(testInstance, requirements);
	}
	
	@Test(expected=org.jboss.reddeer.junit.requirement.inject.RequirementInjectionException.class)
	public void testNoRequirementFieldInjection() {
		NonExistedRequirementTestMock testInstance = new NonExistedRequirementTestMock();
		
		assertThat(numberOfAnnotatatedFieldsInClass(testInstance), is(1));
		
		requirementsInjection.inject(testInstance, requirements);
	}
	
	private static TestRunConfiguration getTestRunById(List<TestRunConfiguration> testRunConfigutaions, String id) {
		for (TestRunConfiguration config : testRunConfigutaions){
			if (id.equals(config.getId())){
				return config;
			}
		}
		throw new AssertionError("The required test run config was not present: " + id);
	}
	
	private int numberOfAnnotatatedFieldsInClass(Object testInstance) {
		Field[] fields = testInstance.getClass().getDeclaredFields();
		int counter = 0;
		for (Field field : fields) {
			counter += field.getAnnotations().length > 0?1:0;
		}
		return counter;
	}
	
	private Object getValueOfField(String fieldName, Object testInstance) {
		try {
			Field param1 = testInstance.getClass().getDeclaredField(fieldName); 
			param1.setAccessible(true);
			return param1.get(testInstance);
		} catch (Exception e) {
			throw new RequirementInjectionException("Cannot get value of field \"" + fieldName + 
					"\". " + e.getLocalizedMessage());
		}
	}

	private List<Requirement<?>> asList(Requirement<?>... requirements) {
		return Arrays.asList(requirements);
	}
	
}
