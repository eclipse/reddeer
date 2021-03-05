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
package org.eclipse.reddeer.core.matcher;

import org.eclipse.reddeer.core.handler.LinkHandler;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Widget;
import org.hamcrest.Matcher;

/**
 * Matcher matching text of {@link Link} to specified text. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class LinkTextMatcher extends WithTextMatcher {
	

	/**
	 * Creates new LinkTextMatcher matching specified text to text of {@link Link}.
	 * 
	 * @param text text to match text of {@link Link}
	 */
	public LinkTextMatcher(String text) {
		super(text);
	}

	/**
	 * Creates new LinkTextMatcher matching specified string matcher to text of {@link Link}.
	 * 
	 * @param matcher matcher to match text of {@link Link}
	 */
	public LinkTextMatcher(Matcher<String> matcher) {
		super(matcher);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.core.matcher.AbstractWidgetWithTextMatcher#extractWidgetText(org.eclipse.swt.widgets.Widget)
	 */
	@Override
	protected String extractWidgetText(Widget widget) {
		if (widget instanceof Link){
			return LinkHandler.getInstance().getText((Link) widget);	
		}
		return null;
	}
}
