package org.jboss.reddeer.workbench.test.editor;

import static org.junit.Assert.assertEquals;

import java.awt.AWTException;
import java.lang.reflect.Field;
import java.util.List;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.texteditor.ITextEditor;
import org.jboss.reddeer.eclipse.jdt.ui.ProjectExplorer;
import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.Project;
import org.jboss.reddeer.eclipse.ui.ide.NewFileCreationWizardDialog;
import org.jboss.reddeer.eclipse.ui.ide.NewFileCreationWizardPage;
import org.jboss.reddeer.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;
import org.jboss.reddeer.swt.test.RedDeerTest;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.workbench.editor.TextEditor;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.jboss.reddeer.workbench.exception.WorkbenchPartNotFound;

public class TextEditorTest extends RedDeerTest {

	@BeforeClass
	public static void setup(){
		BasicNewProjectResourceWizard projectWizard = new BasicNewProjectResourceWizard();
		projectWizard.open();
		projectWizard.getFirstPage().setProjectName("testProject");
		projectWizard.finish();
	}
	
	@AfterClass
	public static void teardown(){
		ProjectExplorer pe = new ProjectExplorer();
		List<Project> projects = pe.getProjects();
		for (Project p: projects){
			p.delete(true);
		}
	}
	
	@Test(expected=WorkbenchPartNotFound.class)
	public void notTextEditorTest(){
		
		NewFileCreationWizardDialog newFileDialog = new NewFileCreationWizardDialog();
		newFileDialog.open();
		NewFileCreationWizardPage page = newFileDialog.getFirstPage();
		page.setFileName("editorTest.min");
		page.setFolderPath("testProject");
		newFileDialog.finish();
		new TextEditor();
	}
	
	@Test
	public void getTextTest() throws AWTException{
		NewFileCreationWizardDialog newFileDialog = new NewFileCreationWizardDialog();
		newFileDialog.open();
		NewFileCreationWizardPage page = newFileDialog.getFirstPage();
		page.setFileName("textEditor.txt");
		page.setFolderPath("testProject");
		newFileDialog.finish();
		final TextEditor editor = new TextEditor();
		assertEquals("", editor.getText());
		Display.syncExec(new Runnable() {
			
			@Override
			public void run() {
				ITextEditor textEditor = (ITextEditor)getEditorPart(editor);
				IDocument doc = textEditor.getDocumentProvider().getDocument(textEditor.getEditorInput());
				try {
					doc.replace(0, 0, "testText");
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
			}
		});
		assertEquals("testText", editor.getText());
		editor.save();
	}
	
	
	private IEditorPart getEditorPart(TextEditor editor){
		Field editorField = null;
		try {
			editorField = editor.getClass().getSuperclass().getSuperclass()
					.getDeclaredField("workbenchPart");
			editorField.setAccessible(true);
			return (IEditorPart) editorField.get(editor);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
