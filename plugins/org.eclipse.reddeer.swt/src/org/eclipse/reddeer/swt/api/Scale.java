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
 * API for scale manipulation.
 * 
 * @author Jiri Peterka
 *
 */
public interface Scale extends Control<org.eclipse.swt.widgets.Scale> {

	/**
	 * Returns minimum value of the scale.
	 * 
	 * @return minimum value of the scale.
	 */
	int getMinimum();

	/**
	 * Returns maximum value of the scale.
	 * 
	 * @return maximum value of the scale
	 */
	int getMaximum();

	/**
	 * Returns current value of the scale.
	 * 
	 * @return current value of the scale
	 */
	int getSelection();

	/**
	 * Sets current value of the scale.
	 * 
	 * @param value value of the scale to select
	 */
	void setSelection(int value);
}
