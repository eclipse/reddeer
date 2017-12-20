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
package org.eclipse.reddeer.eclipse.m2e.core.ui.internal.dialogs;

import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.jface.dialogs.TitleAreaDialog;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.impl.button.CancelButton;
import org.eclipse.reddeer.swt.impl.button.CheckBox;
import org.eclipse.reddeer.swt.impl.button.OkButton;
import org.eclipse.reddeer.workbench.core.condition.JobIsRunning;

/**
 * Represends "Update Maven Project" dialog. Can operate OK and Cancel buttons and "Clean projects" checkbox.
 * 
 * @author lvalach
 *
 */
public class UpdateMavenProjectsDialog extends TitleAreaDialog {

	public static final String SHELL_TITLE = "Update Maven Project";
	public static final String CLEAN_PROJECTS = "Clean projects";

	public UpdateMavenProjectsDialog() {
		super(SHELL_TITLE);
	}

	/**
	 * Click "OK" button.
	 */
	public void ok() {
		ok(TimePeriod.LONG);
	}
	
	/**
	 * Click "OK" button and wait while jobs are running for defined time period.
	 */
	public void ok(TimePeriod waitForJobs) {
		new OkButton(this).click();
		new WaitWhile(new ShellIsAvailable(SHELL_TITLE));
		new WaitWhile(new JobIsRunning(), waitForJobs, false);
	}

	/**
	 * Click "Cancel" button.
	 */
	public void cancel() {
		new CancelButton(this).click();
		new WaitWhile(new ShellIsAvailable(SHELL_TITLE));
	}

	/**
	 * Sets {@value #CLEAN_PROJECTS} checkbox to state "checked".
	 *
	 * @param checked
	 *            whether check or not
	 */
	public void clean(Boolean checked) {
		new CheckBox(this, CLEAN_PROJECTS).toggle(checked);
	}

	/**
	 * Returns true when Check Box {@value #CLEAN_PROJECTS} is checked.
	 *
	 * @return true, if is checked
	 */
	public boolean isCleanChecked() {
		return new CheckBox(this, CLEAN_PROJECTS).isChecked();
	}
}