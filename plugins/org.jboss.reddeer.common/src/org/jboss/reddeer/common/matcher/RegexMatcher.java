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
package org.jboss.reddeer.common.matcher;

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
