package org.jboss.reddeer.swt.api;

/**
 * API For Spinner manipulation
 * 
 * @author Andrej Podhradsky
 * 
 */
public interface Spinner {

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
}
