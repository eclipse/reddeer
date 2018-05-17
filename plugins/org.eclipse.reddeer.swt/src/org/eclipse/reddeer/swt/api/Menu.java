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

import org.hamcrest.Matcher;

/**
 * API for menu manipulation
 * @author rawagner
 *
 */
public interface Menu extends Widget<org.eclipse.swt.widgets.Menu>{
	
	/**
	 * Gets menu items of the menu
	 * @return menu items of the menu
	 */
	java.util.List<MenuItem> getItems();

	/**
	 * Checks if a menu item with a given text exists
	 * 
	 * @param text
	 *            menu item text
	 * @return true if a menu item exists; false otherwise
	 */
	boolean hasItem(String... text);

	/**
	 * Checks if a menu item matching with a given matcher exists
	 * 
	 * @param matchers
	 * @return true if a menu item exists; false otherwise
	 */
	boolean hasItem(Matcher<String>... matcher);

	/**
	 * Finds menu item with given path
	 * @param path of menu item to find
	 * @return menu item
	 */
	MenuItem getItem(String... path);
	
	/**
	 * Finds menu item matching given matchers
	 * @param matchers 
	 * @return menu item
	 */
	@SuppressWarnings("unchecked")
	MenuItem getItem(Matcher<String>... matchers);
	/**
	 * Checks whether menu is enabled
	 * @return true if menu is enabled, false otherwise
	 */
	boolean isEnabled();
	
	/**
	 * Checks whether menu is visible
	 * @return true if menu is visible, false otherwise
	 */
	boolean isVisible();
	
	/**
	 * Returns parent menu
	 * @return parent menu
	 */
	Menu getParentMenu();

}
