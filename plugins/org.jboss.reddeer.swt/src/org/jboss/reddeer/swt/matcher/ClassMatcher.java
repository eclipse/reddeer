package org.jboss.reddeer.swt.matcher;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

/**
 * The purpose of this class is to match two classes where 
 * one is assignable from another.
 * 
 * @author jpeterka
 * @author rawagner
 * @author Radoslav Rabara
 * 
 */
@SuppressWarnings("rawtypes")
public class ClassMatcher extends BaseMatcher {

	private Class<? extends Object> clazz;
	
	/**
	 * Constructs matcher matching class
	 * with the same type as the specified class <var>clazz</var>
	 * or type extending the specified class <var>clazz</var>
	 * 
	 * @param clazz The {@link Class} which is used to evaluate matching
	 */
	public ClassMatcher(Class<? extends Object> clazz) {
		if (clazz == null) {
			throw new NullPointerException("No class has been provided.");
		}
		this.clazz = clazz;
	}
	
	@Override
	public boolean matches(Object item) {
		return clazz.isAssignableFrom(item.getClass());
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("has the same type as " + clazz +" or its type extends " + clazz);
	}
	
	@Override
	public String toString() {
		return "Matcher matching widget with the same type as or type extending " + clazz;
	}
}
