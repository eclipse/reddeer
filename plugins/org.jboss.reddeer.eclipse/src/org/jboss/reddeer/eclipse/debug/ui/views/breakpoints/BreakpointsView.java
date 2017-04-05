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
package org.jboss.reddeer.eclipse.debug.ui.views.breakpoints;

import java.util.List;

import org.jboss.reddeer.common.wait.AbstractWait;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.condition.ControlIsEnabled;
import org.jboss.reddeer.swt.condition.ShellIsAvailable;
import org.jboss.reddeer.swt.impl.button.OkButton;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ContextMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.text.DefaultText;
import org.jboss.reddeer.swt.impl.text.LabeledText;
import org.jboss.reddeer.swt.impl.toolbar.DefaultToolItem;
import org.jboss.reddeer.swt.impl.tree.DefaultTree;
import org.jboss.reddeer.workbench.impl.view.WorkbenchView;

/**
 * Breakpoints view
 * 
 * @author Andrej Podhradsky
 * @author Tomas Sedmik
 *
 */
public class BreakpointsView extends WorkbenchView {

	/**
	 * Instantiates a new breakpoints view.
	 */
	public BreakpointsView() {
		super("Breakpoints");
	}

	/**
	 * Adds a given java exception breakpoint.
	 *
	 * @param exception            java exception
	 */
	public void addJavaExceptionBreakpoint(String exception) {
		log.info("Adding java exception breakpoint '" + exception + "'");
		open();
		new DefaultToolItem(cTabItem.getFolder(), "Add Java Exception Breakpoint").click();
		new DefaultShell("Add Java Exception Breakpoint");
		new DefaultText().setText(exception);
		new WaitUntil(new ControlIsEnabled(new OkButton()), TimePeriod.LONG);
		new OkButton().click();
		new WaitWhile(new ShellIsAvailable("Add Java Exception Breakpoint"));
	};
	
	/**
	 * Returns whether a breakpoint is available in Breakpoints View.
	 *
	 * @param label            label or some label's substring of the breakpoint
	 * @return true if a breakpoint is present, false otherwise
	 */
	public boolean isBreakpointAvailable(String label) {
		open();
		return getBreakpoint(label) == null ? false : true;
	}

	/**
	 * Gets a particular breakpoint in Breakpoints view.
	 *
	 * @param label            Label or some label's substring of the breakpoint
	 * @return breakpoint - there is some breakpoint with given label, null -
	 *         otherwise
	 */
	public Breakpoint getBreakpoint(String label) {
		log.info("Accessing breakpoints in Breakpoints view");
		open();
		AbstractWait.sleep(TimePeriod.SHORT);
		List<TreeItem> items = new DefaultTree(cTabItem).getItems();
		for (TreeItem item : items) {
			log.debug("\tfound: " + item.getText());
			if (item.getText().contains(label)) {
				return new Breakpoint(item);
			}
		}
		return null;
	}

	/**
	 * Removes all breakpoints.
	 */
	public void removeAllBreakpoints() {
		log.info("Removing all breakpoints from Breakpoints view");
		open();
		if (new DefaultToolItem(cTabItem.getFolder(), "Remove All Breakpoints").isEnabled()) {
			new DefaultToolItem(cTabItem.getFolder(), "Remove All Breakpoints").click();
			new DefaultShell("Remove All Breakpoints").setFocus();
			new PushButton("Yes").click();
		}
	}

	/**
	 * Imports breakpoints from a file.
	 *
	 * @param path            path to the file
	 */
	public void importBreakpoints(String path) {
		log.info("Importing breakpoints from '" + path + "'");
		open();
		new DefaultTree(cTabItem).setFocus();
		new ContextMenu("Import Breakpoints...").select();
		new DefaultShell("Import Breakpoints");
		new LabeledText("From file:").setText(path);
		new PushButton("Finish").click();
		new WaitWhile(new ShellIsAvailable("Import Breakpoints"));
	}
}
