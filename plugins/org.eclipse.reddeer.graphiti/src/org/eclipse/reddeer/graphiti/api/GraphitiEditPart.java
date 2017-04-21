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
