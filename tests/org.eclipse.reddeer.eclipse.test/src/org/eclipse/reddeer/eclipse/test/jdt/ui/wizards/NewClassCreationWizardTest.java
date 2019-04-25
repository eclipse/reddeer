/*******************************************************************************
 * Copyright (c) 2019 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/

package org.eclipse.reddeer.eclipse.test.jdt.ui.wizards;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.reddeer.common.exception.RedDeerException;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.eclipse.jdt.ui.wizards.JavaProjectWizard;
import org.eclipse.reddeer.eclipse.jdt.ui.wizards.NewClassCreationWizard;
import org.eclipse.reddeer.eclipse.jdt.ui.wizards.NewClassWizardPage;
import org.eclipse.reddeer.eclipse.jdt.ui.wizards.NewJavaProjectWizardPageOne;
import org.eclipse.reddeer.eclipse.ui.navigator.resources.ProjectExplorer;
import org.eclipse.reddeer.eclipse.ui.perspectives.JavaPerspective;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.requirements.openperspective.OpenPerspectiveRequirement.OpenPerspective;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.workbench.condition.EditorWithTitleIsActive;
import org.eclipse.reddeer.workbench.handler.EditorHandler;
import org.eclipse.reddeer.workbench.impl.editor.TextEditor;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test class for Java Class Wizard
 * 
 * @author zcervink@redhat.com
 * 
 */
@RunWith(RedDeerSuite.class)
@OpenPerspective(JavaPerspective.class)
public class NewClassCreationWizardTest {

	protected static final String PROJECT_NAME = "testproject";
	protected static final String CLASS_NAME = "ClassName";
	protected static final String SOURCE_FOLDER_NAME = PROJECT_NAME + "/src";
	protected static final String PACKAGE_NAME = "org.example.test";
	protected static final String ENCLOSING_TYPE_CUSTOM_VALUE = "org.example.test." + CLASS_NAME;

	@BeforeClass
	public static void setup() {
		createTestProject();
	}

	@After
	public void closeAllEditors() {
		EditorHandler.getInstance().closeAll(true);
	}

	@AfterClass
	public static void clean() {
		ProjectExplorer pe = new ProjectExplorer();
		pe.open();
		pe.deleteAllProjects(true, TimePeriod.LONG);
	}

	@Test
	public void testCreateNewClass() {
		// open, fill and finish the wizard
		NewClassCreationWizard classDialog = new NewClassCreationWizard();
		classDialog.open();
		NewClassWizardPage classPage = new NewClassWizardPage(classDialog);
		classPage.setPackage(PACKAGE_NAME).setName(CLASS_NAME);
		classDialog.finish();
		new WaitUntil(new EditorWithTitleIsActive(CLASS_NAME + ".java"), TimePeriod.LONG);

		// check created class file content
		TextEditor te = new TextEditor(CLASS_NAME + ".java");

		assertTrue("Class source file should contain String '" + "package " + PACKAGE_NAME + "'",
				te.getPositionOfText("package " + PACKAGE_NAME, 0) >= 0);
		assertTrue("Class source file should contain String '" + "public class " + CLASS_NAME + "'",
				te.getPositionOfText("public class " + CLASS_NAME, 0) >= 0);

	}

	@Test
	public void testCreateNewClassWithCustomSettings() {
		// open, fill and finish the wizard
		NewClassCreationWizard classDialog = new NewClassCreationWizard();
		classDialog.open();
		NewClassWizardPage classPage = new NewClassWizardPage(classDialog);
		classPage.setPackage(PACKAGE_NAME + "2").setName(CLASS_NAME + "2");
		classPage.togglePackageModifier(true);
		classPage.toggleAbstractModifierCheckbox(true);
		classPage.setSuperclassName("java.lang.Exception");
		classPage.addExtendedInterface("javax.accessibility.AccessibleAction");
		classPage.setStaticMainMethod(true);
		classPage.toggleGenerateCommentsCheckbox(true);
		classDialog.finish();
		new WaitUntil(new EditorWithTitleIsActive(CLASS_NAME + "2.java"), TimePeriod.LONG);

		// check created class file content
		TextEditor te = new TextEditor(CLASS_NAME + "2.java");

		assertTrue("Class source file should contain String '" + "package " + PACKAGE_NAME + "2" + "'",
				te.getPositionOfText("package " + PACKAGE_NAME + "2", 0) >= 0);
		assertTrue(
				"Class source file should contain String '" + "\nabstract class " + CLASS_NAME
						+ "2 extends Exception implements AccessibleAction" + "'",
				te.getPositionOfText(
						"\nabstract class " + CLASS_NAME + "2 extends Exception implements AccessibleAction", 0) >= 0);
		assertTrue("Class source file should contain String '" + "import javax.accessibility.AccessibleAction;" + "'",
				te.getPositionOfText("import javax.accessibility.AccessibleAction;", 0) >= 0);
		assertTrue("Class source file should contain String '" + "/**\n *" + "'",
				te.getPositionOfText("/**\n *", 0) >= 0);
		assertTrue("Class source file should contain String '" + "public static void main(String[] args)" + "'",
				te.getPositionOfText("public static void main(String[] args)", 0) >= 0);
	}

