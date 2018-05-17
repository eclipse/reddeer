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
 * API for tool bar item manipulation.
 * 
 * @author Jiri Peterka
 *
 */
public interface ToolItem extends Item<org.eclipse.swt.widgets.ToolItem> {

	/**
	 * Clicks the tool item.
	 */
	void click();

	/**
	 * Gets ToolTip text of the tool item.
	 * 
	 * @return text of ToolTip
	 */
	String getToolTipText();

	/**
	 * Returns whether the tool item is selected or not.
	 * 
	 * @return true if the tool item is selected, false otherwise
	 */
	boolean isSelected();

	/**
	 * Toggles button of the the tool item.
	 * 
	 * @param toggle the button of the tool item or not
	 */
	void toggle(boolean toggle);
	
	/**
	 * Checks if tool item is enabled
	 * @return true if tool item is enabled, false otherwise
	 */
	boolean isEnabled();
	
	/**
	 * Returns parent toolbar
	 * @return parent toolbar
	 */
	ToolBar getParent();
}
