package org.jboss.reddeer.gef.api;

/**
 * API for edit part manipulation.
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 *
 */
public interface EditPart {

	/**
	 * Selects the edit part.
	 */
	void select();

	/**
	 * Clicks on the edit part.
	 */
	void click();

	/**
	 * Sets the edit part with a given label.
	 * 
	 * @param text
	 *            Text
	 */
	void setLabel(String label);

}
