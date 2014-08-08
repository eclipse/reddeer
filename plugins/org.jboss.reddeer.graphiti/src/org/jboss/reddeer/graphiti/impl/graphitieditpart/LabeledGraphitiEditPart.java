package org.jboss.reddeer.graphiti.impl.graphitieditpart;

import java.util.Arrays;
import java.util.List;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.gef.impl.editpart.LabeledEditPart;
import org.jboss.reddeer.graphiti.GraphitiLayerException;
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

	private static final Logger LOG = Logger.getLogger(LabeledGraphitiEditPart.class);

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
	public List<ContextButton> getContextButtons() {
		return GraphitiEditPartHandler.getInstance().getContextButtons(editPart);
	}

	@Override
	public ContextButton getContextButton(String... label) {
		LOG.info("Looking for context button " + Arrays.toString(label));
		List<ContextButton> contextButtons = getContextButtons();
		ContextButton contextButton = find(label[0], contextButtons);
		for (int i = 1; i < label.length; i++) {
			contextButton = find(label[i], contextButton.getContextButtons());
		}
		return contextButton;
	}

	/**
	 * Finds a context button from a given list. If no such context button exists then the GraphitiLayerException is
	 * thrown.
	 * 
	 * @param label
	 *            Label of the context button
	 * @param entries
	 *            List of context buttons
	 * @return Context button from the list
	 */
	private ContextButton find(String label, List<ContextButton> entries) {
		for (ContextButton entry : entries) {
			if (entry.getText().equals(label)) {
				return entry;
			}
		}
		LOG.debug("Cannot find context button with label '" + label + "'");
		LOG.debug("Available context buttons:");
		for (ContextButton entry : entries) {
			LOG.debug("\t'" + entry.getText() + "'");
		}
		throw new GraphitiLayerException("Cannot find context button '" + label + "'");
	}

	@Override
	public void doubleClick() {
		GraphitiEditPartHandler.getInstance().doubleClick(editPart);
	}

}
