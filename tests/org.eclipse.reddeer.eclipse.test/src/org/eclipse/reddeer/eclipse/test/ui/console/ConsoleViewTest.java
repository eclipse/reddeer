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
package org.eclipse.reddeer.eclipse.test.ui.console;

import static org.eclipse.reddeer.common.wait.WaitProvider.waitUntil;
import static org.eclipse.reddeer.common.wait.WaitProvider.waitWhile;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import org.eclipse.reddeer.common.matcher.RegexMatcher;
import org.eclipse.reddeer.common.wait.AbstractWait;
import org.eclipse.reddeer.common.wait.GroupWait;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.core.matcher.WithTextMatchers;
import org.eclipse.reddeer.eclipse.condition.ConsoleHasLabel;
import org.eclipse.reddeer.eclipse.condition.ConsoleHasLaunch;
import org.eclipse.reddeer.eclipse.condition.ConsoleHasNoChange;
import org.eclipse.reddeer.eclipse.condition.ConsoleHasText;
import org.eclipse.reddeer.eclipse.condition.ConsoleIsTerminated;
import org.eclipse.reddeer.eclipse.jdt.ui.packageview.PackageExplorerPart;
import org.eclipse.reddeer.eclipse.jdt.ui.wizards.JavaProjectWizard;
import org.eclipse.reddeer.eclipse.jdt.ui.wizards.NewClassCreationWizard;
import org.eclipse.reddeer.eclipse.jdt.ui.wizards.NewClassWizardPage;
import org.eclipse.reddeer.eclipse.jdt.ui.wizards.NewJavaProjectWizardPageOne;
import org.eclipse.reddeer.eclipse.ui.console.ConsoleView;
import org.eclipse.reddeer.eclipse.ui.perspectives.JavaPerspective;
import org.eclipse.reddeer.eclipse.utils.DeleteUtils;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.requirements.openperspective.OpenPerspectiveRequirement.OpenPerspective;
import org.eclipse.reddeer.swt.api.StyledText;
import org.eclipse.reddeer.swt.impl.menu.ShellMenuItem;
import org.eclipse.reddeer.swt.impl.styledtext.DefaultStyledText;
import org.eclipse.reddeer.swt.impl.toolbar.DefaultToolItem;
import org.eclipse.reddeer.workbench.core.condition.JobIsRunning;
import org.eclipse.reddeer.workbench.impl.editor.DefaultEditor;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
@OpenPerspective(JavaPerspective.class)
public class ConsoleViewTest {

	private static ConsoleView consoleView;

	private static final String TEST_PROJECT_NAME = "Project";
	private static final String TEST_CLASS_NAME = "TestClass";
	private static final String TEST_CLASS_NAME1 = "TestClass1";
	private static final String TEST_CLASS_NAME2 = "TestClass2";
	private static final String TEST_CLASS_LOOP_NAME = "TestLoopClass";
	private static final String TEST_CLASS_LOOP2_NAME = "TestLoopClass2";

	@BeforeClass
	public static void setupClass() {
		createTestProject();
	}

	@AfterClass
	public static void tearDownClass() {
		PackageExplorerPart explorer = new PackageExplorerPart();
		explorer.open();
		DeleteUtils.forceProjectDeletion(explorer.getProject(TEST_PROJECT_NAME),true);
	}

	private void runTestClassAndWaitToFinish() {
		runTestClass(TEST_CLASS_NAME);
		new WaitUntil(new ConsoleIsTerminated());
	}
	
	@Test
	public void testConsoleHasAnyText() {
		consoleView = new ConsoleView();
		while (consoleView.consoleHasLaunch()) {
			consoleView.terminateConsole();
			consoleView.removeLaunch();
		}
		
		assertFalse("ConsoleHasText wait condition should be false, because there is no text in console",
				new ConsoleHasText().test());
	}
	
	@Test
	public void testConsoleSwitching() {
		consoleView = new ConsoleView();
		consoleView.open();
		runTestClass(TEST_CLASS_NAME1);
		consoleView.toggleShowConsoleOnStandardOutChange(false);
		runTestClass(TEST_CLASS_NAME2);
		consoleView.switchConsole(new RegexMatcher(".*" + TEST_CLASS_NAME1
				+ ".*"));
		assertThat(consoleView.getConsoleText(),
				IsEqual.equalTo("Hello World1"));
		consoleView.switchConsole(new RegexMatcher(".*" + TEST_CLASS_NAME2
				+ ".*"));
		assertThat(consoleView.getConsoleText(),
				IsEqual.equalTo("Hello World2"));
		consoleView.toggleShowConsoleOnStandardOutChange(true);
	}

	@Test
	public void testConsoleView() {
		runTestClassAndWaitToFinish();
		testGettingConsoleTest();
		testClearConsole();
	}

	@Test
	public void testRemoveLaunch() {
		runTestClassAndWaitToFinish();
		consoleView = new ConsoleView();
		consoleView.open();
		new WaitUntil(new ConsoleHasLaunch());
		consoleView.removeLaunch();
		new WaitWhile(new ConsoleHasLaunch());
		assertFalse("Some launches remain" , consoleView.consoleHasLaunch());
	}

	@Test
	public void testRemoveAllTerminatedLaunches() {
		runTestClassAndWaitToFinish();
		consoleView = new ConsoleView();
		consoleView.open();
		new WaitUntil(new ConsoleHasLaunch());
		consoleView.removeAllTerminatedLaunches();
		assertFalse("Some launches remain" , consoleView.consoleHasLaunch());
	}

