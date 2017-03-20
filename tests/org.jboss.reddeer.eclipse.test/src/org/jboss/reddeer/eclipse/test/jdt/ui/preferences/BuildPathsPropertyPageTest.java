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
package org.jboss.reddeer.eclipse.test.jdt.ui.preferences;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.workbench.core.condition.JobIsRunning;
import org.jboss.reddeer.eclipse.core.resources.DefaultProject;
import org.jboss.reddeer.eclipse.core.resources.Project;
import org.jboss.reddeer.eclipse.jdt.ui.packageview.PackageExplorerPart;
import org.jboss.reddeer.eclipse.jdt.ui.preferences.BuildPathsPropertyPage;
import org.jboss.reddeer.eclipse.jdt.ui.wizards.JavaProjectWizard;
import org.jboss.reddeer.eclipse.jdt.ui.wizards.NewJavaProjectWizardPageOne;
import org.jboss.reddeer.eclipse.test.Activator;
import org.jboss.reddeer.eclipse.ui.dialogs.PropertyDialog;
import org.jboss.reddeer.eclipse.ui.navigator.resources.ProjectExplorer;
import org.jboss.reddeer.eclipse.ui.perspectives.JavaPerspective;
import org.jboss.reddeer.eclipse.utils.DeleteUtils;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.requirements.openperspective.OpenPerspectiveRequirement.OpenPerspective;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
@OpenPerspective(JavaPerspective.class)
public class BuildPathsPropertyPageTest {

	private static final String TEST_PROJECT_NAME = "buildpathspropertypagetest";
	private DefaultProject project;
	private PropertyDialog propertiesDialog = null;
	private BuildPathsPropertyPage buildPathsPropertyPage = null;
	
	@BeforeClass
	public static void setupClass(){
		JavaProjectWizard javaProject = new JavaProjectWizard();
		javaProject.open();
		NewJavaProjectWizardPageOne javaWizardPage = new NewJavaProjectWizardPageOne();
		javaWizardPage.setProjectName(TEST_PROJECT_NAME);
		javaProject.finish();
	}
	
	@AfterClass
	public static void teardownClass(){
		ProjectExplorer pe = new ProjectExplorer();
		pe.open();
		for (DefaultProject p : pe.getProjects()){
			DeleteUtils.forceProjectDeletion(p,true);
		}
	}
	
	@Before
	public void setUp(){
		propertiesDialog = getProject().openProperties();
		buildPathsPropertyPage = new BuildPathsPropertyPage();
		propertiesDialog.select(buildPathsPropertyPage);
	}
	
	@After
	public void cleanup(){
		if (propertiesDialog != null && propertiesDialog.isOpen()){
			propertiesDialog.cancel();
		}
	}
	
	@Test
	public void selectTabs() {
		buildPathsPropertyPage.activateLibrariesTab();
		buildPathsPropertyPage.activateSourceTab();
		buildPathsPropertyPage.activateOrderAndExportTab();
		buildPathsPropertyPage.activateProjectsTab();
 	}
	
	@Test
	public void getLibraries() {
		assertTrue("Exactly one library has to be defined on build path",buildPathsPropertyPage.getLibraries().size() == 1);
 	}
	
	@Test
	public void addRemoveVariable() {
		String addedVariableLabel = buildPathsPropertyPage.addVariable("VN", getJarVariableLocation(), true);
		assertTrue("Libraries has to contain item:'" + addedVariableLabel + "'",
			buildPathsPropertyPage.getLibraries().contains(addedVariableLabel));
		propertiesDialog.ok();
		new WaitWhile(new JobIsRunning());
		new PackageExplorerPart().open();
		propertiesDialog = getProject().openProperties();
		propertiesDialog.select(buildPathsPropertyPage);
		buildPathsPropertyPage.removeVariable(addedVariableLabel,true);
		assertFalse("Libraries should not contain item:'" + addedVariableLabel + "'",
				buildPathsPropertyPage.getLibraries().contains(addedVariableLabel));
		propertiesDialog.ok();
 	}
	
	public Project getProject() {
		if (project == null){
			PackageExplorerPart packageExplorer = new PackageExplorerPart();
			packageExplorer.open();
			project = packageExplorer.getProject(TEST_PROJECT_NAME);
		}
		return project;
	}
	
	private String getJarVariableLocation(){
		try {
			File jarFileRoot = new File(FileLocator.resolve(FileLocator.find(
					Platform.getBundle(Activator.PLUGIN_ID), new Path("target"), null)).getFile());
			File[] jarFiles = jarFileRoot.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return name.endsWith(".jar");
				}
			});
			if (jarFiles == null ||jarFiles.length == 0){
				jarFiles = new File(jarFileRoot,"plugins").listFiles(new FilenameFilter() {
					@Override
					public boolean accept(File dir, String name) {
						return name.endsWith(".jar");
					}
				});
			}
			return jarFiles[0].getAbsolutePath();
		} catch (IOException e) {
			return null;
		}
	}
}
