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
	 * Instantiates a new annotations finder.
	 *
	 * @param annotationMatcher the annotation matcher
	 */
	public AnnotationsFinder(Matcher<Annotation> annotationMatcher) {
		super();
		this.annotationMatcher = annotationMatcher;
	}

	/**
	 * Find.
	 *
	 * @param clazz the clazz
	 * @return the list
	 */
	public List<Annotation> find(Class<?> clazz) {
		List<Annotation> annotations = new ArrayList<Annotation>();
		List<Class<?>> present = new ArrayList<Class<?>>();
		do {
			for (Annotation annotation : clazz.getAnnotations()) {
				if (annotationMatcher.matches(annotation)) {
					boolean annotationIsAddedFromSubclass = present
							.contains(annotation.getClass());
					if (!annotationIsAddedFromSubclass) {
						annotations.add(annotation);
						present.add(annotation.getClass());
					}
				}
			}
			clazz = clazz.getSuperclass();
		} while(clazz != null);
		
		return annotations;
	}
}
