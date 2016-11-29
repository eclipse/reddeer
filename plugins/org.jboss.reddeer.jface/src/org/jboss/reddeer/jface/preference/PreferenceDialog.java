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
package org.jboss.reddeer.jface.preference;

import org.eclipse.swt.widgets.Shell;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.core.condition.ShellWithTextIsAvailable;
import org.jboss.reddeer.core.handler.WidgetHandler;
import org.jboss.reddeer.core.lookup.ShellLookup;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.condition.CLabelWithTextIsAvailable;
import org.jboss.reddeer.swt.impl.button.CancelButton;
import org.jboss.reddeer.swt.impl.button.OkButton;
import org.jboss.reddeer.swt.impl.clabel.DefaultCLabel;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;

/**
 * Preference dialog implementation. 
 * 
 * @author Lucia Jelinkova
 *
 */
public abstract class PreferenceDialog {

	private static final Logger log = Logger.getLogger(PreferenceDialog.class);
	
	/**
	 * Returns the title of the dialog.
	 *
	 * @return the title
	 */
	public abstract String getTitle();
	
	/**
	 * Opens the dialog (e.g. by menu)
	 */
	protected abstract void openImpl();
	
	/**
	 * Opens the dialog. Contains checks if the dialog is open, 
	 * opening of the dialog (implementation by subclasses) and
	 * activating the dialog's shell. 
	 */
	public void open() {
		log.info("Open Preferences dialog");

		if (isOpen()){
			log.debug("Preferences dialog was already opened.");
		} else{
			openImpl();
		}
		
		new DefaultShell(getTitle());
	}
	
	/**
	 * Selects the specified preference page <var>page</var>.
	 * @param page preference page to be opened
	 */
	public void select(PreferencePage page) {
		if (page == null) {
			throw new IllegalArgumentException("page can't be null");
		}
		select(page.getPath());
	}

	/**
	 * Selects preference page with the specified <var>path</var>.
	 * @param path path in preference shell tree to specific preference page
	 */
	public void select(String... path) {
		if (path == null) {
			throw new IllegalArgumentException("path can't be null");
		}
		if (path.length == 0) {
			throw new IllegalArgumentException("path can't be empty");
		}
		TreeItem t = new DefaultTreeItem(path);
		t.select();
		
		new WaitUntil(new CLabelWithTextIsAvailable(path[path.length-1]), TimePeriod.SHORT, false);
	}
	
	/**
	 * Get name of the current preference page.
	 * 
	 * @return name of preference page
	 */
	public String getPageName() {
		DefaultCLabel cl = new DefaultCLabel();
		return cl.getText();
	}
	
	/**
	 * Presses Ok button on Property Dialog. 
	 */
	public void ok() {
		final String parentShellText = WidgetHandler.getInstance().getText(
				ShellLookup.getInstance().getParentShell(new DefaultShell(getTitle()).getSWTWidget()));
		
		OkButton ok = new OkButton();
		ok.click();
		new WaitWhile(new ShellWithTextIsAvailable(getTitle())); 
		new DefaultShell(parentShellText);
	}
	
	/**
	 * Presses Cancel button on Property Dialog. 
	 */
	public void cancel() {
		final String parentShellText = WidgetHandler.getInstance().getText(
				ShellLookup.getInstance().getParentShell(new DefaultShell(getTitle()).getSWTWidget()));
		
		CancelButton cancel = new CancelButton();
		cancel.click();
		new WaitWhile(new ShellWithTextIsAvailable(getTitle())); 
		new DefaultShell(parentShellText);
	}
	
	/**
	 * Checks if the specific preference dialog is open.
	 * @return true if the dialog is open, false otherwise
	 */
	public boolean isOpen() {
		Shell shell = ShellLookup.getInstance().getShell(getTitle(),TimePeriod.SHORT);
		return (shell != null);		
	}
}
