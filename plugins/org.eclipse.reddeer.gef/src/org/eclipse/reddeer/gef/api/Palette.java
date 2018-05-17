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

import java.util.List;

/**
 * API for palette manipulation
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com
 *
 */
public interface Palette {

	/**
	 * Activates tool with a given label.
	 *
	 * @param label            Tool label
	 */
	void activateTool(String label);

	/**
	 * Activates tool with a given label in a given group.
	 *
	 * @param label            Tool label
	 * @param group            Group label
	 */
	void activateTool(String label, String group);

	/**
	 * Returns active tool.
	 *
	 * @return active tool
	 */
	String getActiveTool();

	/**
	 * Returns all available tools.
	 *
	 * @return all available tools
	 */
	List<String> getTools();
}
