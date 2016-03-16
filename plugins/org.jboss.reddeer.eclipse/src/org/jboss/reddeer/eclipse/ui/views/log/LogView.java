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
package org.jboss.reddeer.eclipse.ui.views.log;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.core.condition.ProgressInformationShellIsActive;
import org.jboss.reddeer.core.condition.ShellWithTextIsAvailable;
import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.impl.button.CheckBox;
import org.jboss.reddeer.swt.impl.button.OkButton;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ContextMenu;
import org.jboss.reddeer.swt.impl.menu.ViewMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.tree.DefaultTree;
import org.jboss.reddeer.workbench.impl.view.WorkbenchView;

/**
 * Represents Error Log view
 * 
 * @author rawagner
 *
 */
public class LogView extends WorkbenchView{

	private final String DELETE_LOG = "Delete Log";
	private final String CLEAR_LOG = "Clear Log Viewer";
	private final String RESTORE_LOG = "Restore Log";
	private final String CONFIRM_DLG = "Confirm Delete";

	
	public static final String OK_SEVERITY="OK";
	public static final String INFORMATION_SEVERITY="Information";
	public static final String WARNING_SEVERITY="Warning";
	public static final String ERROR_SEVERITY="Error";
	
	/**
	 * Constructs the view with "Error Log".
	 */
	public LogView(){
		super("Error Log");
	}
	
	/**
	 * Gets the OK messages.
	 *
	 * @return list of messages with severity OK (according to IStatus)
	 */
	
	public List<LogMessage> getOKMessages() {
		activate();
		setFilter(OK_SEVERITY);
		activate();
		DefaultTree tree = new DefaultTree();
		List<TreeItem> treeItems = tree.getAllItems();
		List<LogMessage> messages = new ArrayList<LogMessage>();
		for(TreeItem item : treeItems){
			messages.add(new LogMessage(item, IStatus.OK));
		}
		return messages;
	}
	
	/**
	 * Gets the info messages.
	 *
	 * @return list of messages with severity INFO (according to IStatus)
	 */
	public List<LogMessage> getInfoMessages() {
		activate();
		setFilter(INFORMATION_SEVERITY);
		activate();
		DefaultTree tree = new DefaultTree();
		List<TreeItem> treeItems = tree.getAllItems();
		List<LogMessage> messages = new ArrayList<LogMessage>();
		for(TreeItem item : treeItems){
			messages.add(new LogMessage(item, IStatus.INFO));
		}
		return messages;
	}
	
	/**
	 * Gets the warning messages.
	 *
	 * @return list of messages with severity WARNING (according to IStatus)
	 */
	public List<LogMessage> getWarningMessages() {
		activate();
		setFilter(WARNING_SEVERITY);
		activate();
		DefaultTree tree = new DefaultTree();
		List<TreeItem> treeItems = tree.getAllItems();
		List<LogMessage> messages = new ArrayList<LogMessage>();
		for(TreeItem item : treeItems){
			messages.add(new LogMessage(item, IStatus.WARNING));
		}
		return messages;
	}
	
	/**
	 * Gets the error messages.
	 *
	 * @return list of messages with severity ERROR (according to IStatus)
	 */
	public List<LogMessage> getErrorMessages() {
		activate();
		setFilter(ERROR_SEVERITY);
		activate();
		DefaultTree tree = new DefaultTree();
		List<TreeItem> treeItems = tree.getAllItems();
		List<LogMessage> messages = new ArrayList<LogMessage>();
		for(TreeItem item : treeItems){
			messages.add(new LogMessage(item, IStatus.ERROR));
		}
		return messages;
	}
	
	/**
	 * Clears Error lLog messages.
	 */
	public void clearLog() {
		activate();
		new DefaultTree().setFocus();
		Menu cm = new ContextMenu(CLEAR_LOG);
		cm.select();	
	}

	/**
	 * Deletes Error log messages.
	 */
	public void deleteLog() {
		activate();
		new DefaultTree().setFocus();
		Menu cm = new ContextMenu(DELETE_LOG);
		if (!cm.isEnabled()) {
			log.debug("Unable to delete log. \"" + DELETE_LOG + "\" menu item is not enabled.");
			return;
		}
		cm.select();
		new DefaultShell(CONFIRM_DLG);
		new OkButton().click();
		new WaitWhile(new ShellWithTextIsAvailable(CONFIRM_DLG));
	}

	/**
	 * Restores Error log messages.
	 */
	public void restoreLog() {
		activate();
		new DefaultTree().setFocus();
		Menu cm = new ContextMenu(RESTORE_LOG);
		cm.select();			
	}
	

	private void setFilter(String severity){
		ViewMenu tmenu = new ViewMenu("Filters...");
		tmenu.select();
		new WaitUntil(new ShellWithTextIsAvailable("Log Filters"));
		new DefaultShell("Log Filters");
		new CheckBox(OK_SEVERITY).toggle(false);
		new CheckBox(INFORMATION_SEVERITY).toggle(false);
		new CheckBox( WARNING_SEVERITY).toggle(false);
		new CheckBox(ERROR_SEVERITY).toggle(false);
		new CheckBox(severity).toggle(true);
		new CheckBox("Limit visible events to:").toggle(false);
		new PushButton("OK").click();
		new WaitWhile(new ShellWithTextIsAvailable("Log Filters"));
		new WaitWhile(new ProgressInformationShellIsActive());
	}
}
