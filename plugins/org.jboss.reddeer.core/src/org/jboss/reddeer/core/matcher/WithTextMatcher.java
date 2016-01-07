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
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;

/**
 * Matcher matching text of {@link Widget}.
 *  
 * @author Jiri Peterka
 * @author Radoslav Rabara
 * 
 */
public class WithTextMatcher extends AbstractWidgetWithTextMatcher {

	private Matcher<String> matcher;
	
	/**
	 * Constructs new WithTextMatcher matching text of {@link Widget} to specified text.
	 * 
	 * @param text text to match text of {@link Widget}
	 * 
	 */
	public WithTextMatcher(String text) {
		this(new IsEqual<String>(text));
	}

	/**
	 * Constructs new WithTextMatcher matching text of {@link Widget} with specified text matcher.
	 * 
	 * @param matcher text matcher to match text of {@link Widget}
	 * 
	 */
	public WithTextMatcher(Matcher<String> matcher) {
		if (matcher == null)
			throw new NullPointerException("matcher is null");
		this.matcher = matcher;
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.core.matcher.AbstractWidgetWithTextMatcher#matches(java.lang.String)
	 */
	@Override
	protected boolean matches(String text) {
		return matcher.matches(text);
	}
	
	/* (non-Javadoc)
	 * @see org.hamcrest.SelfDescribing#describeTo(org.hamcrest.Description)
	 */
	@Override
	public void describeTo(Description description) {
		description.appendText("with text ").appendDescriptionOf(matcher);
	}
	
	/* (non-Javadoc)
	 * @see org.hamcrest.BaseMatcher#toString()
	 */
	@Override
	public String toString() {
		return "Matcher matching widget which text matches: "+matcher.toString();
	}
}
