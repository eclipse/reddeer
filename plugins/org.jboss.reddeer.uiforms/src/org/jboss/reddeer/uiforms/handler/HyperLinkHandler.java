package org.jboss.reddeer.uiforms.handler;

import org.eclipse.swt.SWT;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.jboss.reddeer.swt.lookup.WidgetLookup;
import org.jboss.reddeer.swt.util.Display;

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
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				hyperLink.setFocus();
				WidgetLookup.getInstance().notifyHyperlink(SWT.MouseEnter, hyperLink);
				WidgetLookup.getInstance().notifyHyperlink(SWT.MouseDown, hyperLink);
				WidgetLookup.getInstance().notifyHyperlink(SWT.MouseUp, hyperLink);
			}
		});
	}
}
