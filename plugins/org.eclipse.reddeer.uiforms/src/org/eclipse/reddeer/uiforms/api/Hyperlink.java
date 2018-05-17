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
 * Represents Hyperlink object
 * 
 * @author Lucia Jelinkova
 *
 */
public interface Hyperlink extends Control<org.eclipse.ui.forms.widgets.Hyperlink> {

	/**
	 * Returns hyperlink's text .
	 *
	 * @return the text
	 */
	String getText();

	/**
	 * Cliks the hyperlink.
	 */
	void activate();
}
