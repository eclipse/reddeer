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

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.common.util.ResultRunnable;
/**
 * Contains methods for handling UI operations on {@link Widget}. 
 * 
 * @author Jiri Peterka
 * @author Rastislav Wagner
 * @author Jaroslav Jankovic
 */
public class WidgetHandler {
	
	private static final Logger log = Logger.getLogger(WidgetHandler.class);
	private static WidgetHandler instance;
	
	/**
	 * Gets instance of WidgetHandler.
	 * 
	 * @return instance of WidgetHandler
	 */
	public static WidgetHandler getInstance(){
		if(instance == null){
			instance = new WidgetHandler();
		}
		return instance;
	}
	
	/**
	 * Finds out whether specified {@link Widget} is disposed or not.
	 * 
	 * @param widget widget to handle
	 * @return true if widget is disposed, false otherwise
	 */
	public boolean isDisposed(final Widget widget) {
		return Display.syncExec(new ResultRunnable<Boolean>() {

			@Override
			public Boolean run() {
				return widget.isDisposed();
			}
		});
	}


	/**
	 * Gets style of specified widget.
	 *
	 * @param w widget to handle
	 * @return style of specified widget
	 */
	public int getStyle(final Widget w) {
		return Display.syncExec(new ResultRunnable<Integer>() {

			@Override
			public Integer run() {
				return w.getStyle();
			}
		});
	}
	
	/**
	 * Sends a click (SWT.Selection) notification to specified widget.
	 * 
	 * @param widget widget to handle
	 */
	public void sendClickNotifications(Widget widget) {
		notifyWidget(SWT.Selection, widget);
	}

	/**
	 * Notifies specified widget about the event of specified event type. 
	 * See {@link Event}.
	 * 
	 * @param eventType type of the event
	 * @param widget widget to handle
	 */
	public void notifyWidget(int eventType, Widget widget) {
		Event event = createEvent(widget);
		notifyWidget(eventType, event, widget);
	}

	/**
	 * Notifies specified widget about the event of specified event type with 
	 * specified details and item. See {@link Event}.
	 * 
	 * @param eventType type of the event
	 * @param detail details of the event
	 * @param widget widget to handle
	 * @param widgetItem item of the event
	 */
	public void notifyItem(int eventType, int detail, Widget widget,
			Widget widgetItem) {
		Event event = createEventItem(eventType, detail, widget, widgetItem);
		notifyWidget(eventType, event, widget);
	}

	/**
	 * Notifies specified widget about the mouse event of specified event type,
	 * specified position, button and item. See {@link Event}.
	 * 
	 * @param eventType type of the event
	 * @param detail details of the event
	 * @param widget widget to handle
	 * @param widgetItem item of the event
	 * @param x x of the event
	 * @param y y of the event
	 * @param button button of the event
	 */
	public void notifyItemMouse(int eventType, int detail, Widget widget,
			Widget widgetItem, int x, int y, int button) {
		Event event = createMouseItemEvent(eventType, detail, widget,
				widgetItem, x, y, button);
		notifyWidget(eventType, event, widget);
	}

	/**
	 * Notifies specified widget about specified event of specified type. See {@link Event}.
	 * 
	 * @param eventType type of specified event
	 * @param createEvent event
	 * @param widget widget to handle
	 */
	public void notifyWidget(final int eventType, final Event createEvent,
			final Widget widget) {
		createEvent.type = eventType;

		log.trace("Notify " + widget.getClass().getSimpleName() + " with event " + eventType);
		Display.asyncExec(new Runnable() {
			public void run() {
				if ((widget == null) || widget.isDisposed()) {
					return;
				}

				widget.notifyListeners(eventType, createEvent);
			}
		});

		log.trace("Wait for synchronization");
		// Wait for synchronization
		Display.syncExec(new Runnable() {
			public void run() {
				// do nothing here
			}
		});
	}

	private Event createEvent(Widget widget) {
		Event event = new Event();
		event.time = (int) System.currentTimeMillis();
		event.widget = widget;
		event.display = Display.getDisplay();
		return event;
	}

	private Event createEventItem(int eventType, int detail, Widget widget,
			Widget widgetItem) {
		Event event = new Event();
		event.display = Display.getDisplay();
		event.time = (int) System.currentTimeMillis();
		event.item = widgetItem;
		event.widget = widget;
		event.detail = detail;
		event.type = eventType;
		return event;
	}

	private Event createMouseItemEvent(int eventType, int detail,
			Widget widget, Widget widgetItem, int x, int y, int button) {
		Event event = new Event();
		event.display = Display.getDisplay();
		event.time = (int) System.currentTimeMillis();
		event.item = widgetItem;
		event.widget = widget;
		event.detail = detail;
		event.type = eventType;
		event.button = button;
		event.x = x;
		event.y = y;
		if(eventType == SWT.MouseDoubleClick){
			event.count=2;
		}
		return event;
	}
	
	/**
	 * Returns widget data object
	 * @param swtWidget widget to get data from
	 * @return widget data
	 */
	public Object getData(final Widget swtWidget){
		log.debug("Get widget data");
		return Display.syncExec(new ResultRunnable<Object>() {

			@Override
			public Object run() {
				return swtWidget.getData();
			}
		});
	}
	
	/**
	 * Returns widget data object with given key
	 * @param swtWidget widget to get data from
	 * @param key data key
	 * @return widget data
	 */
	public Object getData(final Widget swtWidget, final String key) {
		log.debug("Get widget data with key "+key);
		return Display.syncExec(new ResultRunnable<Object>() {

			@Override
			public Object run() {
				return swtWidget.getData(key);
			}
		});
	}
}
