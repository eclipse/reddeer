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
package org.eclipse.reddeer.common.matcher;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

/**
 * Matcher matching class of object to class name.
 * Simple name of full name (package and class name) of object are matched to class name. 
 * 
 * @author apodhrad
 *
 */
public class WithClassNameMatcher extends BaseMatcher<String> {

	private String className;

	/**
	 * Constructs new WithTextNameMatcher to match class name of object to specified class name.
	 * 
	 * @param className class name (with package) or simple class name to match
	 */
	public WithClassNameMatcher(String className) {
		this.className = className;
	}

	/* (non-Javadoc)
	 * @see org.hamcrest.Matcher#matches(java.lang.Object)
	 */
	@Override
	public boolean matches(Object obj) {
		if (obj == null) {
			return false;
		}
		Class<?> clazz = obj.getClass();
		return clazz.getName().equals(className) || clazz.getSimpleName().equals(className);
	}

	/* (non-Javadoc)
	 * @see org.hamcrest.SelfDescribing#describeTo(org.hamcrest.Description)
	 */
	@Override
	public void describeTo(Description desc) {
		desc.appendText("wicth class name '" + className + "'");
	}

}
