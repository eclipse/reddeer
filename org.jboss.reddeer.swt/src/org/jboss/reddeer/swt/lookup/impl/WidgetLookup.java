package org.jboss.reddeer.swt.lookup.impl;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Widget;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ObjectUtil;

/**
 * Widget Lookup methods
 * @author Jiri Peterka
 *
 */
public class WidgetLookup {
	
	private static WidgetLookup instance = null;
	
	private WidgetLookup() {
	}
	
	public static WidgetLookup getInstance() {
		if (instance == null) instance = new WidgetLookup();
		return instance;
	}
	
	/**
	 * Checks if widget is enabled
	 * @param widget
	 * @return
	 */
	public boolean isEnabled(Widget widget) {
		boolean ret = ((Boolean) ObjectUtil.invokeMethod(widget, "isEnabled")).booleanValue();
		return ret;
	}
	
	/**
	 * Send click notification to a widget
	 * @param widget
	 */
	public void sendClickNotifications(Widget widget) {
		notify(SWT.MouseEnter, widget);
		notify(SWT.MouseMove, widget);
		notify(SWT.Activate, widget);
		notify(SWT.MouseDown, widget);
		notify(SWT.MouseUp, widget);
		notify(SWT.Selection, widget);
		notify(SWT.MouseHover, widget);
		notify(SWT.MouseMove, widget);
		notify(SWT.MouseExit, widget);
		notify(SWT.Deactivate, widget);
		notify(SWT.FocusOut, widget);
	}

	private void notify(int eventType, Widget widget) {
		Event event = createEvent(widget);
		notify(eventType, event, widget);
		
	}

	private Event createEvent(Widget widget) {
		Event event = new Event();
		event.time = (int) System.currentTimeMillis();
		event.widget = widget;
		event.display = Display.getDisplay();
		return event;
	}
	
	private void notify(final int eventType, final Event createEvent, final Widget widget) {
		createEvent.type = eventType;
		
		Display.asyncExec(new Runnable() {
			public void run() {
				if ((widget == null) || widget.isDisposed()) {
					return;
				}
				if (!WidgetLookup.getInstance().isEnabled(widget)) {
					throw new SWTLayerException("Widget is not enabled");
				}
				widget.notifyListeners(eventType, createEvent);
			}
		});

		// Wait for synchronization
		Display.syncExec(new Runnable() {
			public void run() {
				// do nothing here
			}
		});
	}

}
