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
package org.eclipse.reddeer.workbench.workbenchmenu;

import org.hamcrest.Matcher;
import org.eclipse.reddeer.core.matcher.WithTextMatcher;
import org.eclipse.reddeer.jface.preference.PreferenceDialog;
import org.eclipse.reddeer.jface.window.Openable;
import org.eclipse.reddeer.swt.api.Shell;

/**
 * Represents all Preference dialogs openable from workbench shell menu
 * @author rawagner
 *
 */
public abstract class WorkbenchMenuPreferencesDialog extends PreferenceDialog {
	
	protected String[] path;
	protected Matcher<String> matcher;
	
	/**
	 * @param text of preference dialog
	 * @param path workbench shell menu item path
	 */
	public WorkbenchMenuPreferencesDialog(String text, String... path){
		this(new WithTextMatcher(text),path);
	}
	
	/**
	 * Implementations are responsible for making sure given shell is Eclipse PreferenceDialog.
	 * @param shell instance of Eclipse PreferenceDialog
	 */
	public WorkbenchMenuPreferencesDialog(Shell shell){
		super(shell);
	}
	
	/**
	 * @param matcher to match preference dialog
	 * @param path workbench shell menu item path
	 */
	public WorkbenchMenuPreferencesDialog(Matcher<String> matcher, String... path){
		super();
		this.matcher = matcher;
		this.path = path;
		isOpen();
	}
	
	@Override
	public Openable getDefaultOpenAction() {
		return new WorkbenchMenuOpenable(matcher, path);
	}

}
