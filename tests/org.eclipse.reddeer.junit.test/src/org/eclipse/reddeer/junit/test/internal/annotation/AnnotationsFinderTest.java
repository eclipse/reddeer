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
package org.eclipse.reddeer.junit.test.internal.annotation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.eclipse.reddeer.junit.internal.annotation.AnnotationsFinder;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;

public class AnnotationsFinderTest {

	private AnnotationsFinder finder;

	@Before
	public void setup(){
		finder = new AnnotationsFinder(new AnnotationMatcher());
	}
	
	@Test
	public void noAnnotationClass() {
		List<Annotation> annotations = finder.find(NoAnnotationsClass.class);

		assertEquals(0, annotations.size());
	}

	@Test
	public void noMatchedAnnotationClass() {
		List<Annotation> annotations = finder.find(NoMatchedAnnotationClass.class);

		assertEquals(0, annotations.size());
	}

	@Test
	public void matchedAnnotationClass() {
		List<Annotation> annotations = finder.find(MatchedAnnotationClass.class);

		assertEquals(1, annotations.size());
		assertThat(annotations, CoreMatchers.hasItem(new AnnotationClassMatcher(AnnotationA.class)));
	}

	@Test
	public void multipleAnnotationsClass() {
		List<Annotation> annotations = finder.find(MultipleAnnotationsClass.class);

		assertEquals(2, annotations.size());
		assertThat(annotations, CoreMatchers.hasItem(new AnnotationClassMatcher(AnnotationA.class)));
		assertThat(annotations, CoreMatchers.hasItem(new AnnotationClassMatcher(AnnotationB.class)));
	}

	@Test
	public void withNoMatchedAnnotationSuperClass() {
		List<Annotation> annotations = finder.find(WithNoMatchedAnnotationSuperClass.class);

		assertEquals(0, annotations.size());
	}

	@Test
	public void withMatchedAnnotationSuperClass() {
		List<Annotation> annotations = finder.find(WithMatchedAnnotationSuperClass.class);

		assertEquals(1, annotations.size());
		assertThat(annotations, CoreMatchers.hasItem(new AnnotationClassMatcher(AnnotationA.class)));
	}

	@Test
	public void multipleAnnotationsClassWithMatcherAnnotationSuperClass() {
		List<Annotation> annotations = finder.find(MultipleAnnotationClassWithMatcherAnnotationSuperClass.class);

		assertEquals(2, annotations.size());
		assertThat(annotations, CoreMatchers.hasItem(new AnnotationClassMatcher(AnnotationA.class)));
		assertThat(annotations, CoreMatchers.hasItem(new AnnotationClassMatcher(AnnotationB.class)));
	}

	class NoAnnotationsClass {

	}

	@RunWith(Runner.class)
	class NoMatchedAnnotationClass {

	}

	@AnnotationA
	@RunWith(Runner.class)
	class MatchedAnnotationClass {

	}

	@AnnotationA
	@RunWith(Runner.class)
	@AnnotationB
	@Ignore
	class MultipleAnnotationsClass {

	}

	class WithNoMatchedAnnotationSuperClass extends NoMatchedAnnotationClass {

	}

	class WithMatchedAnnotationSuperClass extends MatchedAnnotationClass {

	}

	@AnnotationA
	@RunWith(Runner.class)
	@AnnotationB
	@Ignore
	class MultipleAnnotationClassWithMatcherAnnotationSuperClass extends MatchedAnnotationClass {

	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	@interface AnnotationA {
		
	}
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	@interface AnnotationB {
		
	}
	
	class AnnotationMatcher extends TypeSafeMatcher<Annotation> {

		@Override
		public boolean matchesSafely(Annotation item) {
			Class<?> annotationType = item.annotationType();
			return annotationType.equals(AnnotationA.class) || annotationType.equals(AnnotationB.class);
		}
		
		public void describeTo(Description description) {
			
		}
	}
	
	class AnnotationClassMatcher extends TypeSafeMatcher<Annotation> {

		private Class<?> clazz;
		
		AnnotationClassMatcher(Class<?> clazz){
			this.clazz = clazz;
		}
		
		@Override
		public boolean matchesSafely(Annotation annotation) {
			return clazz.equals(annotation.annotationType());
		}

		public void describeTo(Description description) {
			description.appendText("instance of ");
			description.appendValue(clazz);
		}
	}
}
