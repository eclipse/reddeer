package org.jboss.reddeer.junit.test.internal.requirement.inject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Arrays;
import java.util.List;

import org.jboss.reddeer.junit.internal.requirement.Requirements;
import org.jboss.reddeer.junit.internal.requirement.inject.RequirementsInjector;
import org.jboss.reddeer.junit.requirement.Requirement;
import org.junit.Before;
import org.junit.Test;

public class RequirementsInjectorTest {
	
	private RequirementsInjector requirementsInjection = new RequirementsInjector();
	
	private RequirementA requirementA; 
	
	private Requirements requirements;
	
	@Before
	public void setup() {
		requirementA = new RequirementA();
		requirementA.setA("1");
		
		requirements = new Requirements(asList(requirementA), String.class, null); 
	}
	
	@Test
	public void testNonAnnotatedFields() {
		NoRequirementTestMock testInstance = new NoRequirementTestMock();
		
		requirementsInjection.inject(testInstance, requirements);
		
		assertThat(testInstance.getParam1(), is("0"));
	}
	
	@Test
	public void testPropertyRequirementInjection() {
		PropertyRequirementTestMock testInstance = new PropertyRequirementTestMock();
		
		requirementsInjection.inject(testInstance, requirements);
		
		assertThat(testInstance.getRequirementA().getA(), is("1"));
	}
	
	@Test
	public void testMultiplePropertyRequirementInjection() {
		MultiplePropertyRequirementTestMock testInstance = new MultiplePropertyRequirementTestMock();
		
		requirementsInjection.inject(testInstance, requirements);
		
		assertThat(testInstance.getRequirementA1().getA(), is("1"));
		
		assertThat(testInstance.getRequirementA2().getA(), is("1"));
	}
	
	@Test
	public void testClassHierarchyRequirementInjection() {
		ChildRequirementTestMock testInstance = new ChildRequirementTestMock();
		
		requirementsInjection.inject(testInstance, requirements);
		
		assertThat(testInstance.getRequirementA1().getA(), is("1"));
		
		assertThat(testInstance.getRequirementA2().getA(), is("1"));
	}
	
	@Test(expected=org.jboss.reddeer.junit.requirement.inject.RequirementInjectionException.class)
	public void testNonConfiguredRequirementInjection() {
		NoRequirementConfigTestMock testInstance = new NoRequirementConfigTestMock();
		
		requirementsInjection.inject(testInstance, requirements);
	}
	
	@Test(expected=org.jboss.reddeer.junit.requirement.inject.RequirementInjectionException.class)
	public void testNoRequirementFieldInjection() {
		NonExistedRequirementTestMock testInstance = new NonExistedRequirementTestMock();
		
		requirementsInjection.inject(testInstance, requirements);
	}
	
	@Test
	public void testStaticRequirementInjection() {
		requirementsInjection.inject(StaticRequirementTestMock.class, requirements);

		assertThat(StaticRequirementTestMock.getRequirementA().getA(), is("1"));
	}

	private static List<Requirement<?>> asList(Requirement<?>... requirements) {
		return Arrays.asList(requirements);
	}
	
}
