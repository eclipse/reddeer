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

import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.core.lookup.ShellLookup;
import org.eclipse.reddeer.jface.wizard.WizardDialog;
import org.eclipse.reddeer.swt.condition.NewShellIsOpenedOrIsNotAvailable;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.swt.widgets.Shell;

/**
 * Represents Quick Fix dialog
 * 
 *
 */
public class QuickFixWizard extends WizardDialog {

	public static final String TITLE = "Quick Fix";
	
	/**
	 * Constructs the view with {@value #TITLE}.
	 * Opens QuickFix dialog and set focus on it.
	 */
	public QuickFixWizard() {
		super(TITLE);
	}
	
	/**
	 * Press Finish button.
	 */
	public void finish(TimePeriod timeout) {
		Shell[] shells = ShellLookup.getInstance().getShells();
		new PushButton("Finish").click();
		new WaitUntil(new NewShellIsOpenedOrIsNotAvailable(this.getShell().getSWTWidget(), shells), timeout);
	}
	
}
