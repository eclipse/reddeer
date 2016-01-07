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
package org.jboss.reddeer.eclipse.test.wst.jsdt.ui.wizards;

import static org.junit.Assert.assertTrue;

import org.jboss.reddeer.eclipse.jdt.ui.ProjectExplorer;
import org.jboss.reddeer.eclipse.wst.jsdt.ui.wizards.JavaProjectWizardDialog;
import org.jboss.reddeer.eclipse.wst.jsdt.ui.wizards.JavaProjectWizardFirstPage;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.requirements.cleanworkspace.CleanWorkspaceRequirement.CleanWorkspace;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
@CleanWorkspace
public class JavaProjectWizardFirstPageTest {

	public static String PROJECT_NAME = "testProject";

	@Test
	public void setName() {
		JavaProjectWizardDialog dialog = new JavaProjectWizardDialog();
		dialog.open();
		JavaProjectWizardFirstPage dialogPage = new JavaProjectWizardFirstPage();
		dialogPage.setName(PROJECT_NAME);
		dialog.finish();
		assertTrue("Project '" + PROJECT_NAME + "'not found", new ProjectExplorer().containsProject(PROJECT_NAME));
	}

}
