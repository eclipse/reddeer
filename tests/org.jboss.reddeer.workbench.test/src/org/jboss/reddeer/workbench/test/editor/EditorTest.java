package org.jboss.reddeer.workbench.test.editor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.util.List;

import org.jboss.reddeer.eclipse.jdt.ui.ProjectExplorer;
import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.PackageExplorer;
import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.Project;
import org.jboss.reddeer.eclipse.ui.ide.NewFileCreationWizardDialog;
import org.jboss.reddeer.eclipse.ui.ide.NewFileCreationWizardPage;
import org.jboss.reddeer.eclipse.ui.problems.ProblemsView;
import org.jboss.reddeer.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.test.RedDeerTest;
import org.jboss.reddeer.workbench.api.Editor;
import org.jboss.reddeer.workbench.exception.WorkbenchPartNotFound;
import org.jboss.reddeer.workbench.impl.editor.DefaultEditor;
import org.jboss.reddeer.workbench.test.ui.editor.SimpleEditor;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class EditorTest extends RedDeerTest {

	@BeforeClass
	public static void setupClass() {
		BasicNewProjectResourceWizard projectWizard = new BasicNewProjectResourceWizard();
		projectWizard.open();
		projectWizard.getFirstPage().setProjectName("testProject");
		projectWizard.finish();
		NewFileCreationWizardDialog newFileDialog = new NewFileCreationWizardDialog();
		newFileDialog.open();
		NewFileCreationWizardPage page = newFileDialog.getFirstPage();
		page.setFileName("editorTest.min");
		page.setFolderPath("testProject");
		newFileDialog.finish();
		new DefaultEditor().close(false);
		newFileDialog = new NewFileCreationWizardDialog();
		newFileDialog.open();
		page = newFileDialog.getFirstPage();
		page.setFileName("editorTest1.min");
		page.setFolderPath("testProject");
		newFileDialog.finish();
		new DefaultEditor().close(false);
	}

	@Before
	public void setup() {
		new PackageExplorer().getProject("testProject")
				.getProjectItem("editorTest.min").open();
	}

	@After
	public void teardown() {
		try {
			new DefaultEditor().close(false);
			new DefaultEditor().close(false);
		} catch (WorkbenchPartNotFound ex) {
			// do nothing. We just want to have clean workspace
		}
	}
	
	@AfterClass
	public static void teardownClass(){
		List<Project> projects = new ProjectExplorer().getProjects();
		for (Project p: projects){
			p.delete(true);
		}
	}

	@Test(expected = WorkbenchPartNotFound.class)
	public void noEditorOpenedTest() {
		new DefaultEditor().close(false);
		new DefaultEditor();
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
		System.out.println(editor.isDirty());
		assertEquals("Editor does not have expected title", "editorTest.min",
				editor.getTitle());
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
	public void getEditorByTitleTest() {
		DefaultEditor editor = new DefaultEditor("editorTest.min");
		assertNotNull(editor);
		new PackageExplorer().getProject("testProject")
				.getProjectItem("editorTest1.min").open();
		editor = new DefaultEditor("editorTest.min");
		assertNotNull(editor);
		editor = new DefaultEditor("editorTest1.min");
		assertNotNull(editor);
	}

	@Test(expected = WorkbenchPartNotFound.class)
	public void getEditorByTitleWrongTest() {
		new DefaultEditor("Wrong Name Of Editor");
	}

	@Test
	public void switchEditorTest() {
		new PackageExplorer().getProject("testProject")
				.getProjectItem("editorTest1.min").open();
		assertTrue(new DefaultEditor().isActive());
		assertTrue(new DefaultEditor("editorTest.min").isActive()); // should
																	// switch
																	// editors
	}

	@Test(expected = WorkbenchPartNotFound.class)
	public void closeNotActiveEditorTest() {
		Editor editor = new DefaultEditor();
		new PackageExplorer().getProject("testProject")
				.getProjectItem("editorTest1.min").open();
		editor.close(false);
		new DefaultEditor("editorTest.min"); // should be closed now
	}
	
	@Test
	public void isNotActiveTest(){
		DefaultEditor editor = new DefaultEditor();
		new ProblemsView().open();
		assertFalse(editor.isActive());
		assertTrue(new DefaultEditor().isActive());
	}

	private SimpleEditor getEditorPart(Editor editor) {
        Field editorField = null;
        try {
                editorField = editor.getClass().getSuperclass()
                                .getDeclaredField("editorPart");
                editorField.setAccessible(true);
                return (SimpleEditor) editorField.get(editor);
        } catch (NoSuchFieldException e) {
                e.printStackTrace();
        } catch (IllegalAccessException e) {
                e.printStackTrace();
        }
        return null;
}
	
}
