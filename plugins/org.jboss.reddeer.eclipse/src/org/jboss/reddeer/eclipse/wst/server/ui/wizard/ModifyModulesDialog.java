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
package org.jboss.reddeer.eclipse.wst.server.ui.wizard;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.core.handler.ShellHandler;
import org.jboss.reddeer.jface.wizard.WizardDialog;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.condition.ShellHasChildrenOrIsNotAvailable;
import org.jboss.reddeer.swt.condition.ShellIsAvailable;
import org.jboss.reddeer.swt.impl.button.FinishButton;
import org.jboss.reddeer.swt.impl.button.OkButton;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.workbench.core.condition.JobIsRunning;

/**
 * Wizard for adding and removing modules on the server. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class ModifyModulesDialog extends WizardDialog {

	protected final Logger log = Logger.getLogger(this.getClass());
	public static final String DIALOG_TITLE = "Add and Remove...";
	
	/**
	 * Click the finish button in wizard dialog.
	 * @param timeout to wait for wizard shell to close.
	 */
	public void finish(TimePeriod timeout) {
		log.info("Finish wizard");
		
		Shell s = new DefaultShell(DIALOG_TITLE);
		new FinishButton().click();
		new WaitUntil(new ShellHasChildrenOrIsNotAvailable(s));
		if(!ShellHandler.getInstance().isDisposed(s.getSWTWidget())){
			Shell serverShell = new DefaultShell("Server");
			new OkButton().click();
			new WaitWhile(new ShellIsAvailable(serverShell));
			new WaitWhile(new ShellIsAvailable(s), timeout);
		}
		new WaitWhile(new JobIsRunning(), timeout);
	}
}
