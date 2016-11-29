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
package org.jboss.reddeer.uiforms.matcher;

import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.forms.widgets.FormText;
import org.jboss.reddeer.core.matcher.WithTextMatcher;
import org.jboss.reddeer.uiforms.handler.FormTextHandler;

/**
 * Matcher matching text of {@link org.eclipse.ui.forms.widgets.FormText}. 
 * 
 * @author rhopp
 *
 */
public class FormTextWithTextMatcher extends WithTextMatcher {

	/**
	 * Creates new FormTextWithTextMatcher matching text of FormText widget to specified text.
	 * 
	 * @param text text to match text of form text widget
	 */
	public FormTextWithTextMatcher(String text) {
		super(text);
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.core.matcher.AbstractWidgetWithTextMatcher#extractWidgetText(org.eclipse.swt.widgets.Widget)
	 */
	@Override
	protected String extractWidgetText(Widget widget) {
		if (widget instanceof FormText) {
			return FormTextHandler.getInstance().getText((FormText) widget);
		}
		return null;
	}

}
