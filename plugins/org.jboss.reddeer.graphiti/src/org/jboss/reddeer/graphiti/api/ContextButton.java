package org.jboss.reddeer.graphiti.api;

/**
 * API for context button.
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 *
 */
public interface ContextButton {

	/**
	 * Click the context button.
	 */
	void click();

	/**
	 * Retuns a text of the context button.
	 * 
	 * @return text
	 */
	String getText();
}
