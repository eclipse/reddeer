package org.jboss.reddeer.eclipse.test.ui.console;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;
import org.jboss.reddeer.eclipse.jdt.ui.NewJavaClassWizardDialog;
import org.jboss.reddeer.eclipse.jdt.ui.NewJavaClassWizardPage;
import org.jboss.reddeer.eclipse.jdt.ui.ide.NewJavaProjectWizardDialog;
import org.jboss.reddeer.eclipse.jdt.ui.ide.NewJavaProjectWizardPage;
import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.PackageExplorer;
import org.jboss.reddeer.eclipse.ui.console.ConsoleView;
import org.jboss.reddeer.eclipse.ui.perspectives.JavaPerspective;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.requirements.openperspective.OpenPerspectiveRequirement.OpenPerspective;
import org.jboss.reddeer.swt.api.StyledText;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.styledtext.DefaultStyledText;
import org.jboss.reddeer.swt.impl.toolbar.DefaultToolItem;
import org.jboss.reddeer.swt.matcher.RegexMatcher;
import org.jboss.reddeer.swt.wait.AbstractWait;
import org.jboss.reddeer.swt.wait.TimePeriod;
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
	private static final String TEST_CLASS_LOOP_NAME = "TestLoopClass";
	
	@BeforeClass
	public static void setupClass() {
		createTestProject();
	}
	
	@AfterClass
	public static void tearDownClass() {
		new PackageExplorer().getProject(TEST_PROJECT_NAME).delete(true);
	}
	
	@Before
	public void setupTest() {
		runTestClass(TEST_CLASS_NAME);
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
		// make sure console has a launch
		new DefaultStyledText();

		consoleView.removeLaunch();
		try {
			new DefaultStyledText();
			fail("Some launches remain");
		} catch (Exception ex) {
			// ok, no styled text can be found
		}
	}
	
	@Test
	public void testRemoveAllTerminatedLaunches() {
		consoleView = new ConsoleView();
		consoleView.open();
		// make sure console has a launch
		new DefaultStyledText();

		consoleView.removeAllTerminatedLaunches();
		try {
			new DefaultStyledText();
			fail("Some launches remain");
		} catch (Exception ex) {
			// ok, no styled text can be found
		}
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
	
	@SuppressWarnings("unchecked")
	private static void runTestClass(String name) {
		new PackageExplorer().getProject(TEST_PROJECT_NAME).getProjectItem("src", "test", name + ".java").select();
		new ShellMenu(new RegexMatcher("Run.*"), new RegexMatcher("Run As.*"), new RegexMatcher(".*Java Application.*")).select();		
	}
}
