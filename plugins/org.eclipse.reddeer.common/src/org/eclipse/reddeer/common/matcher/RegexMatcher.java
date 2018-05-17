/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.common.matcher;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 * Matcher to match text (String) to given regular expression.
 * 
 * @author rhopp
 *
 */

public class RegexMatcher extends TypeSafeMatcher<String> {

	private final String regex;

	/**
	 * Default constructor.
	 * 
	 * @param regex
	 *            regular expression to match against.
	 */

	public RegexMatcher(String regex) {
		this.regex = regex;
	}

	/* (non-Javadoc)
	 * @see org.hamcrest.SelfDescribing#describeTo(org.hamcrest.Description)
	 */
	@Override
	public void describeTo(Description description) {
		description.appendText("regular expression \"" +
			(regex == null ? "null" : regex)	+ "\"");
	}

	/* (non-Javadoc)
	 * @see org.hamcrest.TypeSafeMatcher#matchesSafely(java.lang.Object)
	 */
	@Override
	protected boolean matchesSafely(String textToMatch) {
		return textToMatch.matches(regex);
	}

	/* (non-Javadoc)
	 * @see org.hamcrest.BaseMatcher#toString()
	 */
	@Override
	public String toString() {
		return "Matcher matching text to regular expression '" +
			(regex == null ? "null" : regex) + "'";
	}

}
