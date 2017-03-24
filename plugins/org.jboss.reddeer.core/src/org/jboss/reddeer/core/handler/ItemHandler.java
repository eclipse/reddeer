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

import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.TreeItem;
import org.jboss.reddeer.common.util.Display;
import org.jboss.reddeer.common.util.ResultRunnable;

/**
 * Contains methods for handling UI operations on {@link Item} widget
 * @author rawagner
 *
 */
public class ItemHandler {
	
	private static ItemHandler instance;
	
	private ItemHandler(){}
	
	/**
	 * Gets instance of ItemHandler
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
	
	/**
	 * Sets focus to item
	 * @param swtItem item to handle
	 */
	public void setFocus(Item swtItem){
		if(swtItem instanceof CTabItem){
			CTabItemHandler.getInstance().setFocus((CTabItem)swtItem);
		} else if (swtItem instanceof ExpandItem){
			ExpandItemHandler.getInstance().setFocus(((ExpandItem)swtItem));
		} else if (swtItem instanceof TabItem) {
			TabItemHandler.getInstance().setFocus((TabItem)swtItem);
		} else if (swtItem instanceof TableItem){
			TableItemHandler.getInstance().setFocus((TableItem)swtItem);
		} else if (swtItem instanceof ToolItem){
			 ToolItemHandler.getInstance().setFocus((ToolItem)swtItem);
		} else if (swtItem instanceof TreeItem) {
			TreeItemHandler.getInstance().setFocus((TreeItem)swtItem);
		}
	}

}
