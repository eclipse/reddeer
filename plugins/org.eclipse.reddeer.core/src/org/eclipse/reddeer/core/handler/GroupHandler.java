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
package org.eclipse.reddeer.core.handler;

import org.eclipse.swt.widgets.Group;
import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.common.util.ResultRunnable;

/**
 * Contains methods for handling UI operations on {@link Group} widget
 * @author rawagner
 *
 */
public class GroupHandler extends ControlHandler{
	
	private static GroupHandler instance;
	
	/**
	 * Gets instance of GroupHandler.
	 * 
	 * @return instance of GroupHandler
	 */
	public static GroupHandler getInstance(){
		if(instance == null){
			instance = new GroupHandler();
		}
		return instance;
	}
	
	/**
	 * Gets text of group
	 * @param group to handle
	 * @return text of specified group
	 */
	public String getText(Group group){
		return Display.syncExec(new ResultRunnable<String>() {

			@Override
			public String run() {
				return group.getText();
			}
		});
	}

}
