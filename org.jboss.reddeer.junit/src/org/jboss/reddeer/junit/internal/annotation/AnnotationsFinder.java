package org.jboss.reddeer.junit.internal.annotation;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matcher;

/**
 * 
 * Iterates all annotations of a specified class and return those matching the matcher.
 * 
 * @author Lucia Jelinkova
 *
 */
public class AnnotationsFinder {

	private Matcher<Annotation> annotationMatcher;
	
	/**
	 * Constructor
	 * @param annotationMatcher
	 */
	public AnnotationsFinder(Matcher<Annotation> annotationMatcher) {
		super();
		this.annotationMatcher = annotationMatcher;
	}

	/**
	 * 
	 * @param clazz
	 * @return List of annotations matching the matcher
	 */
	public List<Annotation> find(Class<?> clazz){
		List<Annotation> annotations = new ArrayList<Annotation>();
		
		for (Annotation annotation : clazz.getAnnotations()){
			if (annotationMatcher.matches(annotation)){
				annotations.add(annotation);
			}
		}
		
		return annotations;
	}
}
