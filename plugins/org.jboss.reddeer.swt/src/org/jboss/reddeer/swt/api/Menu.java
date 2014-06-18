package org.jboss.reddeer.swt.api;

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

}
