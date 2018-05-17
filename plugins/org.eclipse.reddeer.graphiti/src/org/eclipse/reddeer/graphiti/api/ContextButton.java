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

/**
 * API for context button.
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 * 
 */
public interface ContextButton {

	/**
	 * Click the context button.
	 */
	void click();

	/**
	 * Retuns a text of the context button.
	 * 
	 * @return text
	 */
	String getText();

	/**
	 * Returns a list of context buttons from sub menu.
	 * 
	 * @return list of context buttons from sub menu.
	 */
	List<ContextButton> getContextButtons();
}
