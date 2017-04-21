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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.annotation.Annotation;

import org.eclipse.reddeer.junit.internal.requirement.RequirementAnnotationMatcher;
import org.eclipse.reddeer.junit.test.internal.requirement.TestRequirementA.TestRequirementAAnnotation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;

public class RequirementAnnotationMatcherTest {

	private RequirementAnnotationMatcher matcher = new RequirementAnnotationMatcher();
	
	@Test
	public void nullAnnotation() {
		boolean result = matcher.matches(null);
		
		assertFalse(result);
	}

	@Test
	public void highLevelAnnotation() {
		boolean result = matcher.matches(getAnnotation(HighLevelAnnotationClass.class));
		
		assertFalse(result);
	}
	
	@Test
	public void notAssignableAnnotation() {
		boolean result = matcher.matches(getAnnotation(NonAssignableClass.class));
		
		assertFalse(result);
	}
	
	@Test
	public void requirementAnnotation() {
		boolean result = matcher.matches(getAnnotation(RequirementClass.class));
		
		assertTrue(result);
	}
	
	private Annotation getAnnotation(Class<?> clazz) {
		return clazz.getAnnotations()[0];
	}
	
	@HighLevelAnnotation
	class HighLevelAnnotationClass {
		
	}
	
	@RunWith(Runner.class)
	class NonAssignableClass {
		
	}
	
	@TestRequirementAAnnotation
	class RequirementClass {
		
	}	
}
