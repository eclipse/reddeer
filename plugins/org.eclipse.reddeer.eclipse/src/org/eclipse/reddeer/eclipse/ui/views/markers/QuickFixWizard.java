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
package org.eclipse.reddeer.eclipse.ui.views.markers;

import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.swt.condition.ShellHasChildrenOrIsNotAvailable;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;

/**
 * Represents Quick Fix dialog
 * 
 *
 */
public class QuickFixWizard extends DefaultShell {

	public static final String TITLE = "Quick Fix";
	
	/**
	 * Constructs the view with {@value #TITLE}.
	 * Opens QuickFix dialog and set focus on it.
	 */
	public QuickFixWizard() {
		super(TITLE);
	}
	
	/**
	 * Press Cancel button.
	 */
	public void cancel() {
		new PushButton("Cancel").click();
		new WaitWhile(new ShellIsAvailable(this));
	}
	
	/**
	 * Press Finish button.
	 */
	public void finish() {
		new PushButton("Finish").click();
		new WaitUntil(new ShellHasChildrenOrIsNotAvailable(this));
	}
	
}
