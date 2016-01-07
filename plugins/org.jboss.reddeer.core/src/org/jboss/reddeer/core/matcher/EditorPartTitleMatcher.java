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


import org.eclipse.ui.IEditorPart;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Matcher matching name, title or tooltip of specified {@link IEditorPart}.
 * 
 * @author Lucia Jelinkova
 */
public class EditorPartTitleMatcher extends TypeSafeMatcher<IEditorPart> {

	private Matcher<String> titleMatcher;
	
	/**
	 * Creates new EditorPartTitleMatcher with specified string matcher.
	 * String matcher can match title, name or tooltip of {@link IEditorPart}.
	 * 
	 * @param titleMatcher matcher to match title, name or tooltip
	 */
	public EditorPartTitleMatcher(Matcher<String> titleMatcher) {
		this.titleMatcher = titleMatcher;
	}
	
	/* (non-Javadoc)
	 * @see org.hamcrest.TypeSafeMatcher#matchesSafely(java.lang.Object)
	 */
	@Override
	protected boolean matchesSafely(IEditorPart item) {
		if (titleMatcher.matches(item.getTitle())) {
			return true;
		} 
		
		if (titleMatcher.matches(item.getEditorInput().getName())) {
			return true;
		} 
		
		if (titleMatcher.matches(item.getEditorInput().getToolTipText())) {
			return true;
		}
		
		return false;
	}

	/* (non-Javadoc)
	 * @see org.hamcrest.SelfDescribing#describeTo(org.hamcrest.Description)
	 */
	@Override
	public void describeTo(Description description) {
		description.appendText("EditorPart title matches");
		description.appendDescriptionOf(titleMatcher);
	}
}
