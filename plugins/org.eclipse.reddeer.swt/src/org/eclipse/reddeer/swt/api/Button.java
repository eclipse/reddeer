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
package org.eclipse.reddeer.swt.api;

/**
 * API for button (push, radio and toggle) manipulation.
 * 
 * @author Jiri Peterka
 *
 */
public interface Button extends Control<org.eclipse.swt.widgets.Button> {

	/**
	 * Performs click on the button.
	 */
	void click();

	/**
	 * Returns text on the button.
	 * 
	 * @return text on the button
	 */
	String getText();
}
