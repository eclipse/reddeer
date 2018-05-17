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
package org.eclipse.reddeer.uiforms.api;

import org.eclipse.reddeer.swt.api.Control;

/**
 * Represents FormText @see{
 * 
 * @author rhopp
 *
 */

public interface FormText extends Control<org.eclipse.ui.forms.widgets.FormText> {

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
}
