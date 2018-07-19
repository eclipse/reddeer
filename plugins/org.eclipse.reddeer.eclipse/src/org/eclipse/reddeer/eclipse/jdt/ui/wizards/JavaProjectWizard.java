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
package org.eclipse.reddeer.eclipse.jdt.ui.wizards;

import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.eclipse.selectionwizard.NewMenuWizard;
import org.eclipse.reddeer.jface.condition.WindowIsAvailable;
import org.eclipse.reddeer.swt.api.Button;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.impl.button.FinishButton;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.workbench.core.condition.JobIsRunning;

/**
 * Wizard dialog for creating new Java project.
 */
public class JavaProjectWizard extends NewMenuWizard {

	/**
	 * Constructs the wizard with Java &gt; Java Project.
	 */
	public JavaProjectWizard() {
		super("New Java Project", "Java", "Java Project");
	}
	
	@Override
	public void finish(TimePeriod timeout) {
		finish(timeout, false, "");
	}
	
	public void finish(boolean createModule, String moduleName) {
		finish(TimePeriod.LONG, createModule, moduleName);
	}
	
	/**
	 * Click the finish button in wizard dialog and deal with module-info.java file dialog
	 * @param timeout
	 * @param createModule
	 * @param moduleName
	 */
	public void finish(TimePeriod timeout, boolean createModule, String moduleName) {
		checkShell();
		log.info("Finish wizard");

		Button button = new FinishButton(this);
		button.click();

		ShellIsAvailable moduleShell = new ShellIsAvailable("New module-info.java");
		new WaitUntil(moduleShell, TimePeriod.MEDIUM, false);
		if (moduleShell.getResult() != null) {
			NewModuleInfoDialog moduleDialog = new NewModuleInfoDialog(new DefaultShell(moduleShell.getResult()));
			if (createModule) {
				if (moduleName != null && !moduleName.isEmpty()) {
					moduleDialog.setModuleName(moduleName);
				}
				moduleDialog.create();
			} else {
				moduleDialog.dontCreate();
			}
			new WaitWhile(moduleShell, TimePeriod.DEFAULT);
		}
		
		new WaitWhile(new WindowIsAvailable(this), timeout);
		new WaitWhile(new JobIsRunning(), timeout);
	}
	
}
