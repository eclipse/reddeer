/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.gef.handler;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.common.util.Display;

/**
 * Handler for {@link org.eclipse.gef.EditPart}.
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 *
 */
public class EditPartHandler {

	protected final Logger log = Logger.getLogger(this.getClass());
	private static EditPartHandler instance;
	
	/**
	 * Gets instance of EditPartHandler.
	 * 
	 * @return instance of EditPartHandler
	 */
	public static EditPartHandler getInstance(){
		if(instance == null){
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
