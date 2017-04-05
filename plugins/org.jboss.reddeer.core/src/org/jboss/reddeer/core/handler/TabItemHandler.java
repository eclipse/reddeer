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
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.jboss.reddeer.common.util.Display;
import org.jboss.reddeer.common.util.ResultRunnable;

/**
 * Contains methods for handling UI operations on {@link TabItem} widgets.
 * 
 * @author Vlado Pakan
 *
 */
public class TabItemHandler extends ItemHandler{

	private static TabItemHandler instance;
	
	/**
	 * Gets instance of TabItemHandler.
	 * 
	 * @return instance of TabItemHandler
	 */
	public static TabItemHandler getInstance(){
		if(instance == null){
			instance = new TabItemHandler();
		}
		return instance;
	}
	
	/**
	 * Gets {@link TabFolder} containing specified {@link TabItem}.
	 * 
	 * @param swtTabItem tab item to handle
	 * @return encapsulating tab folder
	 */
	public TabFolder getTabFolder(final TabItem swtTabItem) {
		return Display
				.syncExec(new ResultRunnable<org.eclipse.swt.widgets.TabFolder>() {
					@Override
					public org.eclipse.swt.widgets.TabFolder run() {
						return swtTabItem.getParent();
					}
				});
	}
	
	/**
	 * Gets the control that is displayed in the content area of the tab item.
	 * @param tabItem item to handler
	 * @return control that is displayed in the content area of the tab item
	 */
	public Control getControl(final TabItem tabItem){
		return Display.syncExec(new ResultRunnable<Control>() {
			
			@Override
			public Control run() {
				return tabItem.getControl();
			}
		});
	}

	/**
	 * Creates the event of specified type for specified {@link TabItem}.
	 * 
	 * @param swtTabItem tab item to handle
	 * @param type type of the event
	 * @return event for specified tab item
	 */
	public Event createEventForTabItem(final TabItem swtTabItem, int type) {
		Event event = new Event();
		event.type = type;
		event.display = Display.getDisplay();
		event.time = (int) System.currentTimeMillis();
		event.item = swtTabItem;
		event.widget = getTabFolder(swtTabItem);
		return event;
	}

	/**
	 * Selects specified {@link TabItem}.
	 *  
	 * @param swtTabItem tab item to select
	 */
	public void select(final TabItem swtTabItem) {
		ControlHandler.getInstance().checkModalShells(getParent(swtTabItem));
		Display.syncExec(new Runnable() {
			public void run() {
				swtTabItem.getParent().setSelection(swtTabItem);
			}
		});
		notifyTabFolder(createEventForTabItem(swtTabItem,SWT.Selection), getParent(swtTabItem));
	}

	/**
	 * Focuses specified {@link TabItem}.
	 * 
	 * @param tabItem tab item to focus
	 */
	public void setFocus(final TabItem tabItem) {
		ControlHandler.getInstance().checkModalShells(getParent(tabItem));
		ControlHandler.getInstance().setFocus(getParent(tabItem));
	}
	
	/**
	 * Gets parent of tab item
	 * @param tabItem to handle
	 * @return TabFolder which is parent of specified tab item
	 */
	public TabFolder getParent(final TabItem tabItem){
		return Display.syncExec(new ResultRunnable<TabFolder>() {

			@Override
			public TabFolder run() {
				return tabItem.getParent();
			}
		});
	}

	/**
	 * Gets tooltip text of tab item
	 * @param swtWidget to handle
	 * @return tooltip text of specified tab item
	 */
	public String getToolTipText(TabItem swtWidget) {
		return Display.syncExec(new ResultRunnable<String>() {

			@Override
			public String run() {
				return swtWidget.getToolTipText();
			}
		});
	}
	
	/**
	 * Notifies {@link TabFolder} listeners about the event.
	 * Field for event type in specified event has to be set properly.
	 * 
	 * @param event event to notify about
	 * @param swtTabFolder encapsulating parent tab folder
	 */
	private void notifyTabFolder(final Event event, final TabFolder swtTabFolder) {
		Display.syncExec(new Runnable() {
			public void run() {
				swtTabFolder.notifyListeners(event.type, event);
			}
		});
	}

}
