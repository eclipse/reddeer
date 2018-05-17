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
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.eclipse.reddeer.core.exception.CoreLayerException;
import org.eclipse.reddeer.core.handler.WidgetHandler;

/**
 * Matcher matching style of {@link Widget}.
 * 
 * @author jjankovi
 * @author Radoslav Rabara
 * 
 */
public class WithStyleMatcher extends BaseMatcher<Integer> {

	private int style; 
	
	/**
	 * Constructs WithStyleMatcher matching style of {@link Widget} to specified style.
	 * 
	 * @param style style to match style of {@link Widget} 
	 * 
	 */
	public WithStyleMatcher(int style) {
		this.style = style;
	}
	
	/* (non-Javadoc)
	 * @see org.hamcrest.Matcher#matches(java.lang.Object)
	 */
	@Override
	public boolean matches(Object item) {
		if (item instanceof Widget){
			try {
				Integer widgetStyle = WidgetHandler.getInstance().getStyle((Widget)item);
				return (widgetStyle.intValue() & style) == style;
			} catch (CoreLayerException sle) {
				// object is not supported by widget handler mechanism 'getStyle'
				return false;
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.hamcrest.SelfDescribing#describeTo(org.hamcrest.Description)
	 */
	@Override
	public void describeTo(Description description) {
		description.appendText("has style " + style);
	}
	
	/* (non-Javadoc)
	 * @see org.hamcrest.BaseMatcher#toString()
	 */
	@Override
	public String toString() {
		return "Widget matcher matching widgets with style: " + style;
	}
}
