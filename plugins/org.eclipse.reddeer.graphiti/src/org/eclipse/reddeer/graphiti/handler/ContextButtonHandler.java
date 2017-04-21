/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.graphiti.handler;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.graphiti.tb.ContextButtonEntry;
import org.eclipse.graphiti.tb.IContextButtonEntry;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.common.util.ResultRunnable;
import org.eclipse.reddeer.graphiti.api.ContextButton;
import org.eclipse.reddeer.graphiti.impl.contextbutton.internal.BasicContextButton;

/**
 * Handler for ContextButton operations.
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 * 
 */
public class ContextButtonHandler {

	protected final Logger log = Logger.getLogger(this.getClass());
	private static ContextButtonHandler instance;
	
	/**
	 * Gets instance of ContextButtonHandler.
	 * 
	 * @return instance of ContextButtonHandler
	 */
	public static ContextButtonHandler getInstance(){
		if(instance == null){
			instance = new ContextButtonHandler();
		}
		return instance;
	}

	/**
	 * Clicks on a given context button entry.
	 * 
	 * @param contextButtonEntry
	 *            Context button entry
	 */
	public void click(final IContextButtonEntry contextButtonEntry) {
		Display.asyncExec(new Runnable() {
			@Override
			public void run() {
				contextButtonEntry.execute();
			}
		});
	}

	/**
	 * Returns a text from a given context button entry.
	 * 
	 * @param contextButtonEntry
	 *            Context button entry
	 * @return text
	 */
	public String getText(final IContextButtonEntry contextButtonEntry) {
		return Display.syncExec(new ResultRunnable<String>() {
			@Override
			public String run() {
				return contextButtonEntry.getText();
			}
		});
	}

	/**
	 * Returns a list of context buttons from sub menu of a given context button entry.
	 * 
	 * @param contextButtonEntry
	 *            Context button entry
	 * @return list of context buttons from sub menu
	 */
	public List<ContextButton> getContextButtons(final IContextButtonEntry contextButtonEntry) {
		List<ContextButton> entries = new ArrayList<ContextButton>();
		if (contextButtonEntry instanceof ContextButtonEntry) {
			ContextButtonEntry entry = (ContextButtonEntry) contextButtonEntry;
			List<IContextButtonEntry> menuEntries = entry.getContextButtonMenuEntries();
			for (IContextButtonEntry menuEntry : menuEntries) {
				entries.add(new BasicContextButton(menuEntry));
			}
		}
		return entries;
	}
}
