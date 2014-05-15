package org.jboss.reddeer.graphiti.impl.graphitieditpart;

import java.util.List;

import org.jboss.reddeer.gef.impl.editpart.LabeledEditPart;
import org.jboss.reddeer.graphiti.api.ContextButton;
import org.jboss.reddeer.graphiti.api.GraphitiEditPart;
import org.jboss.reddeer.graphiti.handler.GraphitiEditPartHandler;

/**
 * Graphiti EditPart implementation which is looking for a given label inside the edit part.
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 *
 */
public class LabeledGraphitiEditPart extends LabeledEditPart implements GraphitiEditPart {

	/**
	 * Finds a graphiti edit part with a given label.
	 * 
	 * @param label
	 *            Label
	 */
	public LabeledGraphitiEditPart(String label) {
		this(label, 0);
	}

	/**
	 * Finds a graphiti edit part with a given label at the specified index.
	 * 
	 * @param label
	 *            Label
	 * @param index
	 *            Index
	 */
	public LabeledGraphitiEditPart(String label, int index) {
		super(label, index);
	}

	@Override
	public ContextButton getContextButton(String label) {
		List<ContextButton> contextButtons = GraphitiEditPartHandler.getInstance().getContextButtons(editPart);
		for (ContextButton contextButton : contextButtons) {
			if (contextButton.getText().equals(label)) {
				return contextButton;
			}
		}
		return null;
	}

	@Override
	public void doubleClick() {
		GraphitiEditPartHandler.getInstance().doubleClick(editPart);
	}

}
