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
package org.eclipse.reddeer.junit.test.annotation;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.annotation.Annotation;
import java.util.List;

import org.eclipse.reddeer.common.exception.RedDeerException;
import org.eclipse.reddeer.junit.annotation.AnnotationUtils;
import org.eclipse.reddeer.junit.test.annotation.resources.AnotherAnnotation;
import org.eclipse.reddeer.junit.test.annotation.resources.ChildClass;
import org.eclipse.reddeer.junit.test.annotation.resources.CustomMethodAnnotation;
import org.eclipse.reddeer.junit.test.annotation.resources.CustomMethodAnnotation2;
import org.eclipse.reddeer.junit.test.annotation.resources.ConfigurableTestRequirement;
import org.eclipse.reddeer.junit.test.annotation.resources.ConfigurableTestRequirement.ConfigurableTestReq;
import org.eclipse.reddeer.junit.test.annotation.resources.DefaultTestRequirement;
import org.eclipse.reddeer.junit.test.annotation.resources.DefaultTestRequirement.SomeReq;
import org.eclipse.reddeer.junit.test.annotation.resources.GrandChildClass;
import org.eclipse.reddeer.junit.test.annotation.resources.SampleAnnotation;
import org.eclipse.reddeer.junit.test.annotation.resources.SimpleTestClassWithAnnotatedMethod;
import org.eclipse.reddeer.junit.test.annotation.resources.TestClassWithDefRequirement;
import org.eclipse.reddeer.junit.test.annotation.resources.TestClassWithTwoRequirements;
import org.eclipse.reddeer.junit.test.annotation.resources.TopClass;
import org.eclipse.reddeer.junit.test.annotation.resources.YetAnotherAnnotation;
import org.junit.Test;

/**
 * 
 * Tests for annotation utils.
 * 
 * @author mlabuda@redhat.com
 *
 */
public class AnnotationUtilsTest {

	@Test
	public void testGetTopClassAnnotations() {
		List<Annotation> annotations = AnnotationUtils.getClassLevelAnnotations(TopClass.class);

		assertTrue("There should be 3 annotation collected from top class ", annotations.size() == 3);
	}

	@Test
	public void testGetChildsClassAnnotations() {
		List<Annotation> annotations = AnnotationUtils.getClassLevelAnnotations(ChildClass.class);

		assertTrue("There should be 3 annotation collected from child class, but there are " + annotations.size()
				+ " annotations", annotations.size() == 3);
		assertTrue("Annotation SampleAnnotation should override parent annotation.",
				getAnnotationValue(SampleAnnotation.class, annotations).equals("child"));
		assertTrue("Annotation AnotherAnnotation should override parent annotation.",
				getAnnotationValue(AnotherAnnotation.class, annotations).equals("child"));
		assertTrue("Annotation YetAnotherAnnotation should be coming from parent annotation.",
				getAnnotationValue(YetAnotherAnnotation.class, annotations).equals("parent"));
	}

	@Test
	public void testGetGrandChildClassAnotations() {
		List<Annotation> annotations = AnnotationUtils.getClassLevelAnnotations(GrandChildClass.class);

		assertTrue("There should be 3 annotation collected from grandchild class, but there are " + annotations.size()
				+ " annotations", annotations.size() == 3);

		assertTrue("Annotation SampleAnnotation should come from parent annotation.",
				getAnnotationValue(SampleAnnotation.class, annotations).equals("child"));
		assertTrue("Annotation AnotherAnnotation should come from parent annotation.",
				getAnnotationValue(AnotherAnnotation.class, annotations).equals("child"));
		assertTrue("Annotation YetAnotherAnnotation should override parent annotation.",
				getAnnotationValue(YetAnotherAnnotation.class, annotations).equals("grand"));
	}

	@Test
	public void getAnnotationWithNoEnclosingClass() {
		assertTrue("There should be no enclosing requirement obtained for annotation in separate file",
				AnnotationUtils.getEnclosingRequirementClass(AnotherAnnotation.class) == null);
	}
	
	@Test
	public void testGetDefaultRequirementFromAnnotation() {
		Class<?> clazz = AnnotationUtils.getEnclosingRequirementClass(SomeReq.class);

		assertNotNull(clazz);
		assertTrue("Requirement class of annotation SomeReq should be DefaultTestRequirement.",
				DefaultTestRequirement.class.isAssignableFrom(clazz));
	}

	@Test
	public void testGetRequirementAnnotation() {
		List<Annotation> annotations = AnnotationUtils.getRequirementAnnotations(TestClassWithDefRequirement.class);
		assertTrue("There should be obtained 1 requirement class for TestClassWithDefRequirement class, but there is/are "
				+ annotations.size(),
				annotations.size() == 1);
	}
	
	@Test
	public void testGetRequirementAnnotations() {
		assertTrue("There should be obtained 2 requirement class for TestClassWithTwoRequirements class",
				AnnotationUtils.getRequirementAnnotations(TestClassWithTwoRequirements.class).size() == 2);
	}
	
	@Test
	public void testGetCustomRequirementFromAnnotation() {
		Class<?> clazz = AnnotationUtils.getEnclosingRequirementClass(ConfigurableTestReq.class);

		assertNotNull(clazz);
		assertTrue("Requirement class of annotation SomeReq should be DefaultTestRequirement.",
				ConfigurableTestRequirement.class.isAssignableFrom(clazz));
	}

	@Test
	public void testGetValueOfStaticAnnotatedMethod() {
		String value = AnnotationUtils.invokeStaticMethodWithAnnotation(
				SimpleTestClassWithAnnotatedMethod.class, CustomMethodAnnotation.class, String.class);

		assertTrue("Value of invoked method should be 'value'.", value.equals("value"));
	}

	@Test
	public void testGetValueOfStaticAnnotatedMethodWhichDoesNotExist() {
		String value = AnnotationUtils.invokeStaticMethodWithAnnotation(
				SimpleTestClassWithAnnotatedMethod.class, CustomMethodAnnotation2.class, String.class);
		assertNull(value);
	}

	@Test(expected=RedDeerException.class)
	public void testGetValueOfStaticAnnotatedMethodWithWrongReturnType() {
		AnnotationUtils.invokeStaticMethodWithAnnotation(SimpleTestClassWithAnnotatedMethod.class,
				CustomMethodAnnotation.class, ChildClass.class);
	}

	private String getAnnotationValue(Class<? extends Annotation> annotationClass, List<Annotation> annotations) {
		for (Annotation annotation : annotations) {
			if (annotation.annotationType().equals(annotationClass)) {
				if (annotationClass.equals(SampleAnnotation.class)) {
					return ((SampleAnnotation) annotation).someValue();
				} else if (annotationClass.equals(AnotherAnnotation.class)) {
					return ((AnotherAnnotation) annotation).someValue();
				} else if (annotationClass.equals(YetAnotherAnnotation.class)) {
					return ((YetAnotherAnnotation) annotation).someValue();
				}
			}
		}
		return "";
	}
}
