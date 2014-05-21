package org.jboss.reddeer.graphiti.api;

import org.jboss.reddeer.gef.api.EditPart;

/**
 * API for edit part manipulation in Graphiti.
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 *
 */
public interface GraphitiEditPart extends EditPart {

	ContextButton getContextButton(String label);

	void doubleClick();
}
