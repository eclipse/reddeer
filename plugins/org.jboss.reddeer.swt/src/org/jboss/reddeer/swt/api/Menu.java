package org.jboss.reddeer.swt.api;

/**
 * API for Menu (MenuItem) manipulation
 * @author Jiri Peterka
 *
 */

public interface Menu {

	/**
	 * Select Menu (MenuItem) instance
	 */
	void select();

	/**
	 * Returns Menu (MenuItem) title text
	 * @return menu text
	 */
	String getText();
			
}
