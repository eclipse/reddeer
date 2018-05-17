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
package org.eclipse.reddeer.graphiti.api;

import java.util.List;

import org.eclipse.reddeer.gef.api.EditPart;

/**
 * API for edit part manipulation in Graphiti.
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 * 
 */
public interface GraphitiEditPart extends EditPart {

	/**
	 * Returns all available context buttons for this edit part.
	 * 
	 * @return list of context buttons
	 */
	List<ContextButton> getContextButtons();

	/**
	 * Returns a context button on a given path.
	 * 
	 * @param path
	 *            Path to the context button
	 * @return Context button on a given path
	 */
	ContextButton getContextButton(String... path);

	/**
	 * Double clicks on the edit part.
	 */
	void doubleClick();
}
