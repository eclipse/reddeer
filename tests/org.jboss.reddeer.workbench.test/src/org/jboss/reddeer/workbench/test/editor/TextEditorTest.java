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
package org.jboss.reddeer.workbench.test.editor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
import org.jboss.reddeer.common.exception.RedDeerException;
import org.jboss.reddeer.common.platform.RunningPlatform;
import org.jboss.reddeer.common.util.Display;
import org.jboss.reddeer.common.wait.AbstractWait;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.core.condition.ShellWithTextIsAvailable;
import org.jboss.reddeer.eclipse.core.resources.ProjectItem;
import org.jboss.reddeer.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.jboss.reddeer.eclipse.ui.navigator.resources.ProjectExplorer;
import org.jboss.reddeer.eclipse.ui.wizards.datatransfer.ExternalProjectImportWizardDialog;
import org.jboss.reddeer.eclipse.ui.wizards.datatransfer.WizardProjectsImportPage;
import org.jboss.reddeer.eclipse.ui.wizards.newresource.BasicNewFileResourceWizard;
import org.jboss.reddeer.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;
import org.jboss.reddeer.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizardFirstPage;
import org.jboss.reddeer.jface.text.contentassist.ContentAssistant;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.requirements.cleanworkspace.CleanWorkspaceRequirement;
import org.jboss.reddeer.requirements.cleanworkspace.CleanWorkspaceRequirement.CleanWorkspace;
import org.jboss.reddeer.swt.keyboard.KeyboardFactory;
import org.jboss.reddeer.workbench.exception.WorkbenchLayerException;
import org.jboss.reddeer.workbench.handler.EditorHandler;
import org.jboss.reddeer.workbench.handler.WorkbenchShellHandler;
import org.jboss.reddeer.workbench.impl.editor.AbstractEditor.ContentAssistantEnum;
import org.jboss.reddeer.workbench.impl.editor.TextEditor;
import org.jboss.reddeer.workbench.impl.shell.WorkbenchShell;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
@CleanWorkspace
public class TextEditorTest {

	private static String ORIGINAL_JAVA_FILE_TEXT = "";
	private static final String JAVA_CLASS_FILE_NAME = "JavaClass.java";

	@BeforeClass
	public static void setup() {
		EditorHandler.getInstance().closeAll(true);
		BasicNewProjectResourceWizard projectWizard = new BasicNewProjectResourceWizard();
		projectWizard.open();
		new BasicNewProjectResourceWizardFirstPage().setProjectName("testProject");
		projectWizard.finish();
		TextEditorTest.importTestProject();
		TextEditor javaTextEditor = TextEditorTest.openJavaFile();
		TextEditorTest.ORIGINAL_JAVA_FILE_TEXT = javaTextEditor.getText();
		// this has to be here because of Eclipse incorrect behavior on MS
		// Windows
		javaTextEditor.setCursorPosition(0, 13);
		KeyboardFactory.getKeyboard().type("a");
		javaTextEditor.save();
		javaTextEditor.close();
		new WorkbenchShell().maximize();
		// set java class file content to original
		javaTextEditor = TextEditorTest.openJavaFile();
		javaTextEditor.setText(TextEditorTest.ORIGINAL_JAVA_FILE_TEXT);
		javaTextEditor.save();
		EditorHandler.getInstance().closeAll(true);
	}

	@AfterClass
	public static void teardownClass() {
		new CleanWorkspaceRequirement().fulfill();
	}

	@After
	public void teardown() {
		// set java class file content to original
		TextEditor javaTextEditor = TextEditorTest.openJavaFile();
		javaTextEditor.setText(TextEditorTest.ORIGINAL_JAVA_FILE_TEXT);
		javaTextEditor.save();
		EditorHandler.getInstance().closeAll(true);
	}

	@Test
	public void getAvailableContextAssistantTest() {
		TextEditor javaTextEditor = TextEditorTest.openJavaFile();
		assertFalse(javaTextEditor.getAvailableContentAssistants().isEmpty());
	}

