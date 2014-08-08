package org.jboss.reddeer.graphiti.api;

import java.util.List;

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

	/**
	 * Returns a list of context buttons from sub menu.
	 * 
	 * @return list of context buttons from sub menu.
	 */
	List<ContextButton> getContextButtons();
}
