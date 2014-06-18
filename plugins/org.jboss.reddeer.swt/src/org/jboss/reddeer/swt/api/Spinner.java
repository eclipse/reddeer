package org.jboss.reddeer.swt.api;

import org.jboss.reddeer.swt.widgets.Widget;

/**
 * API for spinner manipulation.
 * 
 * @author Andrej Podhradsky
 * 
 */
public interface Spinner extends Widget {

	/**
	 * Gets value of the spinner.
	 * 
	 * @return value of the spinner
	 */
	int getValue();

	/**
	 * Sets given value to the spinner.
	 * 
	 * @param value value of the spinner to set
	 */
	void setValue(int value);
	
	org.eclipse.swt.widgets.Spinner getSWTWidget();
}
