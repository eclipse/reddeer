package org.jboss.reddeer.core.matcher;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

/**
 * Matches any object with the specified class name (or simple name).
 * 
 * @author apodhrad
 *
 */
public class WithClassNameMatcher extends BaseMatcher<String> {

	private String className;

	/**
	 * Constructs the matcher with a given class name (or simple name).
	 * 
	 * @param className
	 *            Class name (or simple name)
	 */
	public WithClassNameMatcher(String className) {
		this.className = className;
	}

	@Override
	public boolean matches(Object obj) {
		if (obj == null) {
			return false;
		}
		Class<?> clazz = obj.getClass();
		return clazz.getName().equals(className) || clazz.getSimpleName().equals(className);
	}

	@Override
	public void describeTo(Description desc) {
		desc.appendText("wicth class name '" + className + "'");
	}

}
