package org.jboss.reddeer.core.matcher;

import org.eclipse.swt.widgets.Widget;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

/**
 * The purpose of this class is to match with {@link Widget}
 * that has the same type as the given class,
 * or its type is extending the given class.
 * 
 * @author jpeterka
 * @author rawagner
 * @author Radoslav Rabara
 * 
 */
@SuppressWarnings("rawtypes")
public class ClassMatcher extends BaseMatcher {

	private Class<? extends Widget> c;
	
	/**
	 * Constructs matcher matching {@link Widget}s
	 * with the same type as the specified class <var>c</var>
	 * or type extending the specified class <var>c</var>
	 * 
	 * @param c The {@link Class} which is used to evaluate matching
	 */
	public ClassMatcher(Class<? extends Widget> c) {
		if(c==null)
			throw new NullPointerException("c is null");
		this.c = c;
	}
	
	@Override
	public boolean matches(Object item) {
		return c.isAssignableFrom(item.getClass());
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("has the same type as " + c +" or its type extends " + c);
	}
	
	@Override
	public String toString() {
		return "Matcher matching widget with the same type as or type extending " + c;
	}
}
