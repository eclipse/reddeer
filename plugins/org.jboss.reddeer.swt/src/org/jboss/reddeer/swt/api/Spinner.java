package org.jboss.reddeer.swt.api;

import org.jboss.reddeer.swt.widgets.Widget;

/**
 * API For Spinner manipulation
 * 
 * @author Andrej Podhradsky
 * 
 */
public interface Spinner extends Widget{

	/**
	 * Get Value of the spinner
	 * 
	 * @return value
	 */
	int getValue();

	/**
	 * Set a given value into the spinner
	 * 
	 * @param value
	 */
	void setValue(int value);
	
	org.eclipse.swt.widgets.Spinner getSWTWidget();
}
