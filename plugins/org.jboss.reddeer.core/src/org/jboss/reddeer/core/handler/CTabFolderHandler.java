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
package org.jboss.reddeer.core.handler;

import org.eclipse.swt.custom.CTabFolder;
import org.jboss.reddeer.core.util.Display;

/**
 * Contains methods for handling UI operations on {@link CTabFolder} widgets.
 * 
 * @author Lucia Jelinkova
 *
 */
public class CTabFolderHandler {

	private static CTabFolderHandler instance;

	private CTabFolderHandler() {

	}

	/**
	 * Gets instance of CTabFolderHandler.
	 * 
	 * @return instance of CTabFolderHandler
	 */
	public static CTabFolderHandler getInstance() {
		if (instance == null) {
			instance = new CTabFolderHandler();
		}
		return instance;
	}

	/**
	 * Sets focus on specified {@link CTabFolder}.
	 * 
	 * @param folder folder to handle
	 */
	public void setFocus(final CTabFolder folder) {
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				folder.forceFocus();
			}
		});
	}
}
