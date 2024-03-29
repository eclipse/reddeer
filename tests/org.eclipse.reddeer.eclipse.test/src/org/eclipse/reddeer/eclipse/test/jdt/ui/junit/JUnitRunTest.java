/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.eclipse.test.jdt.ui.junit;

import static org.junit.Assert.assertEquals;

import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.eclipse.jdt.junit.ui.TestRunnerViewPart;
import org.eclipse.reddeer.eclipse.jdt.ui.packageview.PackageExplorerPart;
import org.eclipse.reddeer.eclipse.jdt.ui.wizards.JavaProjectWizard;
import org.eclipse.reddeer.eclipse.jdt.ui.wizards.NewClassCreationWizard;
import org.eclipse.reddeer.eclipse.jdt.ui.wizards.NewClassWizardPage;
import org.eclipse.reddeer.eclipse.jdt.ui.wizards.NewJavaProjectWizardPageOne;
import org.eclipse.reddeer.eclipse.utils.DeleteUtils;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.requirements.cleanworkspace.CleanWorkspaceRequirement.CleanWorkspace;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.impl.button.FinishButton;
import org.eclipse.reddeer.swt.impl.button.NextButton;
import org.eclipse.reddeer.swt.impl.combo.DefaultCombo;
import org.eclipse.reddeer.swt.impl.list.DefaultList;
import org.eclipse.reddeer.swt.impl.menu.ContextMenuItem;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.workbench.impl.editor.TextEditor;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * 
 * @author apodhrad
 *
 */
@CleanWorkspace
@RunWith(RedDeerSuite.class)
public class JUnitRunTest {

	private static final String PROJECT_NAME = "hellotest";

	@BeforeClass
	public static void createTestProject() {
		JavaProjectWizard projectWizard = new JavaProjectWizard();
		projectWizard.open();
		NewJavaProjectWizardPageOne page = new NewJavaProjectWizardPageOne(projectWizard);
		page.setProjectName(PROJECT_NAME);
		page.createModuleInfoFile(false);
		projectWizard.finish();

		NewClassCreationWizard classWizard = new NewClassCreationWizard();
		classWizard.open();
		new NewClassWizardPage(classWizard).setName("HelloTest");
		classWizard.finish();

		PackageExplorerPart explorer = new PackageExplorerPart();
		explorer.open();

		explorer.getProject(PROJECT_NAME).select();
		new ContextMenuItem("Build Path", "Add Libraries...").select();
		new DefaultShell("Add Library");
		new DefaultList().select("JUnit");
		new NextButton().click();
		new DefaultCombo().setSelection("JUnit 4");
		new FinishButton().click();
		new WaitWhile(new ShellIsAvailable("Add Library"));

		explorer.getProject("hellotest").getProjectItem("src", "hellotest", "HelloTest.java").open();
		TextEditor editor = new TextEditor("HelloTest.java");
		editor.setText("package hellotest;\n\n"
				+ "import org.junit.After;\nimport org.junit.Assert;\nimport org.junit.Test;\n\n"
				+ "public class HelloTest {\n\n"
				+ "\t@Test\n\tpublic void badTest() {\n\t\tAssert.assertEquals(5, 2 + 2);\n\t}\n\n"
				+ "\t@Test\n\tpublic void veryBadTest() {\n\t\tthrow new RuntimeException();\n\t}\n\n"
				+ "\t@After\n\tpublic void waitAfter() throws Exception {\n\t\tThread.sleep(5000);\n\t}" + "\n}");
		editor.save();
		editor.close();
	}

	@AfterClass
	public static void deleteTestProject() {
		PackageExplorerPart explorer = new PackageExplorerPart();
		explorer.open();
		DeleteUtils.forceProjectDeletion(explorer.getProject(PROJECT_NAME), true);
	}

	@Test
	public void junitRunTest() {
		PackageExplorerPart explorer = new PackageExplorerPart();
		explorer.open();
		explorer.getProject(PROJECT_NAME).getProjectItem("src", "hellotest", "HelloTest.java").runAsJUnitTest(TimePeriod.getCustom(90));

		TestRunnerViewPart junitView = new TestRunnerViewPart();
		junitView.open();

		assertEquals("2/2", junitView.getRunStatus());
		assertEquals(1, junitView.getNumberOfErrors());
		assertEquals(1, junitView.getNumberOfFailures());
	}
}
