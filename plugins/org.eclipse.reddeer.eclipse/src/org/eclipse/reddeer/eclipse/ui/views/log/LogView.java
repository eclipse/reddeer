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
package org.eclipse.reddeer.eclipse.ui.views.log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.core.condition.WidgetIsFound;
import org.eclipse.reddeer.core.matcher.WithMnemonicTextMatcher;
import org.eclipse.reddeer.swt.api.Button;
import org.eclipse.reddeer.swt.api.MenuItem;
import org.eclipse.reddeer.swt.api.Shell;
import org.eclipse.reddeer.swt.api.Tree;
import org.eclipse.reddeer.swt.api.TreeItem;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.impl.button.OkButton;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.menu.ContextMenuItem;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.swt.impl.tree.DefaultTree;
import org.eclipse.reddeer.workbench.impl.menu.WorkbenchPartMenuItem;
import org.eclipse.reddeer.workbench.impl.view.WorkbenchView;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.internal.views.log.SharedImages;

/**
 * Represents Error Log view
 * 
 * @author rawagner
 * @author jnovak
 *
 */
public class LogView extends WorkbenchView {

	private final String DELETE_LOG = "Delete Log";
	private final String CLEAR_LOG = "Clear Log Viewer";
	private final String RESTORE_LOG = "Restore Log";
	private final String CONFIRM_DLG = "Confirm Delete";

	/**
	 * Constructs the view with "Error Log".
	 */
	public LogView() {
		super("Error Log");
	}

	/**
	 * Gets the OK messages.
	 *
	 * @return list of messages with severity OK (according to IStatus)
	 */
	public List<LogMessage> getOKMessages() {
		return getMessages(Severity.OK);
	}

	/**
	 * Gets the info messages.
	 *
	 * @return list of messages with severity INFO (according to IStatus)
	 */
	public List<LogMessage> getInfoMessages() {
		return getMessages(Severity.INFO);
	}

	/**
	 * Gets the warning messages.
	 *
	 * @return list of messages with severity WARNING (according to IStatus)
	 */
	public List<LogMessage> getWarningMessages() {
		return getMessages(Severity.WARNING);
	}

	/**
	 * Gets the error messages.
	 *
	 * @return list of messages with severity ERROR (according to IStatus)
	 */
	public List<LogMessage> getErrorMessages() {
		return getMessages(Severity.ERROR);
	}

	/**
	 * Clears Error log messages.
	 */
	public void clearLog() {
		activate();
		getViewTree();
		MenuItem cm = new ContextMenuItem(CLEAR_LOG);
		cm.select();
	}

	/**
	 * Deletes Error log messages.
	 */
	public void deleteLog() {
		activate();
		getViewTree();
		MenuItem cm = new ContextMenuItem(DELETE_LOG);
		if (!cm.isEnabled()) {
			log.debug("Unable to delete log. \"" + DELETE_LOG + "\" menu item is not enabled.");
			return;
		}
		cm.select();
		Shell configDialog = new DefaultShell(CONFIRM_DLG);
		WidgetIsFound openButton = new WidgetIsFound(org.eclipse.swt.widgets.Button.class, 
				new WithMnemonicTextMatcher("Delete All Events"));
		
		
		Button button;
		if(openButton.test()){
			button = new PushButton("Delete All Events"); //oxygen changed button text
		} else {
			button = new OkButton();	
		}
		button.click();
		new WaitWhile(new ShellIsAvailable(configDialog));
	}

	/**
	 * Restores Error log messages.
	 */
	public void restoreLog() {
		activate();
		getViewTree();
		MenuItem cm = new ContextMenuItem(RESTORE_LOG);
		cm.select();
		new WaitUntil(new ShellIsAvailable("Progress Information"), TimePeriod.SHORT,false);
		new WaitWhile(new ShellIsAvailable("Progress Information"));
	}

	/**
	 * Sets 'Activate on new events' option in Error Log View
	 * @param value true - select the option, false - deselect the option
	 */
	public void setActivateOnNewEvents(boolean value) {
		activate();
		WorkbenchPartMenuItem menu = new WorkbenchPartMenuItem("Activate on new events");
		if ((value && !menu.isSelected()) || (!value && menu.isSelected())) {
			menu.select();
		}
	}

	private Tree getViewTree() {
		return new DefaultTree(this);
	}

	private List<LogMessage> getMessages(Severity severity) {
		activate();

		WidgetIsFound isTreeFound =
				new WidgetIsFound(org.eclipse.swt.widgets.Tree.class, cTabItem.getFolder().getSWTWidget());
		new WaitUntil(isTreeFound, TimePeriod.SHORT, false);
		Widget tree = isTreeFound.getResult();

		return tree != null ? getMessagesFromTree(tree, severity) : new ArrayList<>();
	}

	private List<LogMessage> getMessagesFromTree(Widget widget, Severity severity) {
		DefaultTree tree = new DefaultTree((org.eclipse.swt.widgets.Tree) widget);
		ArrayList<LogMessage> messages = new ArrayList<>();

		for (TreeItem item : tree.getItems()) {
			if (severityMatch(item, severity)) {
				messages.add(new LogMessage(item, severity.getIStatus()));
			}
		}
		return messages;
	}

	private boolean severityMatch(TreeItem item, Severity severity) {
		Image itemImage = item.getImage();
		return severity.getImages().contains(itemImage);
	}

	private enum Severity {

		OK(IStatus.OK,
			SharedImages.getImage(SharedImages.DESC_OK_ST_OBJ)),
		INFO(IStatus.INFO,
			SharedImages.getImage(SharedImages.DESC_INFO_ST_OBJ)),
		WARNING(IStatus.WARNING,
			SharedImages.getImage(SharedImages.DESC_WARNING_ST_OBJ)),
		ERROR(IStatus.ERROR,
			SharedImages.getImage(SharedImages.DESC_ERROR_ST_OBJ),
			SharedImages.getImage(SharedImages.DESC_ERROR_STACK_OBJ));

		private List<Image> images;
		private int iStatus;

		Severity(int iStatus, Image... image) {
			this.images = Arrays.asList(image);
			this.iStatus = iStatus;
		}

		public List<Image> getImages() {
			return images;
		}

		public int getIStatus() {
			return iStatus;
		}
	}

}
