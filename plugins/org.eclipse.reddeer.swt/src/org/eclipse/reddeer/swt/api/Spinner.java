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
