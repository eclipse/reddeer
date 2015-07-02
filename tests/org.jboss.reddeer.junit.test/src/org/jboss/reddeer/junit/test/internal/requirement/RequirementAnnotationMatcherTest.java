package org.jboss.reddeer.junit.test.internal.requirement;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.annotation.Annotation;

import org.jboss.reddeer.junit.internal.requirement.RequirementAnnotationMatcher;
import org.jboss.reddeer.junit.test.internal.requirement.TestRequirementA.TestRequirementAAnnotation;
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
