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
package org.jboss.reddeer.spy.view;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Widget;
import org.jboss.reddeer.spy.view.internal.RedDeerWidgetTracker;

/**
 * RedDeerSpy provides active monitoring of widgets and its output. 
 * 
 * @author mlabuda
 * @since 0.8.0
 */
public class RedDeerSpy {

	public static final String SPY_VIEW_HEADER = "To start/stop spying on widgets press CTRL+ALT:\n\n";
	
	private Action action;
	private StyledText spyOutput;
	private Runnable widgetTracker;
	
	private final Composite	parent;
	
	private Widget lastWidget;
	
	/**
	 * Creates a new RedDeerSpy.
	 * 
	 * @param parent parent composite
	 */
	public RedDeerSpy(Composite parent) {
		this.parent = parent;
		widgetTracker = new RedDeerWidgetTracker(this);
		
		createOutputText();
		createAction();
		addTrackerListener();		
	}
	
	/**
	 * Gets styled text for output of tracked widget.
	 * 
	 * @return styled text output for currently tracked widget
	 */
	public StyledText getOutput() {
		return spyOutput;
	}
	
	/**
	 * Gets action monitoring widgets.
	 * 
	 * @return action monitoring widget 
	 */
	public Action getAction() {
		return action;
	}
	
	/**
	 * Sets last tracked widget.
	 * 
	 * @param widget last tracked widget
	 */
	public void setLastWidget(Widget widget) {
		lastWidget = widget;
	}
	
	/**
	 * Gets last tracked widget.
	 * 
	 * @return last tracked widget
	 */
	public Widget getLastWidget() {
		return lastWidget;
	}
	
	/**
	 * Gets widget tracker.
	 * 
	 * @return widget tracker
	 */
	public Runnable getWidgetTracker() {
		return widgetTracker;
	}
	
	private void createAction() {
		action = new Action("Monitor", IAction.AS_CHECK_BOX) {
			
			@Override
			public void run() {
				if (action.isChecked() && !spyOutput.isDisposed()) {
					Display display = spyOutput.getDisplay();
					display.timerExec(70, widgetTracker);
				}
			}
		};

		action.setToolTipText("Enable/Disable monitoring of widgets");
		action.setChecked(false);
	}

	private void createOutputText() {
		spyOutput = new StyledText(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.READ_ONLY);
		spyOutput.setLayoutData(new GridData(GridData.FILL_HORIZONTAL, GridData.FILL_VERTICAL, true, true));
		spyOutput.setText(SPY_VIEW_HEADER);
		if (isMac()){
			spyOutput.setFont(new Font(Display.getCurrent(), "Georgia", 11, SWT.NONE));
		} else {
			spyOutput.setFont(new Font(Display.getCurrent(), "Courier New", 11, SWT.NONE));
		}
	}

	private void addTrackerListener() {
		parent.getDisplay().addFilter(SWT.KeyDown, new Listener() {
			@Override
			public void handleEvent(Event event) {
				if ((event.stateMask == SWT.CTRL) && (event.keyCode == SWT.ALT))
					if (action.isChecked())
						action.setChecked(false);
					else {
						action.setChecked(true);
						action.run();
					}
			}
		});
	}

	private boolean isMac() {
		String swtPlatform = SWT.getPlatform();
		return ("carbon".equals(swtPlatform) || "cocoa".equals(swtPlatform));
	}	
}
