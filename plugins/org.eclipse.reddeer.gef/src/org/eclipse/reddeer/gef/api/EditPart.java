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
package org.eclipse.reddeer.gef.api;

/**
 * API for edit part manipulation.
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 *
 */
public interface EditPart {

	/**
	 * Selects the edit part.
	 */
	void select();

	/**
	 * Clicks on the edit part.
	 */
	void click();

	/**
	 * Sets the edit part with a given label.
	 *
	 * @param label
	 *            the new label
	 */
	void setLabel(String label);

	/**
	 * Returns GEF Edit Part enclosed by this edit part.
	 * 
	 * @return the enclosed edit part
	 */
	org.eclipse.gef.EditPart getGEFEditPart();

}
