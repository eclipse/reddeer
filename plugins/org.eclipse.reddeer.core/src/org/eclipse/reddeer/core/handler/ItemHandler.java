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

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Item;
import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.common.util.ResultRunnable;

/**
 * Contains methods for handling UI operations on {@link Item} widget
 * @author rawagner
 *
 */
public class ItemHandler extends WidgetHandler{
	
	
	private static ItemHandler instance;
	
	/**
	 * Gets instance of ItemHandler.
	 * 
	 * @return instance of ItemHandler
	 */
	public static ItemHandler getInstance(){
		if(instance == null){
			instance = new ItemHandler();
		}
		return instance;
	}
	
	/**
	 * Gets image of item
	 * @param swtItem to handle
	 * @return image of specified item
	 */
	public Image getImage(Item swtItem){
		return Display.syncExec(new ResultRunnable<Image>() {

			@Override
			public Image run() {
				return swtItem.getImage();
			}
		});
	}
	
	/**
	 * Gets text of item
	 * @param swtItem item to handle
	 * @return text of specified item
	 */
	public String getText(Item swtItem){
		return Display.syncExec(new ResultRunnable<String>() {

			@Override
			public String run() {
				return swtItem.getText();
			}
		});
	}
	
	

}
