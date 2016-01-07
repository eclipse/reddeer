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
package org.jboss.reddeer.eclipse.test.jdt.ui.junit;

import static org.junit.Assert.assertEquals;

import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.core.condition.ShellWithTextIsAvailable;
import org.jboss.reddeer.eclipse.jdt.ui.NewJavaClassWizardDialog;
import org.jboss.reddeer.eclipse.jdt.ui.NewJavaClassWizardPage;
import org.jboss.reddeer.eclipse.jdt.ui.ide.NewJavaProjectWizardDialog;
import org.jboss.reddeer.eclipse.jdt.ui.ide.NewJavaProjectWizardPage;
import org.jboss.reddeer.eclipse.jdt.ui.junit.JUnitView;
import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.PackageExplorer;
import org.jboss.reddeer.eclipse.utils.DeleteUtils;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.impl.button.FinishButton;
import org.jboss.reddeer.swt.impl.button.NextButton;
import org.jboss.reddeer.swt.impl.list.DefaultList;
import org.jboss.reddeer.swt.impl.menu.ContextMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.workbench.impl.editor.TextEditor;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * 
 * @author apodhrad
 *
 */
@RunWith(RedDeerSuite.class)
public class JUnitRunTest {

	private static final String PROJECT_NAME = "hellotest";

	@BeforeClass
	public static void createTestProject() {
		NewJavaProjectWizardDialog projectWizard = new NewJavaProjectWizardDialog();
		projectWizard.open();
		new NewJavaProjectWizardPage().setProjectName(PROJECT_NAME);
		projectWizard.finish();

		NewJavaClassWizardDialog classWizard = new NewJavaClassWizardDialog();
		classWizard.open();
		new NewJavaClassWizardPage().setName("HelloTest");
		classWizard.finish();

		PackageExplorer explorer = new PackageExplorer();
		explorer.open();

		explorer.getProject(PROJECT_NAME).select();
		new ContextMenu("Build Path", "Add Libraries...").select();
		new DefaultShell("Add Library");
		new DefaultList().select("JUnit");
		new NextButton().click();
		new FinishButton().click();
		new WaitWhile(new ShellWithTextIsAvailable("Add Library"));

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
		PackageExplorer explorer = new PackageExplorer();
		explorer.open();
		DeleteUtils.forceProjectDeletion(explorer.getProject(PROJECT_NAME), true);
	}

	@Test
	public void junitRunTest() {
		PackageExplorer explorer = new PackageExplorer();
		explorer.open();
		explorer.getProject(PROJECT_NAME).getProjectItem("src", "hellotest", "HelloTest.java").runAsJUnitTest();

		JUnitView junitView = new JUnitView();
		junitView.open();

		assertEquals("2/2", junitView.getRunStatus());
		assertEquals(1, junitView.getNumberOfErrors());
		assertEquals(1, junitView.getNumberOfFailures());
	}
}
