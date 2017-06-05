/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.jface.preference;

import org.eclipse.swt.custom.CLabel;
import org.hamcrest.Matcher;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.core.condition.WidgetIsFound;
import org.eclipse.reddeer.core.lookup.ShellLookup;
import org.eclipse.reddeer.core.matcher.WithMnemonicTextMatcher;
import org.eclipse.reddeer.core.matcher.WithTextMatcher;
import org.eclipse.reddeer.jface.window.AbstractWindow;
import org.eclipse.reddeer.swt.api.Button;
import org.eclipse.reddeer.swt.api.Shell;
import org.eclipse.reddeer.swt.api.TreeItem;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.impl.button.CancelButton;
import org.eclipse.reddeer.swt.impl.button.OkButton;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.clabel.DefaultCLabel;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.swt.impl.tree.DefaultTreeItem;

/**
 * Preference dialog implementation. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class PreferenceDialog extends AbstractWindow{
	
	public PreferenceDialog(String text) {
		super(text);
	}

	public PreferenceDialog(Shell shell) {
		super(shell);
	}


	public PreferenceDialog(Matcher<?>...matchers) {
		super(matchers);
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
		
		new WaitUntil(new WidgetIsFound(CLabel.class, new WithTextMatcher(path[path.length-1])), TimePeriod.SHORT, false);
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
		org.eclipse.swt.widgets.Shell parentShell = ShellLookup.getInstance().getParentShell(getShell().getSWTWidget());
		
		WidgetIsFound applyAndCloseButton = new WidgetIsFound(org.eclipse.swt.widgets.Button.class,
				new WithMnemonicTextMatcher("Apply and Close"));
		
		
		Button button;
		if(applyAndCloseButton.test()){
			button = new PushButton("Apply and Close"); //oxygen changed button text
		} else {
			button = new OkButton();	
		}
		button.click();
		new WaitWhile(new ShellIsAvailable(getShell())); 
		new DefaultShell(parentShell);
	}
	
	/**
	 * Presses Cancel button on Property Dialog. 
	 */
	public void cancel() {
		org.eclipse.swt.widgets.Shell parentShell = ShellLookup.getInstance().getParentShell(getShell().getSWTWidget());
		
		CancelButton cancel = new CancelButton();
		cancel.click();
		new WaitWhile(new ShellIsAvailable(getShell())); 
		new DefaultShell(parentShell);
	}

	@Override
	public Class<?> getEclipseClass() {
		return org.eclipse.jface.preference.PreferenceDialog.class;
	}
}
