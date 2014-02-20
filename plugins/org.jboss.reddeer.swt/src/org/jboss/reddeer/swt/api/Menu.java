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
	 * Returns Menu (MenuItem) title text
	 * @return menu text
	 */
	String getText();
	
	MenuItem getSWTWidget();
			
}
