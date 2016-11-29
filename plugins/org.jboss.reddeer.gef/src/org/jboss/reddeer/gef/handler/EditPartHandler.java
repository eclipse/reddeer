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
package org.jboss.reddeer.gef.handler;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.requests.DirectEditRequest;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.util.Display;

/**
 * Handler for {@link org.eclipse.gef.EditPart}.
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 *
 */
public class EditPartHandler {

	protected final Logger log = Logger.getLogger(this.getClass());

	private static EditPartHandler instance;

	private EditPartHandler() {

	}

	/**
	 * Gets the single instance of EditPartHandler.
	 *
	 * @return single instance of EditPartHandler
	 */
	public static EditPartHandler getInstance() {
		if (instance == null) {
			instance = new EditPartHandler();
		}
		return instance;
	}

	/**
	 * Select a given edit part in the specified viewer.
	 * 
	 * @param editPart
	 *            Edit part
	 */
	public void select(final EditPart editPart) {
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				editPart.getViewer().select(editPart);
			}
		});
	}

	/**
	 * Calls direct editing on the specified edit part.
	 * 
	 * @param editPart
	 *            Edit part
	 */
	public void directEdit(final EditPart editPart) {
		Display.asyncExec(new Runnable() {
			@Override
			public void run() {
				DirectEditRequest request = new DirectEditRequest();
				editPart.performRequest(request);
			}
		});
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				
			}
		});
	}

	/**
	 * Returns an appropriate figure of a given edit part.
	 * 
	 * @param editPart
	 *            Edit part
	 * @return Figure
	 */
	public IFigure getFigure(final EditPart editPart) {
		return ((GraphicalEditPart) editPart).getFigure();
	}

}
