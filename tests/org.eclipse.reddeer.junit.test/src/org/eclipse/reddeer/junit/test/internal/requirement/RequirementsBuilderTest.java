/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.junit.test.internal.requirement;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.eclipse.reddeer.junit.internal.annotation.AnnotationsFinder;
import org.eclipse.reddeer.junit.internal.configuration.RequirementsConfiguration;
import org.eclipse.reddeer.junit.internal.requirement.Requirements;
import org.eclipse.reddeer.junit.internal.requirement.RequirementsBuilder;
import org.eclipse.reddeer.junit.requirement.Requirement;
import org.eclipse.reddeer.junit.test.internal.requirement.TestRequirementA.TestRequirementAAnnotation;
import org.eclipse.reddeer.junit.test.internal.requirement.TestRequirementB.TestRequirementBAnnotation;
import org.junit.Test;

public class RequirementsBuilderTest {

	private RequirementsBuilder builder = new RequirementsBuilder();

	@Test(expected=IllegalArgumentException.class)
	public void nullClass() {
		builder.build(null, mock(RequirementsConfiguration.class), null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void nullConfig() {
		builder.build(this.getClass(), null, null);
	}

	@Test
	public void noAnnotation() {
		builder.setFinder(new TestAnnotationsFinder(new ArrayList<Annotation>()));
		RequirementsConfiguration config = mock(RequirementsConfiguration.class);
		
		Requirements requirements = builder.build(String.class, config, null);

		assertThat(requirements.size(), is(0));
	}

	@Test
	public void annotations() {
		RequirementsConfiguration requirementConfig = mock(RequirementsConfiguration.class);

		builder.setFinder(new TestAnnotationsFinder(getRequirementAnnotations()));
		
		Requirements requirements = builder.build(String.class, requirementConfig, null);
		
		assertThat(requirements.size(), is(2));
		assertThat(requirements, hasItem(new RequirementClassMatcher(TestRequirementA.class, TestRequirementAAnnotation.class)));
		assertThat(requirements, hasItem(new RequirementClassMatcher(TestRequirementB.class, TestRequirementBAnnotation.class)));
		verify(requirementConfig, times(2)).configure(any(Requirement.class));
	}

	class TestAnnotationsFinder extends AnnotationsFinder {

		private List<Annotation> annotations;

		public TestAnnotationsFinder(List<Annotation> annotations) {
			super(null);
			this.annotations = annotations;
		}

		@Override
		public List<Annotation> find(Class<?> clazz) {
			return annotations;
		}
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
	
	private List<Annotation> getRequirementAnnotations() {
		return Arrays.asList(RequirementClass.class.getAnnotations());
	}

	@TestRequirementAAnnotation
	@TestRequirementBAnnotation
	class RequirementClass {

	}
}
