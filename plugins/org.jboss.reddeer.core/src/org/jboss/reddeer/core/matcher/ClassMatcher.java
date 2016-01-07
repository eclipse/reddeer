/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.core.matcher;

import org.eclipse.swt.widgets.Widget;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

/**
 * Matcher matching {@link Widget} widget classes.
 * Matching is satisfied if class specified in constructor is same or is superclass of class of object specified 
 * as argument a in method {@link ClassMatcher#matches(Object)}.
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
	 * Constructs new ClassMatcher matcher matching {@link Widget} objects.
	 * For satisfied match it is required the class specified in constructor
	 * is either same class as class of object in matches method or it is superclass
	 * of object provided in matches method.
	 * 
	 * @param c descendant of widget class to be used as a base for matching
	 */
	public ClassMatcher(Class<? extends Widget> c) {
		if(c==null)
			throw new NullPointerException("c is null");
		this.c = c;
	}
	
	/* (non-Javadoc)
	 * @see org.hamcrest.Matcher#matches(java.lang.Object)
	 */
	@Override
	public boolean matches(Object item) {
		return c.isAssignableFrom(item.getClass());
	}

	/* (non-Javadoc)
	 * @see org.hamcrest.SelfDescribing#describeTo(org.hamcrest.Description)
	 */
	@Override
	public void describeTo(Description description) {
		description.appendText("has the same type as " + c +" or its type extends " + c);
	}
	
	/* (non-Javadoc)
	 * @see org.hamcrest.BaseMatcher#toString()
	 */
	@Override
	public String toString() {
		return "Matcher matching widget with the same type as or type extending " + c;
	}
}
