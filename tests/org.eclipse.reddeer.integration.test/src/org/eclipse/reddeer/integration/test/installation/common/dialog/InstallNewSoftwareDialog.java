/*******************************************************************************
 * Copyright (c) 2020 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/

package org.eclipse.reddeer.integration.test.installation.common.dialog;

import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.integration.test.installation.common.condition.DialogTitleIsFound;
import org.eclipse.reddeer.jface.condition.WindowIsAvailable;
import org.eclipse.reddeer.jface.window.Openable;
import org.eclipse.reddeer.jface.wizard.WizardDialog;
import org.eclipse.reddeer.swt.api.Button;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.impl.button.FinishButton;
import org.eclipse.reddeer.workbench.workbenchmenu.WorkbenchMenuOpenable;

public class InstallNewSoftwareDialog extends WizardDialog {
	
	public static final String TITLE = "Install";
	public static final String[] PATH = { "Help", "Install New Software..." };
	
	protected final Logger log = Logger.getLogger(InstallNewSoftwareDialog.class);
	protected WorkbenchMenuOpenable openable;

	public InstallNewSoftwareDialog() {
		super();
		openable = new WorkbenchMenuOpenable(TITLE, PATH);
	}
	
	/**
	 * Open wizard dialog defined by menu path.
	 */
	public void open() {
		super.open();
		new WaitUntil(new ShellIsAvailable(TITLE), TimePeriod.LONG);
	}
	
	@Override
	protected Openable getOpenAction() {
		return openable;
	}
	
	public WizardDialog next() {
		String pageTitle = this.getTitle();
		super.next();
		// extended waiting as there might be a remediation page
		new WaitWhile(new DialogTitleIsFound(this, pageTitle), TimePeriod.getCustom(600));
		return this;
 	}
	
	@Override
	public void finish(TimePeriod timeout) {
		checkShell();
		log.info("Finish wizard - overriden");

		Button button = new FinishButton(this);
		button.click();

		new WaitWhile(new WindowIsAvailable(this), timeout);
	}
}
