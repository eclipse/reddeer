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

import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.common.util.ResultRunnable;
import org.eclipse.reddeer.core.handler.ControlHandler;

/**
 * Contains methods for handling UI operations on
 * {@link org.eclipse.ui.forms.widgets.Section} widgets.
 * 
 * @author Lucia Jelinkova
 *
 */
public class SectionHandler extends ControlHandler{
	
	private static SectionHandler instance;
	
	/**
	 * Gets instance of SectionHandler.
	 * 
	 * @return instance of SectionHandler
	 */
	public static SectionHandler getInstance(){
		if(instance == null){
			instance = new SectionHandler();
		}
		return instance;
	}
	
	/**
	 * Sets specified {@link Section} to specified expand state.
	 * 
	 * @param section section to handle
	 * @param expanded true for expand specified section, false for collapse
	 */
	public void setExpanded(final Section section, final boolean expanded) {
		Display.syncExec(new Runnable() {
			
			@Override
			public void run() {
				section.setExpanded(expanded);
			}
		});
	}
	
	/**
	 * Gets text of section
	 * @param section to handle
	 * @return text of specified section
	 */
	public String getText(final Section section){
		return Display.syncExec(new ResultRunnable<String>() {

			@Override
			public String run() {
				return section.getText();
			}
		});
	}
}
