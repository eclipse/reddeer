package org.jboss.reddeer.eclipse.test.ui.console;

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
import org.jboss.reddeer.swt.api.StyledText;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.styledtext.DefaultStyledText;
import org.jboss.reddeer.swt.matcher.RegexMatchers;
import org.jboss.reddeer.swt.test.RedDeerTest;
import org.jboss.reddeer.workbench.editor.DefaultEditor;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ConsoleViewTest extends RedDeerTest{

	private static ConsoleView consoleView;
	
	private static final String TEST_PROJECT_NAME = "Project";
	
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
		runTestProject();
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
			createJavaClass();
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
	
	private static void createJavaClass() {
		NewJavaClassWizardDialog javaClassDialog = new NewJavaClassWizardDialog();
		javaClassDialog.open();
		
		NewJavaClassWizardPage wizardPage = javaClassDialog.getFirstPage();
		wizardPage.setName("TestClass");
		wizardPage.setPackage("test");
		wizardPage.setStaticMainMethod(true);
		javaClassDialog.finish();
		
		StyledText dst = new DefaultStyledText();
		dst.insertText(7, 0, "System.out.print(\"Hello World\");");
		new DefaultEditor().save();
	}
	
	private static void runTestProject() {
		new PackageExplorer().getProject(TEST_PROJECT_NAME).select();
		RegexMatchers m = new RegexMatchers("Run.*", "Run As.*", ".*Java Application.*");
		new ShellMenu(m.getMatchers()).select();
	}
}
