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
package org.jboss.reddeer.eclipse.test.jdt.ui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

import org.eclipse.core.resources.ResourcesPlugin;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.workbench.core.condition.JobIsRunning;
import org.jboss.reddeer.eclipse.core.resources.Project;
import org.jboss.reddeer.eclipse.core.resources.ProjectItem;
import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.eclipse.jdt.ui.AbstractExplorer;
import org.jboss.reddeer.eclipse.jdt.ui.NewJavaClassWizardDialog;
import org.jboss.reddeer.eclipse.jdt.ui.NewJavaClassWizardPage;
import org.jboss.reddeer.eclipse.jdt.ui.ide.NewJavaProjectWizardDialog;
import org.jboss.reddeer.eclipse.jdt.ui.ide.NewJavaProjectWizardPage;
import org.jboss.reddeer.eclipse.ui.console.ConsoleView;
import org.jboss.reddeer.eclipse.ui.ide.NewFileCreationWizardDialog;
import org.jboss.reddeer.eclipse.ui.ide.NewFileCreationWizardPage;
import org.jboss.reddeer.eclipse.ui.perspectives.JavaPerspective;
import org.jboss.reddeer.eclipse.utils.DeleteUtils;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.requirements.openperspective.OpenPerspectiveRequirement.OpenPerspective;
import org.jboss.reddeer.workbench.handler.EditorHandler;
import org.jboss.reddeer.workbench.impl.editor.DefaultEditor;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
@OpenPerspective(JavaPerspective.class)
public abstract class AbstractExplorerItemTest {

	public static final String PROJECT_NAME = "ProjectItemTestProject";
	public static final String PROJECT_ITEM_TEXT = "src";
	public static final String DEFAULT_PACKAGE_TEXT = "(default package)";
	protected static final String JAVA_CLASS_NAME = "TestClass";
	protected static final String JAVA_CLASS_NAME_1 = "TestClass1";
	protected static final String JAVA_CLASS_FILE_NAME = "TestClass.java";
	protected static AbstractExplorer explorer;
	protected ProjectItem projectItem;

	public AbstractExplorerItemTest(AbstractExplorer explorer) {
		AbstractExplorerItemTest.explorer=explorer;
	}

	@BeforeClass
	public static void importProject() {
		NewJavaProjectWizardDialog dialog = new NewJavaProjectWizardDialog();
		dialog.open();
		NewJavaProjectWizardPage page1 = new NewJavaProjectWizardPage();
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
		new ConsoleView().open();
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
		Project project = explorer.getProject(PROJECT_NAME);
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
		new NewFileCreationWizard().createFile("files", "text.txt");
		new DefaultEditor("text.txt").close();
		// Edit the file outside the Eclipse IDE
		String rootPath = ResourcesPlugin.getWorkspace().getRoot().getLocationURI().getPath();
		File file = new File(rootPath + "/" + PROJECT_NAME + "/src/text.txt");
		FileWriter out = new FileWriter(file);
		out.write("Hello World");
		out.flush();
		out.close();
		// Delete the file
		ProjectItem folder = explorer.getProject(PROJECT_NAME).getProjectItem(PROJECT_ITEM_TEXT).getChild("files");
		folder.delete();
		assertFalse("Project " + PROJECT_NAME + " contains project item " + PROJECT_ITEM_TEXT +
				" but it should be deleted.",
				explorer.getProject(PROJECT_NAME).getChild(PROJECT_ITEM_TEXT).containsItem("files"));
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

	protected void getChild(String... pathToItem) {
		explorer.getProject(PROJECT_NAME).getProjectItem(PROJECT_ITEM_TEXT).select();

		try {
			explorer.getProject(PROJECT_NAME).getProjectItem(pathToItem)
			.getChild(JAVA_CLASS_FILE_NAME);
		} catch (EclipseLayerException ex) {
			fail("Child item " + JAVA_CLASS_FILE_NAME + " has not been found.");
		}
	}

	protected void getChildren(String... pathToItem) {
		explorer.getProject(PROJECT_NAME).getProjectItem(PROJECT_ITEM_TEXT).select();

		List<ProjectItem> srcChildren = explorer.getProject(PROJECT_NAME).getProjectItem(pathToItem)
				.getChildren();

		assertTrue("There have to be 2 items presented, but number of items is " + srcChildren.size() + ".",
				srcChildren.size() == 2);
	}

	protected static void createJavaClass(final String javaClassName) {
		NewJavaClassWizardDialog newJavaClassDialog = new NewJavaClassWizardDialog();
		newJavaClassDialog.open();

		NewJavaClassWizardPage wizardPage = new NewJavaClassWizardPage();
		wizardPage.setName(javaClassName);
		newJavaClassDialog.finish();

		new WaitWhile(new JobIsRunning(), TimePeriod.LONG);
	}

	private class NewFileCreationWizard extends NewFileCreationWizardPage {

		public void createFile(String folder, String fileName) {
			NewFileCreationWizardDialog wizard = new NewFileCreationWizardDialog();
			wizard.open();
			setFileName(fileName);
			setFolderPath(PROJECT_NAME, PROJECT_ITEM_TEXT, folder);
			wizard.finish();
		}
	}
}
