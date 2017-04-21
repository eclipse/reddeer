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
package org.eclipse.reddeer.core.handler;

import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.common.util.ResultRunnable;

/**
 * Contains methods for handling UI operations on {@link TabFolder} widgets.
 * 
 * @author Andrej Podhradsky
 *
 */
public class TabFolderHandler extends ControlHandler{
	
	private static TabFolderHandler instance;
	
	/**
	 * Gets instance of TabFolderHandler.
	 * 
	 * @return instance of TabFolderHandler
	 */
	public static TabFolderHandler getInstance(){
		if(instance == null){
			instance = new TabFolderHandler();
		}
		return instance;
	}

	/**
	 * Gets tab items nested in specified {@link TabFolder}.
	 * 
	 * @param tabFolder tab folder to handle
	 * @return tab items nested in specified tab folder
	 */
	public TabItem[] getTabItems(final TabFolder tabFolder) {
		return Display.syncExec(new ResultRunnable<TabItem[]>() {
			@Override
			public TabItem[] run() {
				return tabFolder.getItems();
			}
		});
	}
	
	/**
	 * Gets list of selected tab items
	 * @param folder to handle
	 * @return list of selected tab items
	 */
	public List<TabItem> getSelection(final TabFolder folder){
		return Display.syncExec(new ResultRunnable<List<TabItem>>() {

			@Override
			public List<TabItem> run() {
				return Arrays.asList(folder.getSelection());
			}
		});
	}
}