	@Test
	public void testSettingAndGettingInputAndCheckboxValues() {
		// open the wizard
		NewClassCreationWizard classDialog = new NewClassCreationWizard();
		classDialog.open();
		NewClassWizardPage classPage = new NewClassWizardPage(classDialog);

		// fill and test values in input fields
		classPage.setSourceFolder(SOURCE_FOLDER_NAME).setPackage(PACKAGE_NAME).setName(CLASS_NAME);

		assertTrue(
				"Source folder name obtained from the input using getSourceFolder() function is not the same as name set using setSourceFolder().",
				classPage.getSourceFolder().equals(SOURCE_FOLDER_NAME));
		assertTrue(
				"Package name obtained from the input using getPackage() function is not the same as name set using setPackage().",
				classPage.getPackage().equals(PACKAGE_NAME));
		assertTrue(
				"Class name obtained from the input using getName() function is not the same as name set using setName().",
				classPage.getName().equals(CLASS_NAME));

		// test toggling the "Enclosing type:" checkbox and changing the input value,
		// test the modifier radio buttons
		classPage.toggleEnclosingTypeCheckbox(true);

		String enclosingTypeDefaultValue = classPage.getEnclosingType();
		classPage.setEnclosingType(ENCLOSING_TYPE_CUSTOM_VALUE);
		assertTrue(
				"Class enclosing type obtained from the wizard using getEnclosingType() is not the same as the type given using setEnclosingType().",
				classPage.getEnclosingType().equals(ENCLOSING_TYPE_CUSTOM_VALUE));
		classPage.setEnclosingType(enclosingTypeDefaultValue);

		assertTrue("Currently set modifier should be 'public', but is '" + classPage.getCurrentModifier() + "'.",
				classPage.togglePublicModifier(true).getCurrentModifier().equals("public"));
		assertTrue("Currently set modifier should be 'package', but is '" + classPage.getCurrentModifier() + "'.",
				classPage.togglePackageModifier(true).getCurrentModifier().equals("package"));
		assertTrue("Currently set modifier should be 'private', but is '" + classPage.getCurrentModifier() + "'.",
				classPage.togglePrivateModifier(true).getCurrentModifier().equals("private"));
		assertTrue("Currently set modifier should be 'protected', but is '" + classPage.getCurrentModifier() + "'.",
				classPage.toggleProtectedModifier(true).getCurrentModifier().equals("protected"));

		classPage.toggleEnclosingTypeCheckbox(false);

		// test toggling the abstract and final modifier checkbox
		assertTrue("State of abstract modifier should be 'true', but is not.",
				classPage.toggleAbstractModifierCheckbox(true).getAbstractModifierCheckboxState());
		assertTrue("State of abstract modifier should be 'false', but is not.",
				!classPage.toggleAbstractModifierCheckbox(false).getAbstractModifierCheckboxState());
		assertTrue("State of final modifier should be 'true', but is not.",
				classPage.toggleFinalModifierCheckbox(true).getFinalModifierCheckboxState());
		assertTrue("State of final modifier should be 'false', but is not.",
				!classPage.toggleFinalModifierCheckbox(false).getFinalModifierCheckboxState());

		// test toggling the checkbox for creating main method in newly created class
		assertTrue("State of checkbox for creating main method should be 'disabled', but is not.",
				!classPage.setStaticMainMethod(false).getStaticMainMethodCheckboxState());
		assertTrue("State of checkbox for creating main method should be 'enabled', but is not.",
				classPage.setStaticMainMethod(true).getStaticMainMethodCheckboxState());

		// toggle generate comments checkbox
		boolean generateCommentsCheckboxState = classPage.getGenerateCommentsCheckboxState();
		classPage.toggleGenerateCommentsCheckbox(!generateCommentsCheckboxState);
		assertTrue(
				"The Generate comments checkbox state should be '" + !generateCommentsCheckboxState + "' but is'"
						+ generateCommentsCheckboxState + " '.",
				classPage.getGenerateCommentsCheckboxState() != generateCommentsCheckboxState);
		classPage.toggleGenerateCommentsCheckbox(generateCommentsCheckboxState);
		assertTrue(
				"The Generate comments checkbox state should be '" + generateCommentsCheckboxState + "' but is'"
						+ !generateCommentsCheckboxState + " '.",
				classPage.getGenerateCommentsCheckboxState() == generateCommentsCheckboxState);

		// cancel the wizard
		classDialog.cancel();
	}

