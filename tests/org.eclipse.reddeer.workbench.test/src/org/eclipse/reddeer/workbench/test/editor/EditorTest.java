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
package org.eclipse.reddeer.workbench.test.editor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;

import org.eclipse.reddeer.eclipse.jdt.ui.packageview.PackageExplorerPart;
import org.eclipse.reddeer.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.eclipse.reddeer.eclipse.ui.wizards.newresource.BasicNewFileResourceWizard;
import org.eclipse.reddeer.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;
import org.eclipse.reddeer.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizardFirstPage;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.requirements.cleanworkspace.CleanWorkspaceRequirement;
import org.eclipse.reddeer.requirements.cleanworkspace.CleanWorkspaceRequirement.CleanWorkspace;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.workbench.api.Editor;
import org.eclipse.reddeer.workbench.core.exception.WorkbenchCoreLayerException;
import org.eclipse.reddeer.workbench.exception.WorkbenchLayerException;
import org.eclipse.reddeer.workbench.handler.EditorHandler;
import org.eclipse.reddeer.workbench.impl.editor.DefaultEditor;
import org.eclipse.reddeer.workbench.test.Activator;
import org.eclipse.reddeer.workbench.test.ui.editor.SimpleEditor;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
@CleanWorkspace
public class EditorTest {

	private static final String PROJECT_NAME = "testProject";

	@BeforeClass
	public static void setupClass() {
		BasicNewProjectResourceWizard projectWizard = new BasicNewProjectResourceWizard();
		projectWizard.open();
		new BasicNewProjectResourceWizardFirstPage(projectWizard).setProjectName(PROJECT_NAME);
		projectWizard.finish();
		BasicNewFileResourceWizard newFileDialog = new BasicNewFileResourceWizard();
		newFileDialog.open();
		WizardNewFileCreationPage page = new WizardNewFileCreationPage(newFileDialog);
		page.setFileName("editorTest.min");
		page.setFolderPath(PROJECT_NAME);
		newFileDialog.finish();
		new DefaultEditor().close(false);
		newFileDialog = new BasicNewFileResourceWizard();
		newFileDialog.open();
		page = new WizardNewFileCreationPage(newFileDialog);
		page.setFileName("editorTest1.min");
		page.setFolderPath(PROJECT_NAME);
		newFileDialog.finish();
		new DefaultEditor().close(false);
	}

	@Before
	public void setup() {
		PackageExplorerPart packageExplorer = new PackageExplorerPart();
		packageExplorer.open();
		packageExplorer.getProject(PROJECT_NAME).getProjectItem("editorTest.min").open();
	}

	@After
	public void teardown() {
		EditorHandler.getInstance().closeAll(true);
	}

	@AfterClass
	public static void teardownClass() {
		new CleanWorkspaceRequirement().fulfill();
	}

	@Test(expected = WorkbenchCoreLayerException.class)
	public void noEditorsOpenedTest() {
		EditorHandler.getInstance().closeAll(false);
		new DefaultEditor();
	}

	@Test(expected = WorkbenchCoreLayerException.class)
	public void noEditorOpenedTest() {
		new DefaultEditor().close(false);
		new DefaultEditor();
	}
	
	@Test
	public void testTitleImage(){
		assertNotNull(new DefaultEditor().getTitleImage());
		assertEquals(Activator.getDefault().getImageRegistry().get(Activator.REDDEER_ICON),
				new DefaultEditor().getTitleImage());
	}
	
	@Test
	public void testTitleToolTip(){
		assertEquals(SimpleEditor.TITLE_TOOLTIP, new DefaultEditor().getTitleToolTip());
	}

	@Test
	public void closeDirtyWithSaveTest() {
		DefaultEditor editor = new DefaultEditor();
		new PushButton("Make Dirty").click();
		SimpleEditor editorPart = getEditorPart(editor);
		assertTrue(editorPart.dirty);
		editor.close(true);
		assertFalse(editorPart.dirty);
	}

	@Test
	public void closeDirtyEditorsWithSaveTest() {
		DefaultEditor editor = new DefaultEditor();
		new PushButton("Make Dirty").click();
		SimpleEditor editorPart = getEditorPart(editor);
		assertTrue(editorPart.dirty);
		EditorHandler.getInstance().closeAll(true);
		assertFalse(editorPart.dirty);
	}

	@Test
	public void closeDirtyEditorsWithoutSaveTest() {
		DefaultEditor editor = new DefaultEditor();
		new PushButton("Make Dirty").click();
		SimpleEditor editorPart = getEditorPart(editor);
		assertTrue(editorPart.dirty);
		EditorHandler.getInstance().closeAll(false);
		assertTrue(editorPart.dirty);
	}

