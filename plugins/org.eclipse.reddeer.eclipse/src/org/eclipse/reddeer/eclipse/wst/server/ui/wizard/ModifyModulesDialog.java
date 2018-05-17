/*******************************************************************************
 * Copyright (c) 2017, 2018 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.eclipse.wst.server.ui.wizard;

import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.jface.condition.WindowIsAvailable;
import org.eclipse.reddeer.jface.wizard.WizardDialog;
import org.eclipse.reddeer.swt.condition.ShellHasChildrenOrIsNotAvailable;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.impl.button.FinishButton;
import org.eclipse.reddeer.swt.impl.button.OkButton;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.workbench.core.condition.JobIsRunning;

/**
 * Wizard for adding and removing modules on the server. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class ModifyModulesDialog extends WizardDialog {

	protected final Logger log = Logger.getLogger(this.getClass());
	public static final String DIALOG_TITLE = "Add and Remove...";
	
	public ModifyModulesDialog() {
		super(DIALOG_TITLE);
	}
	
	/**
	 * Click the finish button in wizard dialog.
	 * @param timeout to wait for wizard shell to close.
	 */
	public void finish(TimePeriod timeout) {
		log.info("Finish wizard");
		
		new FinishButton(this).click();
		new WaitUntil(new ShellHasChildrenOrIsNotAvailable(getShell()));
		if(!getShell().isDisposed()){
			ShellIsAvailable serverShell = new ShellIsAvailable("Server");
			new WaitUntil(serverShell, TimePeriod.DEFAULT, false);
			if (serverShell.getResult() != null) {
				new	OkButton(new DefaultShell(serverShell.getResult())).click();
				new WaitWhile(serverShell);
			}
			new WaitWhile(new WindowIsAvailable(this), timeout);
		}
		new WaitWhile(new JobIsRunning(), timeout);
	}
}
