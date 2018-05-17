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
package org.eclipse.reddeer.eclipse.test.jdt.ui.packageexplorer;

import org.eclipse.reddeer.eclipse.core.resources.JavaProject;
import org.eclipse.reddeer.eclipse.core.resources.Project;
import org.eclipse.reddeer.eclipse.jdt.ui.packageview.PackageExplorerPart;
import org.eclipse.reddeer.eclipse.jdt.ui.wizards.JavaProjectWizard;
import org.eclipse.reddeer.eclipse.jdt.ui.wizards.NewJavaProjectWizardPageOne;
import org.eclipse.reddeer.eclipse.utils.DeleteUtils;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class JavaProjectTest {

	private static final String PROJECT_NAME_0 = "JavaTestProject0";
	private static PackageExplorerPart packageExplorer;
		
	@BeforeClass
	public static void setUp(){
		JavaProjectWizard dialog = new JavaProjectWizard();
		dialog.open();
		NewJavaProjectWizardPageOne page1 = new NewJavaProjectWizardPageOne(dialog);
		page1.setProjectName(PROJECT_NAME_0);
		dialog.finish();

		packageExplorer = new PackageExplorerPart();
		packageExplorer.open();
	}
	
	@Test
	public void testGetJavaProject() {
		Project project= packageExplorer.getProject(PROJECT_NAME_0);
		project.getAdapter(JavaProject.class);
	}
	
	@AfterClass
	public static void tearDown() {
		packageExplorer.close();
		packageExplorer.open();
		if (packageExplorer.containsProject(PROJECT_NAME_0)) {
			DeleteUtils.forceProjectDeletion(packageExplorer.getProject(PROJECT_NAME_0),
				true);
		}
	}
}
