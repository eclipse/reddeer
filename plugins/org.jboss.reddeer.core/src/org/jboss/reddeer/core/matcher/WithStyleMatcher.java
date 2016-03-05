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
import org.jboss.reddeer.core.exception.CoreLayerException;
import org.jboss.reddeer.core.handler.WidgetHandler;

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
