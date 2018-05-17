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

import org.eclipse.swt.widgets.Widget;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;
import org.eclipse.reddeer.core.exception.CoreLayerException;
import org.eclipse.reddeer.core.matcher.AbstractWidgetWithTextMatcher;
import org.eclipse.reddeer.core.util.TextWidgetUtil;

/**
 * Matcher matching tool tip of {@link Widget}.
 * 
 * @author rhopp
 *
 */
public class WithTooltipTextMatcher extends AbstractWidgetWithTextMatcher {

	private Matcher<String> matcher;
	
	/**
	 * Creates new WithTooltipTextMatcher matching tool tip of {@link Widget} to specified text.
	 * 
	 * @param text tool tip text to match tool tip of {@link Widget}
	 */
	public WithTooltipTextMatcher(String text) {
		this(new IsEqual<String>(text));
	}
	
	

	/**
	 * Creates new WithTooltipTextMatcher matching tool tip of {@link Widget} to specified text.
	 * 
	 * @param matcher text matcher to match tool tip of {@link Widget}
	 */
	public WithTooltipTextMatcher(Matcher<String> matcher) {
		if (matcher == null)
			throw new NullPointerException("matcher is null");
		this.matcher = matcher;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.core.matcher.AbstractWidgetWithTextMatcher#extractWidgetText(org.eclipse.swt.widgets.Widget)
	 */
	@Override
	protected String extractWidgetText(Widget widget) {
		try{
			return TextWidgetUtil.getToolTipText(widget);
		} catch (CoreLayerException ex) {
			return null;
		}
	}
	
	/* (non-Javadoc)
	 * @see org.hamcrest.SelfDescribing#describeTo(org.hamcrest.Description)
	 */
	@Override
	public void describeTo(Description description) {
		description.appendText("with tooltip ").appendDescriptionOf(matcher);

	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.core.matcher.AbstractWidgetWithTextMatcher#matches(java.lang.String)
	 */
	@Override
	protected boolean matches(String text) {
		return matcher.matches(text.replaceAll("&", "").split("\t")[0]);
	}
	
	/* (non-Javadoc)
	 * @see org.hamcrest.BaseMatcher#toString()
	 */
	@Override
	public String toString() {
		return "Matcher matching widget which tooltip matches: "+matcher.toString();
	}
}
