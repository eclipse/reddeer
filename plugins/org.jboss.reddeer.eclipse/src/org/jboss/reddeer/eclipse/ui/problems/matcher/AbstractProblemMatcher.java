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
package org.jboss.reddeer.eclipse.ui.problems.matcher;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;
import org.jboss.reddeer.core.matcher.AbstractWidgetWithTextMatcher;
import org.jboss.reddeer.eclipse.ui.problems.ProblemsView.Column;

/**
 *
 * Abstract problem matcher is a parent matcher for a specific columns in Problems view.
 * Matching works either as precise match on a specified string or by passing proper matcher for matching.
 * 
 * @author mlabuda@redhat.com
 * @since 0.7
 *
 */
public abstract class AbstractProblemMatcher extends AbstractWidgetWithTextMatcher {

	protected Matcher<String> matcher;
	
	/**
	 * Creates a new problem matcher matching to whole text of a column.
	 * 
	 * @param text whole column text of a problem to match
	 */
	public AbstractProblemMatcher(String text) {
		this(new IsEqual<String>(text));
	}
	
	/**
	 * Creates a new problem matcher matching with matcher passed as argument.
	 * 
	 * @param matcher matcher to match column of a problem
	 */
	public AbstractProblemMatcher(Matcher<String> matcher) {
		if (matcher == null) {
			throw new IllegalArgumentException("Matcher cannot be null.");
		}
		this.matcher = matcher;
	}
	
	/**
	 * Gets label of a column of a specific problem.
	 * @return label of a column of a specific problem
	 */
	public abstract Column getColumn();
	
	/* (non-Javadoc)
	 * @see org.hamcrest.SelfDescribing#describeTo(org.hamcrest.Description)
	 */
	@Override
	public void describeTo(Description description) {
		description.appendDescriptionOf(matcher);
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.core.matcher.AbstractWidgetWithTextMatcher#matches(java.lang.String)
	 */
	@Override
	protected boolean matches(String text) {
		return matcher.matches(text);
	}
}
