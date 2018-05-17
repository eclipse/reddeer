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
package org.eclipse.reddeer.uiforms.handler;

import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.common.util.ResultRunnable;
import org.eclipse.reddeer.core.handler.ControlHandler;

/**
 * Contains methods for handling UI operations on {@link Form} widget
 * @author rawagner
 *
 */
public class FormHandler extends ControlHandler{
	
	private static FormHandler instance;
	
	/**
	 * Gets instance of FormHandler.
	 * 
	 * @return instance of FormHandler
	 */
	public static FormHandler getInstance(){
		if(instance == null){
			instance = new FormHandler();
		}
		return instance;
	}
	
	/**
	 * Gets text of form
	 * @param form to handle
	 * @return text of specified form
	 */
	public String getText(final Form form){
		return Display.syncExec(new ResultRunnable<String>() {

			@Override
			public String run() {
				return form.getText();
			}
		});
	}

}