	@Test
	public void testAddingAndRemovingExtendedInterfaces() {
		// open the wizard
		NewClassCreationWizard classDialog = new NewClassCreationWizard();
		classDialog.open();
		NewClassWizardPage classPage = new NewClassWizardPage(classDialog);

		// test adding, verifying and deleting extended interfaces
		List<String> extendedIterfaceList = new ArrayList<String>();
		extendedIterfaceList.add("javax.accessibility.AccessibleAction");
		extendedIterfaceList.add("javax.accessibility.AccessibleEditableText");
		extendedIterfaceList.add("javax.accessibility.AccessibleExtendedTable");
		extendedIterfaceList.add("javax.accessibility.AccessibleStreamable");
		extendedIterfaceList.add("javax.accessibility.AccessibleText");

		for (String extendedInterfaceName : extendedIterfaceList) {
			classPage.addExtendedInterface(extendedInterfaceName);
		}

		assertTrue(
				"List of interfaces obtained using function getExtendedInterfaces() has to be the same as list interfaces, that has been set.",
				classPage.getExtendedInterfaces().equals(extendedIterfaceList));

		for (String extendedInterfaceName : extendedIterfaceList) {
			classPage.removeExtendedInterface(extendedInterfaceName);
		}

		// cancel the wizard
		classDialog.cancel();
	}

	@Test
	public void testAddingAndRemovingNonexistingAndNotFullySpecifiedInterfaces() {
		// open the wizard
		NewClassCreationWizard classDialog = new NewClassCreationWizard();
		classDialog.open();
		NewClassWizardPage classPage = new NewClassWizardPage(classDialog);

		// try to add a non-existing extended interface
		try {
			classPage.addExtendedInterface("nonexisting.interface.name");
			fail("RedDeer exception was not thrown when trying to add a non-existing extended interface.");
		} catch (RedDeerException e) {
			new PushButton("Cancel").click();
		}

		// try to add an extended interface, that is not fully specified by its name
		try {
			classPage.addExtendedInterface("Acl");
			fail("RedDeer exception was not thrown when trying to add an extended interface, that is not fully specified by its name.");
		} catch (RedDeerException e) {
			new PushButton("Cancel").click();
		}

		// try to remove an extended interface, that is not on the list
		try {
			classPage.removeExtendedInterface("javax.accessibility.AccessibleAction");
			fail("RedDeer exception was not thrown when trying to remove an extended interface, that is not on the list.");
		} catch (RedDeerException e) {

		}

		// try to remove an added extended interface, that was not fully specified
		classPage.addExtendedInterface("org.w3c.dom.views.AbstractView");
		try {
			classPage.removeExtendedInterface("AbstractView");
			fail("RedDeer exception was not thrown when trying to remove an extended interface, that was added, but was not fully specified.");
		} catch (RedDeerException e) {
			classPage.removeExtendedInterface("org.w3c.dom.views.AbstractView");
		}

		// cancel the wizard
		classDialog.cancel();
	}

	private static void createTestProject() {
		JavaProjectWizard dialog = new JavaProjectWizard();
		dialog.open();
		new NewJavaProjectWizardPageOne(dialog).setProjectName(PROJECT_NAME);
		dialog.finish();
	}
}