	@Test
	public void openSpecificContextAssistantTest() {
		TextEditor javaTextEditor = TextEditorTest.openJavaFile();
		ContentAssistant assistant = javaTextEditor.openContentAssistant(ContentAssistantEnum.JAVA);
		assertFalse(assistant.getProposals().isEmpty());
		assistant.close();
	}

	@Test(expected = WorkbenchLayerException.class)
	public void notTextEditorTest() {

		BasicNewFileResourceWizard newFileDialog = new BasicNewFileResourceWizard();
		newFileDialog.open();
		WizardNewFileCreationPage page = new WizardNewFileCreationPage();
		page.setFileName("editorTest.min");
		page.setFolderPath("testProject");
		newFileDialog.finish();
		new TextEditor();
	}

	@Test
	public void getTextTest() throws AWTException {
		BasicNewFileResourceWizard newFileDialog = new BasicNewFileResourceWizard();
		newFileDialog.open();
		WizardNewFileCreationPage page = new WizardNewFileCreationPage();
		page.setFileName("textEditor.txt");
		page.setFolderPath("testProject");
		newFileDialog.finish();
		final TextEditor editor = new TextEditor();
		assertEquals("", editor.getText());
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				ITextEditor textEditor = (ITextEditor) getEditorPart(editor);
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
	public void setCursorPosition() {
		final String firstLine = "package test;";
		final String firstLineAppend = "testtext";
		TextEditor textEditor = TextEditorTest.openJavaFile();
		textEditor.activate();
		textEditor.setCursorPosition(0, firstLine.length());
		KeyboardFactory.getKeyboard().type(firstLineAppend);
		textEditor.save();
		String text = textEditor.getText().toLowerCase();

		assertTrue("Editor doesn't contain expected text at expected position\n" + text,
				text.startsWith(firstLine + firstLineAppend));
	}

	@Test
	public void setCursorPositionByOffset() {
		final String firstLine = "package test;";
		final String firstLineAppend = "testtext";
		TextEditor textEditor = TextEditorTest.openJavaFile();
		textEditor.activate();
		textEditor.setCursorPosition(textEditor.getPositionOfText(firstLine) + firstLine.length());
		KeyboardFactory.getKeyboard().type(firstLineAppend);
		textEditor.save();
		String text = textEditor.getText().toLowerCase();
		assertTrue("Editor doesn't contain expected text at expected position\n" + text,
				text.startsWith(firstLine + firstLineAppend));
	}

	@Test
	public void contentAssist() {
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
	public void closeContentAssist() {
		TextEditor textEditor = TextEditorTest.openJavaFile();
		ContentAssistant ca = textEditor.openContentAssistant();
		ca.close();

		try {
			new WaitUntil(new ShellWithTextIsAvailable(""), TimePeriod.NONE);
			fail("ContentAssistant wasn't close");
		} catch (RedDeerException e) {
			// ok, this is expected
		}
	}

	@Test
	public void closeContentAssistUsingCloseAllShells() {
		TextEditor textEditor = TextEditorTest.openJavaFile();
		ContentAssistant ca = textEditor.openContentAssistant();
		WorkbenchShellHandler.getInstance().closeAllNonWorbenchShells();

		try {
			new WaitUntil(new ShellWithTextIsAvailable(""), TimePeriod.NONE);
			// if content assistant is still available then close it
			ca.close();
			fail("ContentAssistant wasn't close");
		} catch (RedDeerException e) {
			// ok, this is expected
		}
	}

	public void openOnAssist() {
		TextEditor textEditor = TextEditorTest.openJavaFile();
		textEditor.selectText("println");
		ContentAssistant ca = textEditor.openOpenOnAssistant();
		assertTrue(ca.getProposals().contains("Open Declaration"));
		assertTrue(ca.getProposals().contains("Open Implementation"));
		ca.chooseProposal("Open Declaration");
		new TextEditor("PrintStream.class");
	}

	@Test
	public void closeOpenOnAssistUsingCloseAllShells() {
		TextEditor textEditor = TextEditorTest.openJavaFile();
		textEditor.selectText("JavaClass");
		ContentAssistant ca = textEditor.openOpenOnAssistant();
		WorkbenchShellHandler.getInstance().closeAllNonWorbenchShells();

		try {
			new WaitUntil(new ShellWithTextIsAvailable(""), TimePeriod.NONE);
			// if content assistant is still available then close it
			ca.close();
			fail("OpenOn ContentAssistant wasn't close");
		} catch (RedDeerException e) {
			// ok, this is expected
		}
	}

	@Test
	public void quickFixAssist() {
		TextEditor textEditor = TextEditorTest.openJavaFile();
		textEditor.selectText("JavaClass");
		ContentAssistant ca = textEditor.openQuickFixContentAssistant();
		assertTrue(ca.getProposals().get(0).contains("Rename in file"));
		assertTrue(ca.getProposals().get(1).contains("Rename in workspace"));
		ca.close();
	}

	@Test
	public void getTextAtLineTest() {
		TextEditor textEditor = TextEditorTest.openJavaFile();
		collapseTextInJavaFile();
		assertEquals("\t\tSystem.out.println(\"\");", textEditor.getTextAtLine(4));
	}

	@Test
	public void getLineOfText() {
		TextEditor textEditor = TextEditorTest.openJavaFile();
		assertEquals(1, textEditor.getLineOfText("JavaClass"));
		assertEquals(3, textEditor.getLineOfText("public JavaClass"));
		assertEquals(-1, textEditor.getLineOfText("Some text not present in editor"));
	}

	@Test
	public void getLineOfTextIndex() {
		TextEditor textEditor = TextEditorTest.openJavaFile();
		assertEquals(1, textEditor.getLineOfText("JavaClass", 0));
		assertEquals(3, textEditor.getLineOfText("JavaClass", 1));
		assertEquals(-1, textEditor.getLineOfText("JavaClass", 2));
		assertEquals(-1, textEditor.getLineOfText("Some text not present in editor", 0));
	}

	@Test
	public void getMarkers() {
		TextEditor textEditor = TextEditorTest.openJavaFile();
		textEditor.setText(textEditor.getText().replace("System", "Systemx"));
		textEditor.save();
		AbstractWait.sleep(TimePeriod.SHORT);
		assertEquals(1, textEditor.getMarkers().size());
		assertEquals("Systemx cannot be resolved", textEditor.getMarkers().get(0).getText());
	}

	@Test
	public void getAYTMarkers() {
		TextEditor textEditor = TextEditorTest.openJavaFile();
		textEditor.setText(textEditor.getText().replace("System", "Systemx"));
		// textEditor.save();
		AbstractWait.sleep(TimePeriod.SHORT);
		assertEquals(1, textEditor.getMarkers().size());
		assertEquals("Systemx cannot be resolved", textEditor.getMarkers().get(0).getText());
		assertEquals(5, textEditor.getMarkers().get(0).getLineNumber());
	}

	@Test
	public void getFoldedTextTest() {
		TextEditor textEditor = TextEditorTest.openJavaFile();
		collapseTextInJavaFile();
		assertEquals(94, textEditor.getText().replaceAll("\r", "").length());
	}

	@Test
	public void insertTextTest() {
		TextEditor textEditor = TextEditorTest.openJavaFile();
		textEditor.insertText(4, 22, "test");
		assertEquals("\t\tSystem.out.println(\"test\");", new TextEditor().getTextAtLine(4));
	}

	@Test
	public void insertLineTest() {
		TextEditor textEditor = TextEditorTest.openJavaFile();
		textEditor.insertLine(4, "\t\ttestLine;");
		// dirty hack for windows carriage return
		assertEquals(106, textEditor.getText().replaceAll("\r", "").length());
		assertEquals("\t\ttestLine;", new TextEditor().getTextAtLine(4));
		assertEquals("\t\tSystem.out.println(\"\");", new TextEditor().getTextAtLine(5));
	}

	@Test
	public void insertLineIntoLastEditorLineTest(){
		TextEditor textEditor = TextEditorTest.openJavaFile();
		int lastIndex = textEditor.getNumberOfLines() - 1;
		String newText = "newText";
		textEditor.insertLine(lastIndex, newText);
		String insertedLine = new TextEditor().getTextAtLine(lastIndex);
		assertEquals(newText, insertedLine);
	}

	@Test
	public void insertTextBehind() {
		TextEditor textEditor = TextEditorTest.openJavaFile();
		int offset = textEditor.getPositionOfText("class");
		textEditor.insertText(offset, "static ");
		assertTrue(textEditor.getText().contains("public static class"));
	}

	@Test
	public void insertTextAfter() {
		TextEditor textEditor = TextEditorTest.openJavaFile();
		int offset = textEditor.getPositionOfText("JavaClass");
		textEditor.insertText(offset + "JavaClass".length(), " extends String");
		assertTrue(textEditor.getText().contains("public class JavaClass extends String"));
	}

	@Test
	public void testGetPositionOfText() {
		TextEditor textEditor = TextEditorTest.openJavaFile();
		int offset1 = textEditor.getPositionOfText("JavaClass", 0);
		assertTrue(offset1 > 0);
		int offset2 = textEditor.getPositionOfText("JavaClass", 1);
		assertTrue(offset2 > offset1);
		int offset = textEditor.getPositionOfText("JavaClass", 2);
		assertTrue(offset == -1);
	}

	@Test
	public void getSelectedTextTest() {
		TextEditor textEditor = TextEditorTest.openJavaFile();
		KeyboardFactory.getKeyboard().select(15, false);
		assertEquals("package test;\np", textEditor.getSelectedText().replaceAll("\r", ""));
	}

	@Test
	public void selectLineTest() {
		TextEditor textEditor = TextEditorTest.openJavaFile();
		collapseTextInJavaFile();
		textEditor.selectLine(7);
		assertEquals("}\n", textEditor.getSelectedText().replaceAll("\r", ""));
	}

	@Test
	public void selectTextTest() {
		TextEditor textEditor = TextEditorTest.openJavaFile();
		collapseTextInJavaFile();
		textEditor.selectText("JavaClass");
		assertEquals("JavaClass", textEditor.getSelectedText());
	}

	@Test
	public void selectTextTest1() {
		TextEditor textEditor = TextEditorTest.openJavaFile();
		collapseTextInJavaFile();
		textEditor.selectText("JavaClass", 1);
		assertEquals("JavaClass", textEditor.getSelectedText());
	}

	@Test(expected = RedDeerException.class)
	public void selectTextTest2() {
		TextEditor textEditor = TextEditorTest.openJavaFile();
		collapseTextInJavaFile();
		textEditor.selectText("JavaClass", 2);
	}

	private void collapseTextInJavaFile() {
		moveCursorDown(4);
		if (RunningPlatform.isOSX()) {
			KeyboardFactory.getKeyboard().invokeKeyCombination(SWT.COMMAND, SWT.KEYPAD_SUBTRACT);
		} else {
			KeyboardFactory.getKeyboard().invokeKeyCombination(SWT.CONTROL, SWT.KEYPAD_SUBTRACT);
		}
	}

	private static TextEditor openJavaFile() {
		ProjectExplorer pe = new ProjectExplorer();
		pe.open();
		ProjectItem javaFile = pe.getProject("TextEditorTestProject").getProjectItem("src", "test",
				TextEditorTest.JAVA_CLASS_FILE_NAME);
		javaFile.open();
		return new TextEditor(TextEditorTest.JAVA_CLASS_FILE_NAME);
	}

	private void moveCursorDown(int count) {
		for (int i = 0; i < count; i++) {
			KeyboardFactory.getKeyboard().type(SWT.ARROW_DOWN);
		}
	}

	private static void importTestProject() {
		URL resourceURL = null;
		try {
			resourceURL = FileLocator.toFileURL(Platform.getBundle("org.jboss.reddeer.workbench.test").getEntry("/"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		ExternalProjectImportWizardDialog epw = new ExternalProjectImportWizardDialog();
		epw.open();
		new WizardProjectsImportPage().setRootDirectory(resourceURL.getFile() + "resources/TextEditorTestProject/");
		new WizardProjectsImportPage().copyProjectsIntoWorkspace(true);
		epw.finish();
	}

	private IEditorPart getEditorPart(TextEditor editor) {
		Field editorField = null;
		try {
			editorField = editor.getClass().getSuperclass().getDeclaredField("editorPart");
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
