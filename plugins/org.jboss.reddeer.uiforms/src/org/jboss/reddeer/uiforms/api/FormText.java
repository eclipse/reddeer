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
package org.jboss.reddeer.uiforms.api;

import org.jboss.reddeer.swt.api.Widget;

/**
 * Represents FormText @see{
 * 
 * @author rhopp
 *
 */

public interface FormText extends Widget<org.eclipse.ui.forms.widgets.FormText> {

	/**
	 * Returns whole text of this FormText.
	 *
	 * @return text
	 */
	public String getText();

	/**
	 * Returns currently selected text.
	 *
	 * @return selected text
	 */

	public String getSelectionText();

	/**
	 * Clicks on this formText (invokes linkActivated HyperlinkEvent).
	 */
	public void click();

	/**
	 * Checks for focus.
	 *
	 * @return true if this FormText is focused
	 */
	public boolean hasFocus();

	/**
	 * Returns tooltip text of this FormText.
	 *
	 * @return the tooltip text
	 */
	public String getTooltipText();

	/**
	 * Sets focus to this widget.
	 */
	public void setFocus();
}
