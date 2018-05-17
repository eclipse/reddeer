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
package org.eclipse.reddeer.eclipse.test.ui.views;

import static org.junit.Assert.assertTrue;

import org.eclipse.reddeer.eclipse.core.resources.Project;
import org.eclipse.reddeer.eclipse.core.resources.ProjectItem;
import org.eclipse.reddeer.eclipse.jdt.ui.wizards.JavaProjectWizard;
import org.eclipse.reddeer.eclipse.jdt.ui.wizards.NewClassCreationWizard;
import org.eclipse.reddeer.eclipse.jdt.ui.wizards.NewClassWizardPage;
import org.eclipse.reddeer.eclipse.jdt.ui.wizards.NewJavaProjectWizardPageOne;
import org.eclipse.reddeer.eclipse.ui.views.navigator.ResourceNavigator;
import org.eclipse.reddeer.eclipse.utils.DeleteUtils;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.workbench.core.lookup.WorkbenchPartLookup;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Testing ResourceNavigator
 * 
 * @author rrabara
 */
@RunWith(RedDeerSuite.class)
public class ResourceNavigatorTest {
	
	private static final String PROJECT_NAME = "ResourceNavigatorTestProject";
	private static final String SOURCE_FOLDER = "src";
	private static final String PACKAGE_NAME = "org.eclipse.reddeer.eclipse.jdt.navigator";
	private static final String CLASS_NAME = "Test";
	
	private ResourceNavigator navigator;
	
	@Before
	public void setUp() {
		navigator = new ResourceNavigator();
	}
	
	@Test
	public void open() {
		
		navigator.open();
		String activeViewTitle = WorkbenchPartLookup.getInstance().getActiveWorkbenchPartTitle();
		assertTrue("Active view have to be Navigator but is"
				+activeViewTitle, activeViewTitle.equals("Navigator"));
	}
	
	/**
	 * This test case is verifying that package
	 * is represented as hierarchy of directories.
	 */
	@Test
	public void navigation() {
		createProject(PROJECT_NAME);
		createClass(PROJECT_NAME, PACKAGE_NAME, CLASS_NAME);
		
		Project project = navigator.getProject(PROJECT_NAME);
		ProjectItem item = project.getProjectItem(SOURCE_FOLDER);
		for(String name : PACKAGE_NAME.split("[.]")) {
			item = item.getProjectItem(name);
		}
		item = item.getProjectItem(CLASS_NAME+".java");
	}

	/**
	 * Create class in specified project and package
	 * 
	 * @param projectName
	 * @param packageName
	 * @param className
	 */
	private void createClass(String projectName, String packageName, String className) {
		NewClassCreationWizard classDialog = new NewClassCreationWizard();
		classDialog.open();
		
		NewClassWizardPage page = new NewClassWizardPage(classDialog);
		page.setName(className);
		page.setPackage(packageName);
		page.setSourceFolder(projectName+"/"+SOURCE_FOLDER);
		
		classDialog.finish();
	}

	/**
	 * Create project with given name
	 * @param projectName
	 */
	private void createProject(String projectName) {
		JavaProjectWizard dialog = new JavaProjectWizard();
		dialog.open();
		new NewJavaProjectWizardPageOne(dialog).setProjectName(projectName);
		dialog.finish();
	}
	
	@After
	public void tearDown() {
		if (navigator != null){
			if(navigator.containsProject(PROJECT_NAME)) {
				DeleteUtils.forceProjectDeletion(navigator.getProject(PROJECT_NAME),true);
			}
		}
	}
}
