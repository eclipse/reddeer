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
 * API for spinner manipulation.
 * 
 * @author Andrej Podhradsky
 * 
 */
public interface Spinner extends Control<org.eclipse.swt.widgets.Spinner> {

	/**
	 * Gets value of the spinner.
	 * 
	 * @return value of the spinner
	 */
	int getValue();

	/**
	 * Sets given value to the spinner.
	 * 
	 * @param value value of the spinner to set
	 */
	void setValue(int value);
}
