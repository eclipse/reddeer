package org.jboss.reddeer.swt.api;

import org.eclipse.swt.widgets.MenuItem;
import org.jboss.reddeer.swt.widgets.Widget;

/**
 * API for Menu (MenuItem) manipulation
 * @author Jiri Peterka
 *
 */

public interface Menu extends Widget{

	/**
	 * Select Menu (MenuItem) instance
	 */
	void select();
	
	/**
	 * Checks if menu is selected - related only to CHECK and RADIO styled menus
	 * @return true if menu is selected, false otherwise
	 */
	boolean isSelected();

	/**
	 * Returns Menu (MenuItem) title text
	 * @return menu text
	 */
	String getText();
	
	MenuItem getSWTWidget();
			
}
