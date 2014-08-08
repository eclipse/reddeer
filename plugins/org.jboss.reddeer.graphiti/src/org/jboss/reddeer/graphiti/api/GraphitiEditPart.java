package org.jboss.reddeer.graphiti.api;

import java.util.List;

import org.jboss.reddeer.gef.api.EditPart;

/**
 * API for edit part manipulation in Graphiti.
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 * 
 */
public interface GraphitiEditPart extends EditPart {

	/**
	 * Returns all available context buttons for this edit part.
	 * 
	 * @return list of context buttons
	 */
	List<ContextButton> getContextButtons();

	/**
	 * Returns a context button on a given path.
	 * 
	 * @param path
	 *            Path to the context button
	 * @return Context button on a given path
	 */
	ContextButton getContextButton(String... path);

	/**
	 * Double clicks on the edit part.
	 */
	void doubleClick();
}
