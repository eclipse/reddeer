/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
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
