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

package org.eclipse.reddeer.ui.test.wizard;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.reddeer.eclipse.jdt.ui.wizards.JavaProjectWizard;
import org.eclipse.reddeer.eclipse.jdt.ui.wizards.NewClassCreationWizard;
import org.eclipse.reddeer.eclipse.jdt.ui.wizards.NewClassWizardPage;
import org.eclipse.reddeer.eclipse.jdt.ui.wizards.NewJavaProjectWizardPageOne;
import org.eclipse.reddeer.swt.api.Tree;
import org.eclipse.reddeer.swt.api.TreeItem;
import org.eclipse.reddeer.swt.impl.button.FinishButton;
import org.eclipse.reddeer.swt.impl.button.NextButton;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.swt.impl.text.LabeledText;
import org.eclipse.reddeer.swt.impl.tree.DefaultTree;
import org.eclipse.reddeer.ui.test.wizard.impl.RedDeerTestCaseWizard;
import org.eclipse.reddeer.ui.test.wizard.impl.RedDeerTestCaseWizardPageOne;
import org.eclipse.reddeer.ui.test.wizard.impl.RedDeerTestCaseWizardPageTwo;
import org.eclipse.reddeer.workbench.impl.editor.TextEditor;
import org.junit.BeforeClass;
import org.junit.Test;

public class RedDeerTestCaseWizardTest extends RedDeerWizardTestCase {

	private static final String PROJECT_NAME = "testproject";
	private static final String CLASS_NAME = "TestClass";
	private static final String TEST_CLASS_NAME = CLASS_NAME + "Test";
	private static final String PACKAGE = "org.example.test";

	private List<String> methodNames = new ArrayList<>();

	@BeforeClass
	public static void setup() {
		wizard = new RedDeerTestCaseWizard();
		projectName = PROJECT_NAME;
		createTestProject();
	}

	@Test
	public void testOpen() {
		assertEquals("New " + RedDeerTestCaseWizard.NAME + " Case", new DefaultShell().getText());

		// assert that the selected class was filled in automatically
		assertEquals(PROJECT_NAME + "/src", new LabeledText("Source folder:").getText());
		assertEquals(PACKAGE, new LabeledText("Package:").getText());
		assertEquals(TEST_CLASS_NAME, new LabeledText("Name:").getText());
		assertEquals("java.lang.Object", new LabeledText("Superclass:").getText());
		assertEquals(PACKAGE + "." + CLASS_NAME, new LabeledText("Class under test:").getText());

		assertTrue("Next Button is not enabled", new NextButton().isEnabled());
		assertTrue("Finish Button is not enabled", new FinishButton().isEnabled());

		wizard.next();

		assertTrue("Method selection is empty", new DefaultTree().getAllItems().size() > 0);
	}

	@Test
	public void testCreate() {
		fillInWizard();
		wizard.finish();
		parseMethodNames();
		
		// check the class name
		TextEditor editor = new TextEditor();
		assertEquals(TEST_CLASS_NAME + ".java", editor.getTitle());

		// check the methods
		assertTrue("Method 'setUp' not generated properly", methodGeneratedProperly(editor, "setUp", "Before", false));
		assertTrue("Method 'tearDown' not generated properly",
				methodGeneratedProperly(editor, "tearDown", "After", false));
		assertTrue("Method 'setUpBeforeClass' not generated properly",
				methodGeneratedProperly(editor, "setUpBeforeClass", "BeforeClass", true));
		assertTrue("Method 'tearDownAfterClass' not generated properly",
				methodGeneratedProperly(editor, "tearDownAfterClass", "AfterClass", true));

		for (String methodName : methodNames) {
			assertTrue("Method '" + methodName + "' not generated properly",
					methodGeneratedProperly(editor, methodName, "Test", false));
		}
	}

	private boolean methodGeneratedProperly(TextEditor editor, String methodName, String annotation, boolean isStatic) {
		String staticModifier = isStatic ? " static " : " ";
		String signature = "public" + staticModifier + "void " + methodName + "()";

		for (int i = 0; i < editor.getNumberOfLines(); i++) {
			String line = editor.getTextAtLine(i);

			if (line.contains(signature)) {
				if (!editor.getTextAtLine(i - 1).contains("@" + annotation)) {
					return false;
				}

				String comment = "";
				for (int j = 4; j > 1; j--) {
					comment += editor.getTextAtLine(i - j);
				}
				if (comment.contains("/**") && comment.contains("*/")) {
					return true;
				}
			}
		}
		return false;
	}

	private void fillInWizard() {
		RedDeerTestCaseWizardPageOne pageOne = new RedDeerTestCaseWizardPageOne();
		pageOne.setAfterClassTearDown(true);
		pageOne.setBeforeClassSetup(true);
		pageOne.setSetupMethod(true);
		pageOne.setTearDownMethod(true);
		pageOne.setGenerateComments(true);

		pageOne.setName(TEST_CLASS_NAME);
		pageOne.setPackage(PACKAGE);

		wizard.next();
		RedDeerTestCaseWizardPageTwo pageTwo = new RedDeerTestCaseWizardPageTwo();
		pageTwo.selectAll();

		// save the names of selected methods
		Tree tree = new DefaultTree();
		for (TreeItem item : tree.getAllItems()) {
			if (item.getText().contains("(") && item.isChecked()) {
				methodNames.add(item.getText());
			}
		}
	}
	
	private void parseMethodNames() {
		List<String> testMethods = new ArrayList<>();
		Iterator<String> iterator = methodNames.iterator();
		
		//first process parameterless methods
		//medhod() -> testMethod()
		while (iterator.hasNext()) {
			String method = iterator.next();
			if (method.contains("()")) {
				testMethods.add("test" + method.substring(0, 1).toUpperCase()
						+ method.substring(1, method.indexOf("(")));
				iterator.remove();
			}
		}
		
		//then process parametrized methods
		for (String method : methodNames) {
			String testMethod = "test" + method.substring(0, 1).toUpperCase()
					+ method.substring(1, method.indexOf("("));

			//if the method is overloaded, use parameter types in the name
			//method(foo, bar) -> testMethodFooBar
			if (testMethods.contains(testMethod)) {
				String[] split = method.substring(method.indexOf("(") + 1, method.lastIndexOf(")")).split(",");
				for (int i = 0; i < split.length; i++) {
					testMethod += split[i].trim().substring(0, 1).toUpperCase() + split[i].trim().substring(1);
				}
			}
			testMethods.add(testMethod);
		}
		
		methodNames = testMethods;
	}

	private static void createTestProject() {
		JavaProjectWizard dialog = new JavaProjectWizard();
		dialog.open();
		new NewJavaProjectWizardPageOne().setProjectName(PROJECT_NAME);
		dialog.finish();

		NewClassCreationWizard classDialog = new NewClassCreationWizard();
		classDialog.open();
		NewClassWizardPage classPage = new NewClassWizardPage();
		classPage.setName(CLASS_NAME);
		classPage.setPackage(PACKAGE);
		classDialog.finish();
	}

	@Override
	String getWizardText() {
		return "New RedDeer Test Case";
	}
}
