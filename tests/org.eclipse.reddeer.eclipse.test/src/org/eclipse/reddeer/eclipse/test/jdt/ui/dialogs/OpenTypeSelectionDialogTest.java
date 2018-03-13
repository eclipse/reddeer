/*******************************************************************************
 * Copyright (c) 2018 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.eclipse.test.jdt.ui.dialogs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.eclipse.reddeer.common.exception.RedDeerException;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.eclipse.jdt.ui.dialogs.OpenTypeSelectionDialog;
import org.eclipse.reddeer.eclipse.jdt.ui.wizards.JavaProjectWizard;
import org.eclipse.reddeer.eclipse.jdt.ui.wizards.NewClassCreationWizard;
import org.eclipse.reddeer.eclipse.jdt.ui.wizards.NewClassWizardPage;
import org.eclipse.reddeer.eclipse.jdt.ui.wizards.NewJavaProjectWizardPageOne;
import org.eclipse.reddeer.eclipse.ui.navigator.resources.ProjectExplorer;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.swt.api.TableItem;
import org.eclipse.reddeer.swt.condition.ShellIsActive;
import org.eclipse.reddeer.swt.impl.menu.ShellMenuItem;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.workbench.condition.EditorWithTitleIsActive;
import org.eclipse.reddeer.workbench.impl.editor.TextEditor;
import org.eclipse.reddeer.workbench.impl.shell.WorkbenchShell;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class OpenTypeSelectionDialogTest {

	private static final String TEST_PROJECT_NAME = "OpenTypeSelectionDialogTest";
	private static final String TEST_CLASS_NAME = "FindMe";

	@Test
	public void openTest() {
		OpenTypeSelectionDialog dialog = new OpenTypeSelectionDialog();
		dialog.open();

		assertTrue("Shell 'Open Type' is not available",
				new ShellIsActive(OpenTypeSelectionDialog.DIALOG_TITLE).test());

		dialog.cancel();

		assertFalse("Shell 'Open Type' should be closed",
				new ShellIsActive(OpenTypeSelectionDialog.DIALOG_TITLE).test());
	}

	@Test
	public void constructorTakingShellTest() {
		new ShellMenuItem(new WorkbenchShell(), "Navigate", "Open Type...").select();
		DefaultShell shell = new DefaultShell(OpenTypeSelectionDialog.DIALOG_TITLE);
		OpenTypeSelectionDialog dialog = new OpenTypeSelectionDialog(shell);

		assertNotNull(dialog.getShell());

		dialog.cancel();

		assertFalse("Shell 'Open Type' should be closed",
				new ShellIsActive(OpenTypeSelectionDialog.DIALOG_TITLE).test());
	}

	@Test
	public void nonParametericConstructorTest() {
		new ShellMenuItem(new WorkbenchShell(), "Navigate", "Open Type...").select();
		OpenTypeSelectionDialog dialog = new OpenTypeSelectionDialog();

		assertNotNull(dialog.getShell());

		dialog.cancel();

		assertFalse("Shell 'Open Type' should be closed",
				new ShellIsActive(OpenTypeSelectionDialog.DIALOG_TITLE).test());
	}

	@Test
	public void findAndOpenClass() {
		OpenTypeSelectionDialog dialog = new OpenTypeSelectionDialog();
		dialog.open();

		dialog.setFilterText(TEST_CLASS_NAME);
		List<TableItem> items = dialog.getResultsTable().getItems();
		assertEquals("There should be one item in 'Open Type' dialog.", 1, items.size());
		assertTrue("The 'Open Type' dialog should find '" + TEST_CLASS_NAME + "' class.",
				items.get(0).getText().contains(TEST_CLASS_NAME));

		dialog.ok();

		assertFalse("Shell 'Open Type' should be closed",
				new ShellIsActive(OpenTypeSelectionDialog.DIALOG_TITLE).test());
		try {
			new TextEditor(TEST_CLASS_NAME + ".java");
		} catch (RedDeerException e) {
			fail("Editor " + TEST_CLASS_NAME + ".java is unavailable.");
		}
	}

	@BeforeClass
	public static void prepareWorkspace() {
		createJavaProject(TEST_PROJECT_NAME);
		createJavaClass(TEST_CLASS_NAME);
		new WaitUntil(new EditorWithTitleIsActive(TEST_CLASS_NAME + ".java"));
		new TextEditor(TEST_CLASS_NAME + ".java").close();
	}

	@AfterClass
	public static void cleanWorkspace() {
		ProjectExplorer pe = new ProjectExplorer();
		pe.deleteAllProjects();
	}

	private static void createJavaProject(String name) {
		JavaProjectWizard javaProject = new JavaProjectWizard();
		javaProject.open();

		NewJavaProjectWizardPageOne javaWizardPage = new NewJavaProjectWizardPageOne(javaProject);
		javaWizardPage.setProjectName(name);

		javaProject.finish();
	}

	private static void createJavaClass(String name) {
		NewClassCreationWizard javaClassDialog = new NewClassCreationWizard();
		javaClassDialog.open();

		NewClassWizardPage wizardPage = new NewClassWizardPage(javaClassDialog);
		wizardPage.setName(name);
		wizardPage.setPackage("test");

		javaClassDialog.finish();
	}
}
