///*******************************************************************************
// * Copyright (c) 2019 Red Hat, Inc and others.
// * This program and the accompanying materials are made available under the
// * terms of the Eclipse Public License 2.0 which is available at
// * http://www.eclipse.org/legal/epl-2.0.
// *
// * SPDX-License-Identifier: EPL-2.0
// *
// * Contributors:
// *     Red Hat, Inc - initial API and implementation
// *******************************************************************************/
//
//package org.eclipse.reddeer.eclipse.test.jdt.ui.wizards;
//
//import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.fail;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.eclipse.reddeer.common.exception.RedDeerException;
//import org.eclipse.reddeer.common.matcher.RegexMatcher;
//import org.eclipse.reddeer.common.wait.TimePeriod;
//import org.eclipse.reddeer.common.wait.WaitUntil;
//import org.eclipse.reddeer.common.wait.WaitWhile;
//import org.eclipse.reddeer.core.matcher.WithTextMatcher;
//import org.eclipse.reddeer.eclipse.jdt.ui.wizards.JavaProjectWizard;
//import org.eclipse.reddeer.eclipse.jdt.ui.wizards.NewInterfaceCreationWizard;
//import org.eclipse.reddeer.eclipse.jdt.ui.wizards.NewInterfaceCreationWizardPage;
//import org.eclipse.reddeer.eclipse.jdt.ui.wizards.NewJavaProjectWizardPageOne;
//import org.eclipse.reddeer.eclipse.ui.navigator.resources.ProjectExplorer;
//import org.eclipse.reddeer.eclipse.ui.perspectives.JavaPerspective;
//import org.eclipse.reddeer.junit.runner.RedDeerSuite;
//import org.eclipse.reddeer.requirements.openperspective.OpenPerspectiveRequirement.OpenPerspective;
//import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
//import org.eclipse.reddeer.swt.impl.button.PushButton;
//import org.eclipse.reddeer.workbench.condition.EditorWithTitleIsActive;
//import org.eclipse.reddeer.workbench.handler.EditorHandler;
//import org.eclipse.reddeer.workbench.impl.editor.TextEditor;
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
///**
// * Test class for Java Interface Wizard
// * 
// * @author zcervink@redhat.com
// * 
// */
//@RunWith(RedDeerSuite.class)
//@OpenPerspective(JavaPerspective.class)
//public class NewInterfaceCreationWizardTest {
//
//	protected static final String PROJECT_NAME = "testproject";
//	protected static final String INTERFACE_NAME = "InterfaceName";
//	protected static final String SOURCE_FOLDER_NAME = PROJECT_NAME + "/src";
//	protected static final String PACKAGE_NAME = "org.example.test";
//	protected static final String ENCLOSING_TYPE_CUSTOM_VALUE = "org.example.test." + INTERFACE_NAME;
//
//	@BeforeClass
//	public static void setup() {
//		createTestProject();
//	}
//
//	@After
//	public void closeAllEditors() {
//		ShellIsAvailable shell = new ShellIsAvailable(new WithTextMatcher(new RegexMatcher("[Extended|Implemented].*Interfaces Selection")));
//		new WaitWhile(shell, TimePeriod.SHORT, false);
//		if (shell.getResult() != null) {
//			shell.getResult().close();
//		}
//		EditorHandler.getInstance().closeAll(true);
//	}
//
//	@AfterClass
//	public static void clean() {
//		ProjectExplorer pe = new ProjectExplorer();
//		pe.open();
//		pe.deleteAllProjects(true, TimePeriod.LONG);
//	}
//
//	@Test
//	public void testCreateNewInterface() {
//		// open, fill and finish the wizard
//		NewInterfaceCreationWizard interfaceDialog = new NewInterfaceCreationWizard();
//		interfaceDialog.open();
//		NewInterfaceCreationWizardPage interfacePage = new NewInterfaceCreationWizardPage(interfaceDialog);
//		interfacePage.setPackage(PACKAGE_NAME).setName(INTERFACE_NAME);
//		interfaceDialog.finish();
//		new WaitUntil(new EditorWithTitleIsActive(INTERFACE_NAME + ".java"), TimePeriod.LONG);
//
//		// check created interface file content
//		TextEditor te = new TextEditor(INTERFACE_NAME + ".java");
//
//		assertTrue("Interface source file should contain String '" + "package " + PACKAGE_NAME + "'",
//				te.getPositionOfText("package " + PACKAGE_NAME, 0) >= 0);
//		assertTrue("Interface source file should contain String '" + "public interface " + INTERFACE_NAME + "'",
//				te.getPositionOfText("public interface " + INTERFACE_NAME, 0) >= 0);
//
//	}
//
//	@Test
//	public void testCreateNewInterfaceWithCustomSettings() {
//		// open, fill and finish the wizard
//		NewInterfaceCreationWizard interfaceDialog = new NewInterfaceCreationWizard();
//		interfaceDialog.open();
//		NewInterfaceCreationWizardPage interfacePage = new NewInterfaceCreationWizardPage(interfaceDialog);
//		interfacePage.setPackage(PACKAGE_NAME + "2").setName(INTERFACE_NAME + "2");
//		interfacePage.togglePackageModifier(true);
//		interfacePage.addExtendedInterface("javax.accessibility.AccessibleAction");
//		interfacePage.toggleGenerateCommentsCheckbox(true);
//		interfaceDialog.finish();
//		new WaitUntil(new EditorWithTitleIsActive(INTERFACE_NAME + "2.java"), TimePeriod.LONG);
//
//		// check created interface file content
//		TextEditor te = new TextEditor(INTERFACE_NAME + "2.java");
//
//		assertTrue("Interface source file should contain String '" + "package " + PACKAGE_NAME + "2" + "'",
//				te.getPositionOfText("package " + PACKAGE_NAME + "2", 0) >= 0);
//		assertTrue(
//				"Interface source file should contain String '" + "\ninterface " + INTERFACE_NAME
//						+ "2 extends AccessibleAction" + "'",
//				te.getPositionOfText("\ninterface " + INTERFACE_NAME + "2 extends AccessibleAction", 0) >= 0);
//		assertTrue(
//				"Interface source file should contain String '" + "import javax.accessibility.AccessibleAction;" + "'",
//				te.getPositionOfText("import javax.accessibility.AccessibleAction;", 0) >= 0);
//		assertTrue("Interface source file should contain String '" + "/**\n *" + "'",
//				te.getPositionOfText("/**\n *", 0) >= 0);
//	}
//
//	@Test
//	public void testSettingAndGettingInputAndCheckboxValues() {
//		// open the wizard
//		NewInterfaceCreationWizard interfaceDialog = new NewInterfaceCreationWizard();
//		interfaceDialog.open();
//		NewInterfaceCreationWizardPage interfacePage = new NewInterfaceCreationWizardPage(interfaceDialog);
//
//		// fill and test values in input fields
//		interfacePage.setSourceFolder(SOURCE_FOLDER_NAME).setPackage(PACKAGE_NAME).setName(INTERFACE_NAME);
//
//		assertTrue(
//				"Source folder name obtained from the input using getSourceFolder() function is not the same as name set using setSourceFolder().",
//				interfacePage.getSourceFolder().equals(SOURCE_FOLDER_NAME));
//		assertTrue(
//				"Package name obtained from the input using getPackage() function is not the same as name set using setPackage().",
//				interfacePage.getPackage().equals(PACKAGE_NAME));
//		assertTrue(
//				"Interface name obtained from the input using getName() function is not the same as name set using setName().",
//				interfacePage.getName().equals(INTERFACE_NAME));
//
//		// test toggling the "Enclosing type:" checkbox and changing the input value,
//		// test the modifier radio buttons
//		interfacePage.toggleEnclosingTypeCheckbox(true);
//
//		String enclosingTypeDefaultValue = interfacePage.getEnclosingType();
//		interfacePage.setEnclosingType(ENCLOSING_TYPE_CUSTOM_VALUE);
//		assertTrue(
//				"Interface enclosing type obtained from the wizard using getEnclosingType() is not the same as the type given using setEnclosingType().",
//				interfacePage.getEnclosingType().equals(ENCLOSING_TYPE_CUSTOM_VALUE));
//		interfacePage.setEnclosingType(enclosingTypeDefaultValue);
//
//		assertTrue("Currently set modifier should be 'public', but is '" + interfacePage.getCurrentModifier() + "'.",
//				interfacePage.togglePublicModifier(true).getCurrentModifier().equals("public"));
//		assertTrue("Currently set modifier should be 'package', but is '" + interfacePage.getCurrentModifier() + "'.",
//				interfacePage.togglePackageModifier(true).getCurrentModifier().equals("package"));
//		assertTrue("Currently set modifier should be 'private', but is '" + interfacePage.getCurrentModifier() + "'.",
//				interfacePage.togglePrivateModifier(true).getCurrentModifier().equals("private"));
//		assertTrue("Currently set modifier should be 'protected', but is '" + interfacePage.getCurrentModifier() + "'.",
//				interfacePage.toggleProtectedModifier(true).getCurrentModifier().equals("protected"));
//
//		interfacePage.toggleEnclosingTypeCheckbox(false);
//
//		// toggle generate comments checkbox
//		boolean generateCommentsCheckboxState = interfacePage.getGenerateCommentsCheckboxState();
//		interfacePage.toggleGenerateCommentsCheckbox(!generateCommentsCheckboxState);
//		assertTrue(
//				"The Generate comments checkbox state should be '" + !generateCommentsCheckboxState + "' but is'"
//						+ generateCommentsCheckboxState + " '.",
//				interfacePage.getGenerateCommentsCheckboxState() != generateCommentsCheckboxState);
//		interfacePage.toggleGenerateCommentsCheckbox(generateCommentsCheckboxState);
//		assertTrue(
//				"The Generate comments checkbox state should be '" + generateCommentsCheckboxState + "' but is'"
//						+ !generateCommentsCheckboxState + " '.",
//				interfacePage.getGenerateCommentsCheckboxState() == generateCommentsCheckboxState);
//
//		// cancel the wizard
//		interfaceDialog.cancel();
//	}
//
//	@Test
//	public void testAddingAndRemovingExtendedInterfaces() {
//		// open the wizard
//		NewInterfaceCreationWizard interfaceDialog = new NewInterfaceCreationWizard();
//		interfaceDialog.open();
//		NewInterfaceCreationWizardPage interfacePage = new NewInterfaceCreationWizardPage(interfaceDialog);
//
//		// test adding, verifying and deleting extended interfaces
//		List<String> extendedIterfaceList = new ArrayList<String>();
//		extendedIterfaceList.add("javax.accessibility.AccessibleAction");
//		extendedIterfaceList.add("javax.accessibility.AccessibleEditableText");
//		extendedIterfaceList.add("javax.accessibility.AccessibleExtendedTable");
//		extendedIterfaceList.add("javax.accessibility.AccessibleStreamable");
//		extendedIterfaceList.add("javax.accessibility.AccessibleText");
//
//		for (String extendedInterfaceName : extendedIterfaceList) {
//			interfacePage.addExtendedInterface(extendedInterfaceName);
//		}
//
//		assertTrue(
//				"List of interfaces obtained using function getExtendedInterfaces() has to be the same as list interfaces, that has been set.",
//				interfacePage.getExtendedInterfaces().equals(extendedIterfaceList));
//
//		for (String extendedInterfaceName : extendedIterfaceList) {
//			interfacePage.removeExtendedInterface(extendedInterfaceName);
//		}
//
//		// cancel the wizard
//		interfaceDialog.cancel();
//	}
//
//	@Test
//	public void testAddingAndRemovingNonexistingAndNotFullySpecifiedInterfaces() {
//		// open the wizard
//		NewInterfaceCreationWizard interfaceDialog = new NewInterfaceCreationWizard();
//		interfaceDialog.open();
//		NewInterfaceCreationWizardPage interfacePage = new NewInterfaceCreationWizardPage(interfaceDialog);
//
//		// try to add a non-existing extended interface
//		try {
//			interfacePage.addExtendedInterface("nonexisting.interface.name");
//			fail("RedDeer exception was not thrown when trying to add a non-existing extended interface.");
//		} catch (RedDeerException e) {
//			new PushButton("Cancel").click();
//		}
//
//		// try to remove an extended interface, that is not on the list
//		try {
//			interfacePage.removeExtendedInterface("javax.accessibility.AccessibleAction");
//			fail("RedDeer exception was not thrown when trying to remove an extended interface, that is not on the list.");
//		} catch (RedDeerException e) {
//
//		}
//
//		// try to remove an added extended interface, that was not fully specified
//		interfacePage.addExtendedInterface("org.w3c.dom.views.AbstractView");
//		try {
//			interfacePage.removeExtendedInterface("AbstractView");
//			fail("RedDeer exception was not thrown when trying to remove an extended interface, that was added, but was not fully specified.");
//		} catch (RedDeerException e) {
//			interfacePage.removeExtendedInterface("org.w3c.dom.views.AbstractView");
//		}
//
//		// cancel the wizard
//		interfaceDialog.cancel();
//	}
//
//	private static void createTestProject() {
//		JavaProjectWizard dialog = new JavaProjectWizard();
//		dialog.open();
//		new NewJavaProjectWizardPageOne(dialog).setProjectName(PROJECT_NAME);
//		dialog.finish();
//	}
//}
