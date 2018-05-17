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
package org.eclipse.reddeer.uiforms.handler;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.common.util.ResultRunnable;
import org.eclipse.reddeer.core.handler.ControlHandler;

/**
 * Contains methods that handle UI operations on {@link Hyperlink} widgets. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class HyperLinkHandler extends ControlHandler{
	
	private static HyperLinkHandler instance;
	
	/**
	 * Gets instance of HyperLinkHandler.
	 * 
	 * @return instance of HyperLinkHandler
	 */
	public static HyperLinkHandler getInstance(){
		if(instance == null){
			instance = new HyperLinkHandler();
		}
		return instance;
	}

	/**
	 * Activates specified hyper link widget.
	 *
	 * @param hyperLink the hyper link
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
	 * Notifies specified hyper link widget with specified event.
	 *  
	 * @param eventType event to notify specified hyper link about
	 * @param widget widget to notify
	 */
	public void notifyHyperlink(int eventType, Hyperlink widget) {
		Event event = createHyperlinkEvent(widget);
		notifyWidget(eventType, event, widget);
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
	
	/**
	 * Gets text of hyperlink
	 * @param widget to handle
	 * @return text of specified hyperlink
	 */
	public String getText(final Hyperlink widget){
		return Display.syncExec(new ResultRunnable<String>() {

			@Override
			public String run() {
				return widget.getText();
			}
		});
	}
}
