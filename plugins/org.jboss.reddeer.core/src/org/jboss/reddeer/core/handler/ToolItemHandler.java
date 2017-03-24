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

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.jboss.reddeer.common.util.Display;
import org.jboss.reddeer.common.util.ResultRunnable;


/**
 * Contains methods for handling UI operations on {@link ToolItem} widgets. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class ToolItemHandler {

	private static ToolItemHandler instance;

	private ToolItemHandler() {

	}

	/**
	 * Gets instance of ToolItemHandler.
	 * 
	 * @return instance of ToolItemHandler
	 */
	public static ToolItemHandler getInstance() {
		if (instance == null) {
			instance = new ToolItemHandler();
		}
		return instance;
	}

	/**
	 * Click specified {@link ToolItem}.
	 * 
	 * @param toolItem tool item to handle
	 */
	public void click(final ToolItem toolItem) {
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				if (((toolItem.getStyle() & SWT.TOGGLE) != 0)
						|| ((toolItem.getStyle() & SWT.CHECK) != 0)
						|| ((toolItem.getStyle() & SWT.RADIO) != 0)) {
					toolItem.setSelection(!toolItem.getSelection());
				}
			}
		});

		WidgetHandler.getInstance().sendClickNotifications(toolItem);
	}
	
	/**
	 * Finds out whether specified {@link ToolItem} is selected or not.

	 * @param toolItem tool item to handle
	 * @return true if specified tool item is selected, false otherwise
	 */
	public boolean isSelected(final ToolItem toolItem) {
		boolean selectionState = Display.syncExec(new ResultRunnable<Boolean>() {
			@Override
			public Boolean run() {
					return toolItem.getSelection(); 
			}
		});
		return selectionState;
	}
	
	/**
	 * Returns ToolBar to which this ToolItem belongs to.
	 *
	 * @param item the item
	 * @return the parent
	 */
	
	public ToolBar getParent(final ToolItem item){
		return Display.syncExec(new ResultRunnable<ToolBar>() {
			@Override
			public ToolBar run() {
				return item.getParent();
			}
		});
	}
	
	/**
	 * Returns true if given ToolItem has style SWT.DROP_DOWN.
	 * 
	 * @param item
	 *            item to investigate.
	 * @return true if given ToolItem has style SWT.DROP_DOWN.
	 */
	
	public boolean isDropDown(final ToolItem item){
		return Display.syncExec(new ResultRunnable<Boolean>() {
			@Override
			public Boolean run() {
				return (SWT.DROP_DOWN & item.getStyle()) == SWT.DROP_DOWN;
			}
		});
	}

	/**
	 * Sends SWT.Selection event to given ToolItem with detail SWT.ARROW. This
	 * should simulate the little arrow to be clicked.
	 * 
	 * @param item
	 *            ToolItem to be clicked on.
	 */

	public void clickDropDown(final ToolItem item) {
		WidgetHandler.getInstance().notifyItem(SWT.Selection, SWT.ARROW, item,
				item);
	}
	
	/**
	 * Gets tooltip text of tool item
	 * @param item to handle
	 * @return tooltip text of specified tool item
	 */
	public String getToolTipText(final ToolItem item){
		return Display.syncExec(new ResultRunnable<String>() {

			@Override
			public String run() {
				return item.getToolTipText();
			}
		});
	}
	
	/**
	 * Checks if tool item is enabled
	 * @param item to handle
	 * @return true if tool item is enabled, false otherwise
	 */
	public boolean isEnabled(final ToolItem item){
		return Display.syncExec(new ResultRunnable<Boolean>() {

			@Override
			public Boolean run() {
				return item.isEnabled();
			}
		});
	}

	/**
	 * Sets focus to tool item
	 * @param swtItem to handle
	 */
	public void setFocus(ToolItem swtItem) {
		ControlHandler.getInstance().setFocus(getParent(swtItem));
		
	}
}
