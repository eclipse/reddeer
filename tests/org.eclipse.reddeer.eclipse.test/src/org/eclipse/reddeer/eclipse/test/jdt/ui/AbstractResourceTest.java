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
package org.eclipse.reddeer.eclipse.test.jdt.ui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileWriter;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.eclipse.core.resources.DefaultProject;
import org.eclipse.reddeer.eclipse.core.resources.ProjectItem;
import org.eclipse.reddeer.eclipse.jdt.ui.wizards.JavaProjectWizard;
import org.eclipse.reddeer.eclipse.jdt.ui.wizards.NewClassCreationWizard;
import org.eclipse.reddeer.eclipse.jdt.ui.wizards.NewClassWizardPage;
import org.eclipse.reddeer.eclipse.jdt.ui.wizards.NewJavaProjectWizardPageOne;
import org.eclipse.reddeer.eclipse.ui.console.ConsoleView;
import org.eclipse.reddeer.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.eclipse.reddeer.eclipse.ui.navigator.resources.AbstractExplorer;
import org.eclipse.reddeer.eclipse.ui.perspectives.JavaPerspective;
import org.eclipse.reddeer.eclipse.ui.wizards.newresource.BasicNewFileResourceWizard;
import org.eclipse.reddeer.eclipse.utils.DeleteUtils;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.requirements.openperspective.OpenPerspectiveRequirement.OpenPerspective;
import org.eclipse.reddeer.workbench.core.condition.JobIsRunning;
import org.eclipse.reddeer.workbench.handler.EditorHandler;
import org.eclipse.reddeer.workbench.impl.editor.DefaultEditor;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
@OpenPerspective(JavaPerspective.class)
public abstract class AbstractResourceTest {

	public static final String PROJECT_NAME = "ProjectItemTestProject";
	public static final String PROJECT_ITEM_TEXT = "src";
	public static final String DEFAULT_PACKAGE_TEXT = "(default package)";
	protected static final String JAVA_CLASS_NAME = "TestClass";
	protected static final String JAVA_CLASS_NAME_1 = "TestClass1";
	protected static final String JAVA_CLASS_FILE_NAME = "TestClass.java";
	protected static AbstractExplorer explorer;
	protected ProjectItem projectItem;

	public AbstractResourceTest(AbstractExplorer explorer) {
		AbstractResourceTest.explorer=explorer;
	}

	@BeforeClass
	public static void importProject() {
		JavaProjectWizard dialog = new JavaProjectWizard();
		dialog.open();
		NewJavaProjectWizardPageOne page1 = new NewJavaProjectWizardPageOne(dialog);
		page1.setProjectName(PROJECT_NAME);
		dialog.finish();		

		createJavaClass(JAVA_CLASS_NAME);
		createJavaClass(JAVA_CLASS_NAME_1);
	}

	@Before
	public void setUp() {
		explorer.open();
		projectItem = explorer.getProject(PROJECT_NAME).getProjectItem(PROJECT_ITEM_TEXT);

		// to test if the explorer items are properly activated and selected
	}

	@After
	public void tearDown() {
		explorer.close();
	}

	@AfterClass
	public static void deleteProject() {
		explorer.open();
		DeleteUtils.forceProjectDeletion(explorer.getProject(PROJECT_NAME),true);
		explorer = null;
	}

	@Test
	public void select() {
		projectItem.select();
		projectItem.getTreeItem().collapse();
		assertTrue("Project item " + PROJECT_ITEM_TEXT + " is not selected" , projectItem.isSelected());
	}

	protected void open(String... projectItemPath) {
		explorer.getProject(PROJECT_NAME).getProjectItem(PROJECT_ITEM_TEXT).select();

		EditorHandler.getInstance().closeAll(true);
		explorer.getProject(PROJECT_NAME).getProjectItem(projectItemPath).open();
		assertTrue("Active Editor has to have title " + JAVA_CLASS_FILE_NAME,
				new DefaultEditor().getTitle().equals(JAVA_CLASS_FILE_NAME));
	}

	protected void selectNonVisibleItem(String... projectItemPath) {
		explorer.activate();
		DefaultProject project = explorer.getProject(PROJECT_NAME);
		project.getProjectItem(PROJECT_ITEM_TEXT).select();

		EditorHandler.getInstance().closeAll(true);
		explorer.activate();

		ProjectItem projectItem = project.getProjectItem(projectItemPath);
		project.collapse();
		project.select();
		projectItem.select();

		assertTrue("Project item is not selected.", projectItem.isSelected());
	}

	@Test
	public void asyncDelete() throws Exception {
		projectItem.select();
		// Create new text file test.txt
		BasicNewFileResourceWizard wizard = new BasicNewFileResourceWizard();
		wizard.open();
		WizardNewFileCreationPage filePage = new WizardNewFileCreationPage(wizard);
		filePage.setFileName("text.txt");
		filePage.setFolderPath(PROJECT_NAME, PROJECT_ITEM_TEXT, "files");
		wizard.finish();
		
		new DefaultEditor("text.txt").close();
		// Edit the file outside the Eclipse IDE
		String rootPath = ResourcesPlugin.getWorkspace().getRoot().getLocationURI().getPath();
		File file = new File(rootPath + "/" + PROJECT_NAME + "/src/text.txt");
		FileWriter out = new FileWriter(file);
		out.write("Hello World");
		out.flush();
		out.close();
		// Delete the file
		ProjectItem folder = explorer.getProject(PROJECT_NAME).getProjectItem(PROJECT_ITEM_TEXT, "files");
		folder.delete();
		assertFalse("Project " + PROJECT_NAME + " contains project item " + PROJECT_ITEM_TEXT +
				" but it should be deleted.",
				explorer.getProject(PROJECT_NAME).getProjectItem(PROJECT_ITEM_TEXT).containsResource("files"));
	}
	
	@Test
	public void testExpand() {
		projectItem.collapse();
		assertFalse(projectItem.isExpanded());
		assertFalse(projectItem.getTreeItem().isExpanded());
		
		projectItem.expand();
		assertTrue(projectItem.isExpanded());
		assertTrue(projectItem.getTreeItem().isExpanded());
	}

	protected static void createJavaClass(final String javaClassName) {
		NewClassCreationWizard newJavaClassDialog = new NewClassCreationWizard();
		newJavaClassDialog.open();

		NewClassWizardPage wizardPage = new NewClassWizardPage(newJavaClassDialog);
		wizardPage.setName(javaClassName);
		newJavaClassDialog.finish();

		new WaitWhile(new JobIsRunning(), TimePeriod.LONG);
	}
}
