/*******************************************************************************
 * Copyright (c) 2017, 2018 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.workbench.test.editor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.List;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.reddeer.common.exception.RedDeerException;
import org.eclipse.reddeer.common.exception.TestFailureException;
import org.eclipse.reddeer.common.platform.RunningPlatform;
import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.common.wait.AbstractWait;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.eclipse.core.resources.ProjectItem;
import org.eclipse.reddeer.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.eclipse.reddeer.eclipse.ui.navigator.resources.ProjectExplorer;
import org.eclipse.reddeer.eclipse.ui.wizards.datatransfer.ExternalProjectImportWizardDialog;
import org.eclipse.reddeer.eclipse.ui.wizards.datatransfer.WizardProjectsImportPage;
import org.eclipse.reddeer.eclipse.ui.wizards.newresource.BasicNewFileResourceWizard;
import org.eclipse.reddeer.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;
import org.eclipse.reddeer.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizardFirstPage;
import org.eclipse.reddeer.jface.text.contentassist.ContentAssistant;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.requirements.cleanworkspace.CleanWorkspaceRequirement;
import org.eclipse.reddeer.requirements.cleanworkspace.CleanWorkspaceRequirement.CleanWorkspace;
import org.eclipse.reddeer.swt.api.Menu;
import org.eclipse.reddeer.swt.api.MenuItem;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.keyboard.KeyboardFactory;
import org.eclipse.reddeer.workbench.exception.WorkbenchLayerException;
import org.eclipse.reddeer.workbench.handler.EditorHandler;
import org.eclipse.reddeer.workbench.handler.WorkbenchShellHandler;
import org.eclipse.reddeer.workbench.impl.editor.AbstractEditor.ContentAssistantEnum;
import org.eclipse.reddeer.workbench.impl.editor.Marker;
import org.eclipse.reddeer.workbench.impl.editor.TextEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.texteditor.ITextEditor;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ComparisonFailure;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
@CleanWorkspace
public class TextEditorTest {

	private static String ORIGINAL_JAVA_FILE_TEXT = "";
	private static final String JAVA_CLASS_FILE_NAME = "JavaClass.java";
	private static final String JAVA_CLASS_REGEX_FILE_NAME = "JavaClass1+.java";

	@BeforeClass
	public static void setup() {
		Listener l = new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				System.out.println(event);
				
			}
		};
		Display.getDisplay().syncExec(new Runnable() {
			
			@Override
			public void run() {
				Display.getDisplay().addFilter(SWT.MouseDown, l);
				
			}
		});
		
		EditorHandler.getInstance().closeAll(true);
		BasicNewProjectResourceWizard projectWizard = new BasicNewProjectResourceWizard();
		projectWizard.open();
		new BasicNewProjectResourceWizardFirstPage(projectWizard).setProjectName("testProject");
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
		javaTextEditor.setCursorPosition(javaTextEditor.getLineOfText("public class"), 0);
		ContentAssistant assistant = javaTextEditor.openContentAssistant(ContentAssistantEnum.JAVA);
		assertFalse(assistant.getProposals().isEmpty());
		assistant.close();
	}

	@Test(expected=TestFailureException.class)
	public void notTextEditorTest() {
		BasicNewFileResourceWizard newFileDialog = new BasicNewFileResourceWizard();
		newFileDialog.open();
		WizardNewFileCreationPage page = new WizardNewFileCreationPage(newFileDialog);
		page.setFileName("editorTest.min");
		page.setFolderPath("testProject");
		newFileDialog.finish();
		try {
			new TextEditor();
		} catch (WorkbenchLayerException ex) {
			throw new TestFailureException(ex.getMessage());
		}
	}

	@Test
	public void getTextTest() throws AWTException {
		BasicNewFileResourceWizard newFileDialog = new BasicNewFileResourceWizard();
		newFileDialog.open();
		WizardNewFileCreationPage page = new WizardNewFileCreationPage(newFileDialog);
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
		int line = textEditor.getLineOfText(firstLine);
		textEditor.setCursorPosition(line, firstLine.length());
		KeyboardFactory.getKeyboard().type(firstLineAppend);
		textEditor.save();
		String text = textEditor.getTextAtLine(line).toLowerCase();

		assertTrue("Editor doesn't contain expected text at expected position\n" + text,
				text.startsWith(firstLine + firstLineAppend));
	}
	
	@Test
	public void getCursorPosition() {
		final String firstLine = "package test;";
		TextEditor textEditor = TextEditorTest.openJavaFile();
		textEditor.activate();
		int line = textEditor.getLineOfText(firstLine);
		textEditor.setCursorPosition(line, firstLine.length());
		Point position = textEditor.getCursorPosition();
		assertTrue("expected "+line+" but was "+position.x,line == position.x);
		assertTrue("expected "+firstLine.length()+" but was "+position.y, 
				firstLine.length() == position.y);
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
		String text = textEditor.getTextAtLine(textEditor.getLineOfText(firstLine)).toLowerCase();
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
			new WaitUntil(new ShellIsAvailable(""), TimePeriod.NONE);
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
			new WaitUntil(new ShellIsAvailable(""), TimePeriod.NONE);
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
			new WaitUntil(new ShellIsAvailable(""), TimePeriod.NONE);
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

	@Test(expected=TestFailureException.class)
	public void getTextAtLineTest() {
		TextEditor textEditor = TextEditorTest.openJavaFile();
		collapseTextInJavaFile();
		try {
			assertEquals("\t\tSystem.out.println(\"\");", textEditor.getTextAtLine(14));
		} catch (ComparisonFailure ex) {
			throw new TestFailureException(ex.getMessage());
		}
	}

	@Test(expected=TestFailureException.class)
	public void getLineOfText() {
		TextEditor textEditor = TextEditorTest.openJavaFile();
		try {
			assertEquals(11, textEditor.getLineOfText("JavaClass"));
		} catch (AssertionError ex) {
			throw new TestFailureException(ex.getMessage());
		}
		assertEquals(13, textEditor.getLineOfText("public JavaClass"));
		assertEquals(-1, textEditor.getLineOfText("Some text not present in editor"));
	}

	@Test(expected=TestFailureException.class)
	public void getLineOfTextIndex() {
		TextEditor textEditor = TextEditorTest.openJavaFile();
		try {
			assertEquals(11, textEditor.getLineOfText("JavaClass", 0));
		} catch (AssertionError ex) {
			throw new TestFailureException(ex.getMessage());
		}
		assertEquals(13, textEditor.getLineOfText("JavaClass", 1));
		assertEquals(-1, textEditor.getLineOfText("JavaClass", 2));
		assertEquals(-1, textEditor.getLineOfText("Some text not present in editor", 0));
	}

	@Test(expected=TestFailureException.class)
	public void getMarkers() {
		TextEditor textEditor = TextEditorTest.openJavaFile();
		textEditor.setText(textEditor.getText().replace("System", "SystemX"));
		// in case if the editor has been changed by the test getAYTMarkers()
		textEditor.setText(textEditor.getText().replace("SystemY", "SystemX"));
		textEditor.save();
		AbstractWait.sleep(TimePeriod.SHORT);
		List<Marker> markers = textEditor.getMarkers(); 
		try {
			assertEquals(2, markers.size());
		} catch (AssertionError ex) {
			throw new TestFailureException(ex.getMessage());
		}
		assertEquals("SystemX cannot be resolved", markers.get(0).getText());
		try {
			assertEquals(15, markers.get(0).getLineNumber());
		} catch (AssertionError ex) {
			throw new TestFailureException(ex.getMessage());
		}
	}

	@Test(expected=TestFailureException.class)
	public void getAYTMarkers() {
		TextEditor textEditor = TextEditorTest.openJavaFile();
		// in case if the editor has been changed by the test getMarkers()
		textEditor.setText(textEditor.getText().replace("SystemX", "System"));
		textEditor.save();
		AbstractWait.sleep(TimePeriod.SHORT);
		textEditor.setText(textEditor.getText().replace("System", "SystemY"));
		// Wait and do not save the editor
		AbstractWait.sleep(TimePeriod.SHORT);
		List<Marker> markers = textEditor.getAYTMarkers();
		assertEquals(1, markers.size());
		assertEquals("SystemY cannot be resolved", markers.get(0).getText());
		try {
			assertEquals(15, markers.get(0).getLineNumber());
		} catch (AssertionError ex) {
			throw new TestFailureException(ex.getMessage());
		}
	}

	@Test(expected=TestFailureException.class)
	public void getFoldedTextTest() {
		TextEditor textEditor = TextEditorTest.openJavaFile();
		collapseTextInJavaFile();
		try {
			assertEquals(623, textEditor.getText().replaceAll("\r", "").length());
		} catch (AssertionError ex) {
			throw new TestFailureException(ex.getMessage());
		}
	}

	@Test(expected=TestFailureException.class)
	public void insertTextTest() {
		TextEditor textEditor = TextEditorTest.openJavaFile();
		textEditor.insertText(14, 22, "test");
		try {
			assertEquals("\t\tSystem.out.println(\"test\");", new TextEditor().getTextAtLine(14));
		} catch (ComparisonFailure ex) {
			throw new TestFailureException(ex.getMessage());
		}
	}

	@Test(expected=TestFailureException.class)
	public void insertLineTest() {
		TextEditor textEditor = TextEditorTest.openJavaFile();
		textEditor.insertLine(5, "\t\ttestLine;");
		// dirty hack for windows carriage return
		try {
			assertEquals(635, textEditor.getText().replaceAll("\r", "").length());
		} catch (AssertionError ex) {
			throw new TestFailureException(ex.getMessage());
		}
		assertEquals("\t\ttestLine;", new TextEditor().getTextAtLine(5));
		assertEquals("\t\tSystem.out.println(\"\");", new TextEditor().getTextAtLine(15));
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

	@Test(expected=TestFailureException.class)
	public void getSelectedTextTest() {
		TextEditor textEditor = TextEditorTest.openJavaFile();
		textEditor.setCursorPosition(textEditor.getLineOfText("package test;"), 0);
		KeyboardFactory.getKeyboard().select(15, false);
		try {
			assertEquals("package test;\np", textEditor.getSelectedText().replaceAll("\r", ""));
		} catch (AssertionError ex) {
			throw new TestFailureException(ex.getMessage());
		}
	}

	@Test(expected=TestFailureException.class)
	public void selectLineTest() {
		TextEditor textEditor = TextEditorTest.openJavaFile();
		collapseTextInJavaFile();
		textEditor.selectLine(17);
		try {
			assertEquals("}\n", textEditor.getSelectedText().replaceAll("\r", ""));
		} catch (AssertionError ex) {
			throw new TestFailureException(ex.getMessage());
		}
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
	
	@Test
	public void getContextMenuTest() {
		TextEditor textEditor = TextEditorTest.openJavaFile();
		Menu contextMenu = textEditor.getContextMenu();
		MenuItem preferences = contextMenu.getItem("Preferences...");
		assertNotNull(preferences);
	}
	
	@Test
	public void openFileWithRegexInName () {
		ProjectExplorer pe = new ProjectExplorer();
		pe.open();
		ProjectItem javaFile = pe.getProject("TextEditorTestProject").getProjectItem("src", "test",
				TextEditorTest.JAVA_CLASS_REGEX_FILE_NAME);
		javaFile.open();
		new TextEditor(TextEditorTest.JAVA_CLASS_REGEX_FILE_NAME);
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
			resourceURL = FileLocator.toFileURL(Platform.getBundle("org.eclipse.reddeer.workbench.test").getEntry("/"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		ExternalProjectImportWizardDialog epw = new ExternalProjectImportWizardDialog();
		epw.open();
		new WizardProjectsImportPage(epw).setRootDirectory(resourceURL.getFile() + "resources/TextEditorTestProject/");
		new WizardProjectsImportPage(epw).copyProjectsIntoWorkspace(true);
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