	@Test
	public void closeDirtyWithoutSave() {
		DefaultEditor editor = new DefaultEditor();
		new PushButton("Make Dirty").click();
		SimpleEditor editorPart = getEditorPart(editor);
		assertTrue(editorPart.dirty);
		editor.close(false);
		assertTrue(editorPart.dirty);
	}

	@Test
	public void closeNotDirty() {
		DefaultEditor editor = new DefaultEditor();
		editor.close(true);

	}

	@Test
	public void getActiveEditorTest() {
		DefaultEditor editor = new DefaultEditor();
		assertNotNull(editor);
	}

	@Test
	public void getTitleTest() {
		DefaultEditor editor = new DefaultEditor();
		assertEquals("Editor does not have expected title", "editorTest.min", editor.getTitle());
	}

	@Test
	public void isDirtyTest() {
		DefaultEditor editor = new DefaultEditor();
		assertFalse(editor.isDirty());
		new PushButton("Make Dirty").click();
		assertTrue(editor.isDirty());
		new PushButton("Save").click();
		assertFalse(editor.isDirty());
	}
	
	@Test
	public void saveEditor(){
		DefaultEditor editor = new DefaultEditor();
		assertFalse(editor.isDirty());
		new PushButton("Make Dirty").click();
		editor.save();
		assertFalse(editor.isDirty());
	}
	
	@Test(expected = WorkbenchLayerException.class)
	public void saveNotDirtyEditor(){
		DefaultEditor editor = new DefaultEditor();
		assertFalse(editor.isDirty());
		editor.save();
	}
	
	@Test
	public void findDirtyEditor(){
		DefaultEditor editor = new DefaultEditor();
		String title = editor.getTitle();
		new PushButton("Make Dirty").click();
		assertTrue(editor.isDirty());

		new DefaultEditor(title);
	}
	
	@Test
	public void testEditorAsReferencedComposite(){
		DefaultEditor editor = new DefaultEditor();
		//editor used as referenced composite
		new PushButton(editor);
	}

	@Test
	public void getEditorByTitleTest() {
		DefaultEditor editor = new DefaultEditor("editorTest.min");
		assertNotNull(editor);
		PackageExplorerPart packageExplorer = new PackageExplorerPart();
		packageExplorer.open();
		packageExplorer.getProject(PROJECT_NAME).getProjectItem("editorTest1.min").open();
		editor = new DefaultEditor("editorTest.min");
		assertNotNull(editor);
		editor = new DefaultEditor("editorTest1.min");
		assertNotNull(editor);
	}

	@Test(expected = WorkbenchCoreLayerException.class)
	public void getEditorByTitleWrongTest() {
		new DefaultEditor("Wrong Name Of Editor");
	}

	@Test
	public void switchEditorTest() {
		PackageExplorerPart packageExplorer = new PackageExplorerPart();
		packageExplorer.open();
		packageExplorer.getProject(PROJECT_NAME).getProjectItem("editorTest1.min").open();
		assertTrue(new DefaultEditor().isActive());
		assertTrue(new DefaultEditor("editorTest.min").isActive()); // should
																	// switch
																	// editors
	}

	@Test(expected = WorkbenchCoreLayerException.class)
	public void closeNotActiveEditorTest() {
		Editor editor = new DefaultEditor();
		PackageExplorerPart packageExplorer = new PackageExplorerPart();
		packageExplorer.open();
		packageExplorer.getProject(PROJECT_NAME).getProjectItem("editorTest1.min").open();
		editor.close(false);
		new DefaultEditor("editorTest.min"); // should be closed now
	}

	@Test
	public void getEditorFileTest() {
		openFile("editorTest.min");
		DefaultEditor editor = new DefaultEditor("editorTest.min");
		assertEquals("/" + PROJECT_NAME + "/editorTest.min", editor.getAssociatedFile().getRelativePath());

		openFile("editorTest1.min");
		DefaultEditor editor1 = new DefaultEditor("editorTest1.min");
		assertEquals("/" + PROJECT_NAME + "/editorTest1.min", editor1.getAssociatedFile().getRelativePath());
	}

	private SimpleEditor getEditorPart(Editor editor) {
		Field editorField = null;
		try {
			editorField = editor.getClass().getSuperclass().getDeclaredField("editorPart");
			editorField.setAccessible(true);
			return (SimpleEditor) editorField.get(editor);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void openFile(String fileName) {
		PackageExplorerPart packageExplorer = new PackageExplorerPart();
		packageExplorer.open();
		packageExplorer.getProject(PROJECT_NAME).getProjectItem(fileName).open();
	}

}