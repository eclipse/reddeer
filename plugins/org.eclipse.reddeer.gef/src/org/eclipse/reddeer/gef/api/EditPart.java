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