	@Test
	public void testTerminateConsole() {

		runTestClass(TEST_CLASS_LOOP_NAME);
		AbstractWait.sleep(TimePeriod.SHORT);

		consoleView = new ConsoleView();
		consoleView.open();
		consoleView.terminateConsole();

		String text = consoleView.getConsoleText();
		AbstractWait.sleep(TimePeriod.SHORT);
		String text2 = consoleView.getConsoleText();
		assertFalse(text.trim().isEmpty());
		assertEquals(text, text2);

		DefaultToolItem terminate = new DefaultToolItem("Terminate");
		assertFalse(terminate.isEnabled());

	}

	@Test
	public void consoleHasNoChangeTest() {
		runTestClass(TEST_CLASS_LOOP2_NAME);
		new GroupWait(TimePeriod.LONG, waitUntil(new ConsoleHasText("Start")),
				waitUntil(new ConsoleHasNoChange(TimePeriod.getCustom(11))));
		consoleView.open();
		consoleView.terminateConsole();
		// compare the text without white spaces
		assertEquals("StartHelloApplication", consoleView.getConsoleText().replaceAll("\\s", ""));
	}
	
	@Test
	public void toggleShowConsoleOnStandardOutChange() {
		runTestClassAndWaitToFinish();
		consoleView = new ConsoleView();
		consoleView.open();
		consoleView.toggleShowConsoleOnStandardOutChange(true);
		consoleView.toggleShowConsoleOnStandardOutChange(false);
	}

	@After
	public void tearDown(){
		consoleView = new ConsoleView();
		consoleView.open();
		// clean up all launches
		while (consoleView.consoleHasLaunch()){
			consoleView.toggleShowConsoleOnStandardOutChange(true);
			consoleView.terminateConsole();
			consoleView.removeLaunch();
		}		
	}
	
	private void testGettingConsoleTest() {
		consoleView = new ConsoleView();
		consoleView.open();
		String text = consoleView.getConsoleText();
		assertThat(text, IsNull.notNullValue());
		assertThat(text, IsEqual.equalTo("Hello World"));
	}

	private void testClearConsole() {
		consoleView = new ConsoleView();
		consoleView.open();
		consoleView.clearConsole();
		String text = consoleView.getConsoleText();
		assertThat(text, IsEqual.equalTo(""));
	}

	private static void createTestProject() {
		PackageExplorerPart packageExplorer = new PackageExplorerPart();
		packageExplorer.open();
		if (!packageExplorer.containsProject(TEST_PROJECT_NAME)) {
			createJavaProject();
			createJavaClass(TEST_CLASS_NAME, "System.out.print(\"Hello World\");");
			createJavaClass(TEST_CLASS_LOOP_NAME, "int i = 0; while (true) {System.out.println(i++);}");
			createJavaClass(TEST_CLASS_LOOP2_NAME, "try {System.out.println(\"Start\");\n"
					+ "Thread.sleep(10 * 1000);\n" + "System.out.println(\"Hello Application\");\n"
					+ "Thread.sleep(20 * 1000);\n" + "System.out.println(\"Finish\");\n"
					+ "} catch (InterruptedException e) {e.printStackTrace();}");
			createJavaClass(
					TEST_CLASS_NAME1,
					"System.out.print(\"Hello World1\");\ntry {\nThread.sleep(15*1000);\n} catch (InterruptedException e) {e.printStackTrace();}");
			createJavaClass(
					TEST_CLASS_NAME2,
					"System.out.print(\"Hello World2\");\ntry {\nThread.sleep(15*1000);\n} catch (InterruptedException e) {e.printStackTrace();}");
		}
		packageExplorer.getProject(TEST_PROJECT_NAME).select();
	}

	private static void createJavaProject() {
		JavaProjectWizard javaProject = new JavaProjectWizard();
		javaProject.open();

		NewJavaProjectWizardPageOne javaWizardPage = new NewJavaProjectWizardPageOne(javaProject);
		javaWizardPage.setProjectName(TEST_PROJECT_NAME);

		javaProject.finish();
	}

	private static void createJavaClass(String name, String text) {
		NewClassCreationWizard javaClassDialog = new NewClassCreationWizard();
		javaClassDialog.open();

		NewClassWizardPage wizardPage = new NewClassWizardPage(javaClassDialog);
		wizardPage.setName(name);
		wizardPage.setPackage("test");
		wizardPage.setStaticMainMethod(true);
		javaClassDialog.finish();

		StyledText dst = new DefaultStyledText();
		dst.insertText(7, 0, text);
		new DefaultEditor().save();
	}

	private static void runTestClass(String name) {
		PackageExplorerPart explorer = new PackageExplorerPart();
		explorer.open();
		explorer.getProject(TEST_PROJECT_NAME).getProjectItem("src", "test", name + ".java").select();
		RegexMatcher[] array = { new RegexMatcher("Run.*"), new RegexMatcher("Run As.*"),
				new RegexMatcher(".*Java Application.*") };
		WithTextMatchers m = new WithTextMatchers(array);
		new ShellMenuItem(m.getMatchers()).select();
		new GroupWait(TimePeriod.getCustom(20), waitUntil(new ConsoleHasLabel(new RegexMatcher(".*" + name	+ ".*"))),
				waitWhile(new JobIsRunning()));		
	}
}
