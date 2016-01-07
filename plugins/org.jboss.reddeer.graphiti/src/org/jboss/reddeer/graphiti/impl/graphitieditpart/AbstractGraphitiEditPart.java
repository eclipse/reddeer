/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.graphiti.impl.graphitieditpart;

import java.util.Arrays;
import java.util.List;

import org.eclipse.gef.EditPart;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.gef.impl.editpart.AbstractEditPart;
import org.jboss.reddeer.graphiti.GraphitiLayerException;
import org.jboss.reddeer.graphiti.api.ContextButton;
import org.jboss.reddeer.graphiti.api.GraphitiEditPart;
import org.jboss.reddeer.graphiti.handler.GraphitiEditPartHandler;

/**
 * Abstract class for GraphitiEditPart implementation.
 * 
 * @author Andrej Podhradsky
 *
 */
public class AbstractGraphitiEditPart extends AbstractEditPart implements GraphitiEditPart {

	private static final Logger LOG = Logger.getLogger(AbstractGraphitiEditPart.class);

	/**
	 * Constructs graphiti edit part from {@link org.eclipse.gef.EditPart}.
	 * 
	 * @param editPart
	 */
	public AbstractGraphitiEditPart(org.eclipse.gef.EditPart editPart) {
		super(editPart);
	}

	/**
	 * Constructs graphiti edit part which fulfills a given matcher.
	 * 
	 * @param editPart
	 */
	public AbstractGraphitiEditPart(Matcher<EditPart> matcher, int index) {
		super(matcher, index);
	}

	/**
	 * Constructs graphit edit part which fulfills a given matcher at the specified index.
	 * 
	 * @param editPart
	 */
	public AbstractGraphitiEditPart(Matcher<EditPart> matcher) {
		super(matcher);
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
