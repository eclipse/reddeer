package org.jboss.reddeer.workbench.test.editor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.texteditor.ITextEditor;
import org.jboss.reddeer.common.platform.RunningPlatform;
import org.jboss.reddeer.eclipse.core.resources.Project;
import org.jboss.reddeer.eclipse.jdt.ui.ProjectExplorer;
import org.jboss.reddeer.eclipse.core.resources.ProjectItem;
import org.jboss.reddeer.eclipse.ui.ide.NewFileCreationWizardDialog;
import org.jboss.reddeer.eclipse.ui.ide.NewFileCreationWizardPage;
import org.jboss.reddeer.eclipse.ui.wizards.datatransfer.ExternalProjectImportWizardDialog;
import org.jboss.reddeer.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;
import org.jboss.reddeer.eclipse.utils.DeleteUtils;
import org.jboss.reddeer.jface.text.contentassist.ContentAssistant;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.handler.ShellHandler;
import org.jboss.reddeer.swt.handler.WorkbenchHandler;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.keyboard.KeyboardFactory;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.wait.AbstractWait;
import org.jboss.reddeer.swt.wait.TimePeriod;
import org.jboss.reddeer.workbench.exception.WorkbenchPartNotFound;
import org.jboss.reddeer.workbench.impl.editor.TextEditor;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class TextEditorTest {
	private static String ORIGINAL_JAVA_FILE_TEXT = "";
	private static final String JAVA_CLASS_FILE_NAME = "JavaClass.java";
	@BeforeClass
	public static void setup(){
		TextEditorTest.importTestProject();
		WorkbenchHandler.getInstance().closeAllEditors();
		TextEditor javaTextEditor = TextEditorTest.openJavaFile();
		TextEditorTest.ORIGINAL_JAVA_FILE_TEXT = javaTextEditor.getText();
		javaTextEditor.close();
		BasicNewProjectResourceWizard projectWizard = new BasicNewProjectResourceWizard();
		projectWizard.open();
		projectWizard.getFirstPage().setProjectName("testProject");
		projectWizard.finish();
	}
	
	@AfterClass
	public static void teardownClass(){
		ProjectExplorer pe = new ProjectExplorer();
		for (Project p : pe.getProjects()){
			DeleteUtils.forceProjectDeletion(p,true);
		}
	}

	@After
	public void teardown(){
		// set java class file content to original
		TextEditor javaTextEditor = TextEditorTest.openJavaFile();
		javaTextEditor.setText(TextEditorTest.ORIGINAL_JAVA_FILE_TEXT);
		javaTextEditor.save();
		WorkbenchHandler.getInstance().closeAllEditors();
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
		editor.save();
		assertEquals("testText", editor.getText());
		editor.close();
	}
	
	@Test
	public void setCursorPosition(){
		final String firstLine = "package test;";
		final String firstLineAppend = "//TEST";
		TextEditor textEditor = TextEditorTest.openJavaFile();
		textEditor.activate();
		textEditor.setCursorPosition(0, firstLine.length());
		KeyboardFactory.getKeyboard().type(firstLineAppend);
		String text = textEditor.getText();
		assertTrue("Editor doesn't contain expected text at expected position\n"
						+ text, text.startsWith(firstLine + firstLineAppend));
	}
	
	@Test
	public void contentAssist(){
		TextEditor textEditor = TextEditorTest.openJavaFile();
		textEditor.setCursorPosition(0, 0);
		textEditor.insertLine(0, "");
		textEditor.save();
		ContentAssistant ca = textEditor.openContentAssistant();
		assertTrue(ca.getProposals().contains("enum"));
		ca.chooseProposal("enum");
		assertTrue(textEditor.getText().contains("enum"));
	}
	
	@Test
	public void closeContentAssist(){
		TextEditor textEditor = TextEditorTest.openJavaFile();
		ContentAssistant ca = textEditor.openContentAssistant();
		ca.close();
		
		try {
			new DefaultShell("");
			fail("ContentAssistant wasn't close");
		} catch (SWTLayerException e) {
			// ok, this is expected
		}
	}

	@Test
	public void closeContentAssistUsingCloseAllShells(){
		TextEditor textEditor = TextEditorTest.openJavaFile();
		ContentAssistant ca = textEditor.openContentAssistant();
		ShellHandler.getInstance().closeAllNonWorbenchShells();

		try {
			new DefaultShell("");
			// if content assistant is still available then close it
			ca.close();
			fail("ContentAssistant wasn't close");
		} catch (SWTLayerException e) {
			// ok, this is expected
		}
	}

	public void openOnAssist(){
		TextEditor textEditor = TextEditorTest.openJavaFile();
		textEditor.selectText("println");
		ContentAssistant ca = textEditor.openOpenOnAssistant();
		assertTrue(ca.getProposals().contains("Open Declaration"));
		assertTrue(ca.getProposals().contains("Open Implementation"));
		ca.chooseProposal("Open Declaration");
		new TextEditor("PrintStream.class");
	}
	
	@Test
	public void closeOpenOnAssistUsingCloseAllShells(){
		TextEditor textEditor = TextEditorTest.openJavaFile();
		textEditor.selectText("JavaClass");
		ContentAssistant ca = textEditor.openOpenOnAssistant();
		ShellHandler.getInstance().closeAllNonWorbenchShells();

		try {
			new DefaultShell("");
			// if content assistant is still available then close it
			ca.close();
			fail("OpenOn ContentAssistant wasn't close");
		} catch (SWTLayerException e) {
			// ok, this is expected
		}
	}
	
	@Test
	public void quickFixAssist(){
		TextEditor textEditor = TextEditorTest.openJavaFile();
		textEditor.selectText("JavaClass");
		ContentAssistant ca = textEditor.openQuickFixContentAssistant();
		assertTrue(ca.getProposals().get(0).contains("Rename in file"));
		assertTrue(ca.getProposals().get(1).contains("Rename in workspace"));
		ca.close();
	}

	@Test
	public void getTextAtLineTest(){
		TextEditor textEditor = TextEditorTest.openJavaFile();
		collapseTextInJavaFile();
		assertEquals("\t\tSystem.out.println(\"\");", textEditor.getTextAtLine(4));
	}
	

	@Test
	public void getMarkers(){
		TextEditor textEditor = TextEditorTest.openJavaFile();
		textEditor.setText(textEditor.getText().replace("System", "Systemx"));
		textEditor.save();
		AbstractWait.sleep(TimePeriod.SHORT);
		assertEquals(1,textEditor.getMarkers().size());
		assertEquals("Systemx cannot be resolved", textEditor.getMarkers().get(0).getText());
	}
	
	@Test
	public void getAYTMarkers(){
		TextEditor textEditor = TextEditorTest.openJavaFile();
		textEditor.setText(textEditor.getText().replace("System", "Systemx"));
		//textEditor.save();
		AbstractWait.sleep(TimePeriod.SHORT);
		assertEquals(1,textEditor.getMarkers().size());
		assertEquals("Systemx cannot be resolved",textEditor.getMarkers().get(0).getText());
		assertEquals(4, textEditor.getMarkers().get(0).getLineNumber());
	}

	@Test
	public void getFoldedTextTest(){
		TextEditor textEditor = TextEditorTest.openJavaFile();
		collapseTextInJavaFile();
		assertEquals(94, textEditor.getText().replaceAll("\r", "").length());
	}

	@Test
	public void insertTextTest(){
		TextEditor textEditor = TextEditorTest.openJavaFile();
		textEditor.insertText(4, 22, "test");
		assertEquals("\t\tSystem.out.println(\"test\");", new TextEditor().getTextAtLine(4));
	}

	@Test
	public void insertLineTest(){
		TextEditor textEditor = TextEditorTest.openJavaFile();
		textEditor.insertLine(4, "\t\ttestLine;");
		assertEquals(106, textEditor.getText().replaceAll("\r", "").length()); //dirty hack for windows carriage return
		assertEquals("\t\ttestLine;", new TextEditor().getTextAtLine(4));
		assertEquals("\t\tSystem.out.println(\"\");", new TextEditor().getTextAtLine(5));
	}
	
	@Test
	public void insertTextBehind(){
		TextEditor textEditor = TextEditorTest.openJavaFile();
		int offset = textEditor.getPositionOfText("class");
		textEditor.insertText(offset, "static ");
		assertTrue(textEditor.getText().contains("public static class"));
	}
	
	@Test
	public void insertTextAfter(){
		TextEditor textEditor = TextEditorTest.openJavaFile();
		int offset = textEditor.getPositionOfText("JavaClass");
		textEditor.insertText(offset+"JavaClass".length(), " extends String");
		assertTrue(textEditor.getText().contains("public class JavaClass extends String"));
	}
	
	@Test
	public void testGetPositionOfText(){
		TextEditor textEditor = TextEditorTest.openJavaFile();
		int offset1 = textEditor.getPositionOfText("JavaClass", 0);
		assertTrue(offset1 > 0);
		int offset2 = textEditor.getPositionOfText("JavaClass", 1);
		assertTrue(offset2 > offset1);
		int offset = textEditor.getPositionOfText("JavaClass", 2);
		assertTrue(offset == -1);
	}

	@Test
	public void getSelectedTextTest(){
		TextEditor textEditor = TextEditorTest.openJavaFile();
		KeyboardFactory.getKeyboard().select(15, false);
		assertEquals("package test;\np", textEditor.getSelectedText().replaceAll("\r", ""));
	}

	@Test
	public void selectLineTest(){
		TextEditor textEditor = TextEditorTest.openJavaFile();
		collapseTextInJavaFile();
		textEditor.selectLine(7);
		assertEquals("}\n", textEditor.getSelectedText().replaceAll("\r",""));
	}
	
	@Test
	public void selectTextTest(){
		TextEditor textEditor = TextEditorTest.openJavaFile();
		collapseTextInJavaFile();
		textEditor.selectText("JavaClass");
		assertEquals("JavaClass",textEditor.getSelectedText());
	}
	
	@Test
	public void selectTextTest1(){
		TextEditor textEditor = TextEditorTest.openJavaFile();
		collapseTextInJavaFile();
		textEditor.selectText("JavaClass",1);
		assertEquals("JavaClass",textEditor.getSelectedText());
	}
	
	@Test(expected = SWTLayerException.class)
	public void selectTextTest2(){
		TextEditor textEditor = TextEditorTest.openJavaFile();
		collapseTextInJavaFile();
		textEditor.selectText("JavaClass",2);
	}

	private void collapseTextInJavaFile(){
		moveCursorDown(4);
		if (RunningPlatform.isOSX()){
			KeyboardFactory.getKeyboard().invokeKeyCombination(SWT.COMMAND, SWT.KEYPAD_SUBTRACT);
		}else{
			KeyboardFactory.getKeyboard().invokeKeyCombination(SWT.CONTROL, SWT.KEYPAD_SUBTRACT);
		}
	}

	private static TextEditor openJavaFile(){
		ProjectItem javaFile = new ProjectExplorer().getProject("TextEditorTestProject")
				.getProjectItem("src", "test", TextEditorTest.JAVA_CLASS_FILE_NAME);
		javaFile.open();
		return new TextEditor(TextEditorTest.JAVA_CLASS_FILE_NAME);
	}

	private void moveCursorDown(int count){
		for(int i=0; i<count; i++){
			KeyboardFactory.getKeyboard().type(SWT.ARROW_DOWN);
		}
	}

	private static void importTestProject(){
		URL resourceURL = null; 
		try {
			resourceURL = FileLocator.toFileURL(Platform.getBundle("org.jboss.reddeer.workbench.test").getEntry("/"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ExternalProjectImportWizardDialog epw = new ExternalProjectImportWizardDialog();
		epw.open();
		epw.getFirstPage().setRootDirectory(resourceURL.getFile()+"resources/TextEditorTestProject/");
		epw.getFirstPage().copyProjectsIntoWorkspace(true);
		epw.finish();
	}

	private IEditorPart getEditorPart(TextEditor editor){
		Field editorField = null;
		try {
			editorField = editor.getClass().getSuperclass()
					.getDeclaredField("editorPart");
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
