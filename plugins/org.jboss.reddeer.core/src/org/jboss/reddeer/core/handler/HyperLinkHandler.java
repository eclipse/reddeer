package org.jboss.reddeer.core.handler;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.jboss.reddeer.core.util.Display;

/**
 * Contains methods that handle UI operations on {@link Hyperlink} widgets. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class HyperLinkHandler {

	private static HyperLinkHandler instance;

	private HyperLinkHandler() {

	}

	/**
	 * Creates and returns instance of ComboHandler class
	 * 
	 * @return
	 */
	public static HyperLinkHandler getInstance() {
		if (instance == null) {
			instance = new HyperLinkHandler();
		}
		return instance;
	}

	/**
	 * Activates widget - link/hyperlink etc
	 * @param w widget to activate
	 */
	public void activate(final Hyperlink hyperLink) {
		Display.asyncExec(new Runnable() {

			@Override
			public void run() {
				hyperLink.setFocus();
				notifyHyperlink(SWT.MouseEnter, hyperLink);
				notifyHyperlink(SWT.MouseDown, hyperLink);
				notifyHyperlink(SWT.MouseUp, hyperLink);
			}
		});
	}
	
	/**
	 * Notifies widget with the event 
	 * @param eventType
	 * @param widget
	 */
	public void notifyHyperlink(int eventType, Hyperlink widget) {
		Event event = createHyperlinkEvent(widget);
		WidgetHandler.getInstance().notify(eventType, event, widget);
	}
	
	private Event createHyperlinkEvent(Hyperlink widget){
		Event event = new Event();
		event.time = (int) System.currentTimeMillis();
		event.widget = widget;
		event.display = Display.getDisplay();
		event.button=1;
		event.x=0;
		event.y=0;
		return event;
	}
}
