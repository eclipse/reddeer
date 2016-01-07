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

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.jboss.reddeer.core.util.Display;
import org.jboss.reddeer.core.util.ResultRunnable;

/**
 * Contains methods for handling UI operations on {@link TabItem} widgets.
 * 
 * @author Vlado Pakan
 *
 */
public class TabItemHandler {

	private static TabItemHandler instance;

	private TabItemHandler() {

	}

	/**
	 * Gets instance of TabItemHandler.
	 * 
	 * @return instance of TabItemHandler
	 */
	public static TabItemHandler getInstance() {
		if (instance == null) {
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
	 * Notifies {@link TabFolder} listeners about the event.
	 * Field for event type in specified event has to be set properly.
	 * 
	 * @param event event to notify about
	 * @param swtTabFolder encapsulating parent tab folder
	 */
	public void notifyTabFolder(final Event event, final TabFolder swtTabFolder) {
		Display.syncExec(new Runnable() {
			public void run() {
				swtTabFolder.notifyListeners(event.type, event);
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
		Display.syncExec(new Runnable() {
			public void run() {
				swtTabItem.getParent().setSelection(swtTabItem);
			}
		});
	}

	/**
	 * Focuses specified {@link TabItem}.
	 * 
	 * @param tabItem tab item to focus
	 */
	public void setFocus(final TabItem tabItem) {
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				tabItem.getParent().forceFocus();
			}
		});
	}
}
