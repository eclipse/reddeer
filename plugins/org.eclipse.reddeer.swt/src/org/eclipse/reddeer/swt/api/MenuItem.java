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

import java.util.List;

/**
 * API for menu (menu item) manipulation.
 * 
 * @author Jiri Peterka
 *
 */

public interface MenuItem extends Item<org.eclipse.swt.widgets.MenuItem> {

	/**
	 * Selects the menu.
	 */
	void select();

	/**
	 * Checks whether menu is selected or not - related only to CHECK and RADIO
	 * styled menus.
	 * 
	 * @return true if menu is selected, false otherwise
	 */
	boolean isSelected();

	/**
	 * Returns title text of the menu.
	 * 
	 * @return menu text
	 */
	String getText();	
	
	/**
	 * Returns child items of menu item
	 * 
	 * @return child items of the menu item
	 */
	List<MenuItem> getChildItems();

	/**
	 * Retrieves available menu items.
	 * @return List of enabled child menu items.
	 */
	List<MenuItem> getAvailableChildItems();
	
	/**
	 * Checks if menu is enabled
	 * @return true if menu is enabled, false otherwise
	 */
	boolean isEnabled();
	
	/**
	 * Returns menu where this menu item belongs
	 * @return menu where this menu item belongs
	 */
	Menu getParent();
	
	/**
	 * Returns cascade menu
	 * @return cascade menu or null
	 */
	Menu getMenu();

}
