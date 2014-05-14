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
	 * Sets the edit part with a given label.
	 * 
	 * @param text Text
	 */
	void setLabel(String label);

}
