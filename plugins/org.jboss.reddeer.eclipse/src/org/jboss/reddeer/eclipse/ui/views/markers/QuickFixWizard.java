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
package org.jboss.reddeer.eclipse.ui.views.markers;

import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.swt.condition.ShellIsAvailable;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;

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
		new WaitWhile(new ShellIsAvailable(this));
	}
	
}
