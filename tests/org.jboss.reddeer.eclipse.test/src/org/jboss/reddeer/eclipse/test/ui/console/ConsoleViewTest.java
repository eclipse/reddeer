package org.jboss.reddeer.eclipse.test.ui.console;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;
import org.jboss.reddeer.eclipse.condition.ConsoleHasLaunch;
import org.jboss.reddeer.eclipse.condition.ConsoleHasNoChange;
import org.jboss.reddeer.eclipse.condition.ConsoleHasText;
import org.jboss.reddeer.eclipse.condition.ConsoleIsTerminated;
import org.jboss.reddeer.eclipse.jdt.ui.NewJavaClassWizardDialog;
import org.jboss.reddeer.eclipse.jdt.ui.NewJavaClassWizardPage;
import org.jboss.reddeer.eclipse.jdt.ui.ide.NewJavaProjectWizardDialog;
import org.jboss.reddeer.eclipse.jdt.ui.ide.NewJavaProjectWizardPage;
import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.PackageExplorer;
import org.jboss.reddeer.eclipse.ui.console.ConsoleView;
import org.jboss.reddeer.eclipse.ui.perspectives.JavaPerspective;
import org.jboss.reddeer.eclipse.utils.DeleteUtils;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.requirements.openperspective.OpenPerspectiveRequirement.OpenPerspective;
import org.jboss.reddeer.swt.api.StyledText;
import org.jboss.reddeer.swt.condition.JobIsRunning;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.styledtext.DefaultStyledText;
import org.jboss.reddeer.swt.impl.toolbar.ViewToolItem;
import org.jboss.reddeer.swt.matcher.RegexMatcher;
import org.jboss.reddeer.swt.matcher.WithTextMatchers;
import org.jboss.reddeer.swt.wait.AbstractWait;
import org.jboss.reddeer.swt.wait.TimePeriod;
import org.jboss.reddeer.swt.wait.WaitUntil;
import org.jboss.reddeer.swt.wait.WaitWhile;
import org.jboss.reddeer.workbench.impl.editor.DefaultEditor;
import org.junit.AfterClass;
import org.junit.Before;
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
		DeleteUtils.forceProjectDeletion(new PackageExplorer().getProject(TEST_PROJECT_NAME),true);
	}

	@Before
	public void setupTest() {
		runTestClass(TEST_CLASS_NAME);
		new WaitUntil(new ConsoleIsTerminated());
	}
	
	@Test
	public void testConsoleSwitching() {
		runTestClass(TEST_CLASS_NAME1);
		runTestClass(TEST_CLASS_NAME2);
		consoleView = new ConsoleView();
		consoleView.open();
		consoleView.switchConsole(new RegexMatcher(".*" + TEST_CLASS_NAME1
				+ ".*"));
		assertThat(consoleView.getConsoleText(),
				IsEqual.equalTo("Hello World1"));
		consoleView.switchConsole(new RegexMatcher(".*" + TEST_CLASS_NAME2
				+ ".*"));
		assertThat(consoleView.getConsoleText(),
				IsEqual.equalTo("Hello World2"));
	}

	@Test
	public void testConsoleView() {
		testGettingConsoleTest();
		testClearConsole();
	}

	@Test
	public void testRemoveLaunch() {
		consoleView = new ConsoleView();
		consoleView.open();
		new WaitUntil(new ConsoleHasLaunch());
		consoleView.removeLaunch();
		new WaitWhile(new ConsoleHasLaunch());
		assertFalse("Some launches remain" , consoleView.consoleHasLaunch());
	}

	@Test
	public void testRemoveAllTerminatedLaunches() {
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

		ViewToolItem terminate = new ViewToolItem("Terminate");
		assertFalse(terminate.isEnabled());

	}

	@Test
	public void consoleHasNoChangeTest() {
		consoleView = new ConsoleView();
		consoleView.open();
		consoleView.clearConsole();
		runTestClass(TEST_CLASS_LOOP2_NAME);
		new WaitUntil(new ConsoleHasText("Start"));
		new WaitUntil(new ConsoleHasNoChange(TimePeriod.getCustom(11)), TimePeriod.LONG);
		consoleView.open();
		consoleView.terminateConsole();
		// compare the text without white spaces
		assertEquals("StartHelloApplication", consoleView.getConsoleText().replaceAll("\\s", ""));
	}
	
	@Test
	public void toggleShowConsoleOnStandardOutChange() {
		consoleView = new ConsoleView();
		consoleView.open();
		consoleView.toggleShowConsoleOnStandardOutChange(true);
		consoleView.toggleShowConsoleOnStandardOutChange(false);
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
		PackageExplorer packageExplorer = new PackageExplorer();
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
					"System.out.print(\"Hello World1\");\ntry {\nThread.sleep(10*1000);\n} catch (InterruptedException e) {e.printStackTrace();}");
			createJavaClass(
					TEST_CLASS_NAME2,
					"System.out.print(\"Hello World2\");\ntry {\nThread.sleep(10*1000);\n} catch (InterruptedException e) {e.printStackTrace();}");
		}
		packageExplorer.getProject(TEST_PROJECT_NAME).select();
	}

	private static void createJavaProject() {
		NewJavaProjectWizardDialog javaProject = new NewJavaProjectWizardDialog();
		javaProject.open();

		NewJavaProjectWizardPage javaWizardPage = javaProject.getFirstPage();
		javaWizardPage.setProjectName(TEST_PROJECT_NAME);

		javaProject.finish(false);
	}

	private static void createJavaClass(String name, String text) {
		NewJavaClassWizardDialog javaClassDialog = new NewJavaClassWizardDialog();
		javaClassDialog.open();

		NewJavaClassWizardPage wizardPage = javaClassDialog.getFirstPage();
		wizardPage.setName(name);
		wizardPage.setPackage("test");
		wizardPage.setStaticMainMethod(true);
		javaClassDialog.finish();

		StyledText dst = new DefaultStyledText();
		dst.insertText(7, 0, text);
		new DefaultEditor().save();
	}

	private static void runTestClass(String name) {
		new PackageExplorer().getProject(TEST_PROJECT_NAME).getProjectItem("src", "test", name + ".java").select();
		RegexMatcher[] array = { new RegexMatcher("Run.*"), new RegexMatcher("Run As.*"),
				new RegexMatcher(".*Java Application.*") };
		WithTextMatchers m = new WithTextMatchers(array);
		new ShellMenu(m.getMatchers()).select();
		new WaitWhile(new JobIsRunning());
	}
}
