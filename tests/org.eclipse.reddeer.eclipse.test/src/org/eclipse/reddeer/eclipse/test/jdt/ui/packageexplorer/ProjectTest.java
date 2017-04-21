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
package org.eclipse.reddeer.eclipse.test.jdt.ui.packageexplorer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.reddeer.eclipse.core.resources.DefaultProject;
import org.eclipse.reddeer.eclipse.jdt.ui.packageview.PackageExplorerPart;
import org.eclipse.reddeer.eclipse.jdt.ui.wizards.JavaProjectWizard;
import org.eclipse.reddeer.eclipse.jdt.ui.wizards.NewJavaProjectWizardPageOne;
import org.eclipse.reddeer.eclipse.ui.perspectives.JavaPerspective;
import org.eclipse.reddeer.eclipse.utils.DeleteUtils;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.requirements.openperspective.OpenPerspectiveRequirement.OpenPerspective;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
@OpenPerspective(JavaPerspective.class)
public class ProjectTest {

	private static final String PROJECT_NAME_0 = "ProjectTestProject0";
	private static final String PROJECT_NAME_1 = "ProjectTestProject1";
	private static final String PROJECT_NAME_2 = "ProjectTestProject2";
	private static final String PROJECT_NAME_3 = "ProjectTestProject3";
	private static PackageExplorerPart packageExplorer;
	private static DefaultProject project0;
	private static DefaultProject project1;
	private static DefaultProject project2;
		
	@BeforeClass
	public static void setUp(){
		JavaProjectWizard dialog = new JavaProjectWizard();
		dialog.open();
		NewJavaProjectWizardPageOne page1 = new NewJavaProjectWizardPageOne(); 
		page1.setProjectName(ProjectTest.PROJECT_NAME_0);
		dialog.finish();
		
		dialog = new JavaProjectWizard();
		dialog.open();
		page1 = new NewJavaProjectWizardPageOne();
		page1.setProjectName(ProjectTest.PROJECT_NAME_1);
		dialog.finish();
		
		dialog = new JavaProjectWizard();
		dialog.open();
		page1 = new NewJavaProjectWizardPageOne();
		page1.setProjectName(ProjectTest.PROJECT_NAME_2);
		dialog.finish();
		
		packageExplorer = new PackageExplorerPart();
		packageExplorer.open();
		project0 = packageExplorer.getProject(ProjectTest.PROJECT_NAME_0);
		project1 = packageExplorer.getProject(ProjectTest.PROJECT_NAME_1);
		project2 = packageExplorer.getProject(ProjectTest.PROJECT_NAME_2);
	}

	@Test
	public void select() {
		project1.select();
		assertTrue("Project " + project1.getName() + " is not selected",
				project1.isSelected());
		assertTrue("Project " + project0.getName() + " is selected",
				!project0.isSelected());
		assertTrue("Project " + project2.getName() + " is selected",
				!project2.isSelected());
	}
	
	@Test
	public void selectProjectItem(){
		project1.getProjectItem("src").select();
		assertTrue(project1.getProjectItem("src").isSelected());
		assertFalse(project1.isSelected());
		project1.select();
		assertTrue(project1.isSelected());
	}
	
	@Test
	public void delete(){
		JavaProjectWizard dialog = new JavaProjectWizard();
		dialog.open();
		NewJavaProjectWizardPageOne page1 = new NewJavaProjectWizardPageOne(); 
		page1.setProjectName(ProjectTest.PROJECT_NAME_3);
		dialog.finish();
		
		packageExplorer.getProject(PROJECT_NAME_3).delete(true);
		assertFalse("Package Explorer contains project " + ProjectTest.PROJECT_NAME_3 +
				" but it should be deleted.",
			packageExplorer.containsProject(ProjectTest.PROJECT_NAME_3));
	}
	
	@AfterClass
	public static void tearDown() {
		packageExplorer.close();
		packageExplorer.open();
		for (DefaultProject p : packageExplorer.getProjects()){
			DeleteUtils.forceProjectDeletion(p, true);
		}
	}
}
