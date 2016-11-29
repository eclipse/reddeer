/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.swt.api;

import java.util.List;
import org.eclipse.swt.widgets.MenuItem;
import org.jboss.reddeer.swt.widgets.Widget;

/**
 * API for menu (menu item) manipulation.
 * 
 * @author Jiri Peterka
 *
 */

public interface Menu extends Widget {

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

	MenuItem getSWTWidget();
	
	
	/**
	 * Returns text of child items of the menu.
	 * 
	 * @return text of child items of the menu
	 */
	List<String> getChildItems();

}
