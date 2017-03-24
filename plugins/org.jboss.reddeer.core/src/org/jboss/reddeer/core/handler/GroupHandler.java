/******************************************************************************* 
 * Copyright (c) 2017 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/
package org.jboss.reddeer.core.handler;

import org.eclipse.swt.widgets.Group;
import org.jboss.reddeer.common.util.Display;
import org.jboss.reddeer.common.util.ResultRunnable;

/**
 * Contains methods for handling UI operations on {@link Group} widget
 * @author rawagner
 *
 */
public class GroupHandler {
	
	private static GroupHandler instance;
	
	private GroupHandler(){}
	
	/**
	 * Gets instance of GroupHandler
	 * @return instance of GroupHander
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
