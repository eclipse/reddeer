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
package org.eclipse.reddeer.eclipse.test.wst.jsdt.ui.wizards;

import static org.junit.Assert.assertTrue;

import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.core.exception.CoreLayerException;
import org.eclipse.reddeer.eclipse.ui.navigator.resources.ProjectExplorer;
import org.eclipse.reddeer.eclipse.wst.jsdt.ui.wizards.JavaProjectWizard;
import org.eclipse.reddeer.eclipse.wst.jsdt.ui.wizards.JavaProjectWizardFirstPage;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.requirements.cleanworkspace.CleanWorkspaceRequirement.CleanWorkspace;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
@CleanWorkspace
public class JavaProjectWizardFirstPageTest {

	public static String PROJECT_NAME = "testProject";

	@Test
	public void setName() {
		JavaProjectWizard dialog = new JavaProjectWizard();
		try {
			dialog.open();
		} catch (CoreLayerException exc) {
			if (!exc.getMessage().contains("matches \"JavaScript Project\"")) {
				throw exc;
			} else {
				// java script library is missing in this eclipse distribution, but that's not a failure
				return;
			}
		}
		JavaProjectWizardFirstPage dialogPage = new JavaProjectWizardFirstPage(dialog);
		dialogPage.setName(PROJECT_NAME);
		dialog.finish();
		assertTrue("Project '" + PROJECT_NAME + "'not found", new ProjectExplorer().containsProject(PROJECT_NAME));
		
	}
	
	@After
	public void closeAll() {
		ShellIsAvailable shell = new ShellIsAvailable("New");
		new WaitUntil(shell, TimePeriod.SHORT, false);
		if (shell.getResult() != null) {
			new DefaultShell().close();
		}
	}

}
