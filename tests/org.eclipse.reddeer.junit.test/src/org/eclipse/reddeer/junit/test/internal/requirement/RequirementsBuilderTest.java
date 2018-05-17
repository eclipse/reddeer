/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.junit.test.internal.requirement;

import static org.junit.Assert.assertTrue;

import java.lang.annotation.Annotation;
import java.util.HashSet;

import org.eclipse.reddeer.junit.internal.configuration.RequirementConfigurationSet;
import org.eclipse.reddeer.junit.internal.requirement.Requirements;
import org.eclipse.reddeer.junit.internal.requirement.RequirementsBuilder;
import org.eclipse.reddeer.junit.requirement.Requirement;
import org.eclipse.reddeer.junit.requirement.configuration.MissingRequirementConfiguration;
import org.eclipse.reddeer.junit.requirement.configuration.RequirementConfiguration;
import org.eclipse.reddeer.junit.test.internal.requirement.TestRequirementA.TestRequirementAAnnotation;
import org.eclipse.reddeer.junit.test.internal.requirement.TestRequirementB.TestRequirementBAnnotation;
import org.eclipse.reddeer.junit.test.requirement.configuration.resources.JavaRequirement.CustomConfigJavaRequirementAAnnotation;
import org.eclipse.reddeer.junit.test.requirement.configuration.resources.JavaRequirementConfig;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;

public class RequirementsBuilderTest {

	private RequirementsBuilder builder = new RequirementsBuilder();

	@Test(expected=IllegalArgumentException.class)
	public void testNullClassArgument() {
		builder.build(new RequirementConfigurationSet(), null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNullConfigArgument() {
		builder.build(null, this.getClass());
	}

	@Test
	public void testBuildNoRequirements() {
		RequirementConfigurationSet configurationSet = new RequirementConfigurationSet();
		configurationSet.setConfigurations(new HashSet<RequirementConfiguration>());
		Requirements requirements = builder.build(configurationSet, this.getClass());
		
		assertTrue("There should be no requirements, but there are some.", requirements.size() == 0);
	}
	
	@Test
	public void testBuildRequirements() {
		RequirementConfigurationSet configurationSet = new RequirementConfigurationSet();
		configurationSet.setConfigurations(new HashSet<RequirementConfiguration>());
		Requirements requirements = builder.build(configurationSet, RequirementClass.class);
		
		assertTrue("There should be precisely 2 requirements, but there is/are " + requirements.size(), requirements.size() == 2);
	}
	
	@Test
	public void testBuildRequirementsWithConfigurableRequirementWithoutConfiguration() {
		RequirementConfigurationSet configurationSet = new RequirementConfigurationSet();
		configurationSet.addConfiguration(new MissingRequirementConfiguration());
		
		Requirements requirements = builder.build(configurationSet, ConfigurableRequirementClass.class);

		assertTrue("There should be no requirement, but there is/are " + requirements.size(), requirements.size() == 0);
	}
	
	@Test
	public void testBuildRequirementsWithConfiguredRequirement() {
		RequirementConfigurationSet configurationSet = new RequirementConfigurationSet();
		configurationSet.addConfiguration(new JavaRequirementConfig());
		
		Requirements requirements = builder.build(configurationSet, ConfigurableRequirementClass.class);
		assertTrue("There should be precisely 1 requirements, but there is/are " + requirements.size(), requirements.size() == 1);
	}
	
	@Test
	public void testBuildRequiremenetsWithMixedRequirements() {
		RequirementConfigurationSet configurationSet = new RequirementConfigurationSet();
		configurationSet.addConfiguration(new JavaRequirementConfig());
		configurationSet.addConfiguration(new JavaRequirementConfig());
		
		Requirements requirements = builder.build(configurationSet, CombinedRequirementsClass.class);
		
		assertTrue("There should be precisely 2 requirements, but there is/are " + requirements.size(), requirements.size() == 2);
		
	}
	
	class RequirementClassMatcher extends TypeSafeMatcher<Requirement<?>> {
		
		private Class<?> requirementClass;
		
		private Class<?> requirementDeclaration;

		public RequirementClassMatcher(Class<?> requirementClass, Class<?> requirementAnnotation) {
			super();
			this.requirementClass = requirementClass;
			this.requirementDeclaration = requirementAnnotation;
		}
		
		@Override
		public boolean matchesSafely(Requirement<?> item) {
			return requirementClass.equals(item.getClass()) && requirementDeclaration.equals(getDeclaration(item).annotationType());
		}
		
		public void describeTo(Description description) {
			description.appendText("instance of ");
			description.appendValue(requirementClass);
			description.appendText(" with declaration annotation of type ");
			description.appendValue(requirementDeclaration);
		}
		
		private Annotation getDeclaration(Requirement<?> requirement){
			try {
				return (Annotation) requirement.getClass().getDeclaredMethod("getDeclaration").invoke(requirement);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	@TestRequirementAAnnotation
	@TestRequirementBAnnotation
	class RequirementClass {}
	
	@TestRequirementAAnnotation
	@CustomConfigJavaRequirementAAnnotation
	class CombinedRequirementsClass {}
	
	@CustomConfigJavaRequirementAAnnotation
	class ConfigurableRequirementClass {}
}
