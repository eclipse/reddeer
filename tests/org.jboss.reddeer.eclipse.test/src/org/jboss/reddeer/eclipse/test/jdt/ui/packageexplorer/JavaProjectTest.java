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
package org.jboss.reddeer.eclipse.test.jdt.ui.packageexplorer;

import org.jboss.reddeer.eclipse.core.resources.JavaProject;
import org.jboss.reddeer.eclipse.core.resources.Project;
import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.PackageExplorer;
import org.jboss.reddeer.eclipse.jdt.ui.wizards.JavaProjectWizard;
import org.jboss.reddeer.eclipse.jdt.ui.wizards.NewJavaProjectWizardPageOne;
import org.jboss.reddeer.eclipse.utils.DeleteUtils;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class JavaProjectTest {

	private static final String PROJECT_NAME_0 = "JavaTestProject0";
	private static PackageExplorer packageExplorer;
		
	@BeforeClass
	public static void setUp(){
		JavaProjectWizard dialog = new JavaProjectWizard();
		dialog.open();
		NewJavaProjectWizardPageOne page1 = new NewJavaProjectWizardPageOne();
		page1.setProjectName(PROJECT_NAME_0);
		dialog.finish();

		packageExplorer = new PackageExplorer();
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
