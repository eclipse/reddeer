package org.jboss.reddeer.eclipse.test.ui.problems;

import static org.junit.Assert.assertEquals;

import org.jboss.reddeer.eclipse.jdt.ui.ide.NewJavaProjectWizardDialog;
import org.jboss.reddeer.eclipse.jdt.ui.ide.NewJavaProjectWizardPage;
import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.PackageExplorer;
import org.jboss.reddeer.eclipse.ui.ide.NewJavaClassDialog;
import org.jboss.reddeer.eclipse.ui.problems.ProblemsView;
import org.jboss.reddeer.swt.condition.AllRunningJobsAreNotActive;
import org.jboss.reddeer.swt.util.Bot;
import org.jboss.reddeer.swt.wait.WaitUntilCondition;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Just a draft.
 * 
 * @author rhopp
 *
 */

public class ProblemsViewTest {
	
	private PackageExplorer pkgExplorer;
	private ProblemsView problemsView;
	
	@Before
	public void setup(){
		NewJavaProjectWizardDialog dialog = new NewJavaProjectWizardDialog();
		dialog.open();
		NewJavaProjectWizardPage page1 = new NewJavaProjectWizardPage(dialog); 
		page1.setProjectName("Test");
		dialog.finish();
		problemsView = new ProblemsView();
		problemsView.open();
		pkgExplorer = new PackageExplorer();
		pkgExplorer.open();
	}
	
	@After
	public void teardown(){
		pkgExplorer.open();
		pkgExplorer.deleteItem("Test", true);
	}
	
	@Test
	public void getAllErrorsNoErrorsNoWarnings(){
		problemsView.open();
		Bot.get().sleep(10000);
		assertEquals("Errors node should be empty", problemsView.getAllErrors().size(), 0);
	}
	
	@Test
	public void getAllErrorsNoErrorsWithWarnings(){
		createError(false, true);
		assertEquals("Errors node should be empty", problemsView.getAllErrors().size(), 0);
	}
	
	@Test
	public void getAllErrorsWithErrorsNoWarnings(){
		createError(true, false);
		assertEquals("There should be one record in Errors", problemsView.getAllErrors().size(), 1);
	}
	
	@Test
	public void getAllWarningsNoWarningsNoErrors(){
		createError(false, false);
		assertEquals("There should be no record in Warnings", problemsView.getAllWarnings().size(), 0);
	}
	
	@Test
	public void getAllWarningsWithWarningsNoErrors(){
		createError(false, true);
		assertEquals("There should be one record in Warnings", problemsView.getAllWarnings().size(), 1);
	}
	
	@Test
	public void getAllWarningsNoWarningsWithErrors(){
		createError(true, false);
		assertEquals("There should no record in Warnings", problemsView.getAllWarnings().size(), 0);
	}
	
	private void createError(boolean error, boolean warning){
		pkgExplorer.open();
		pkgExplorer.selectItem("Test", "src");
		NewJavaClassDialog newJavaClassDialog = new NewJavaClassDialog();
		newJavaClassDialog.open();
		newJavaClassDialog.setName("TestClass");
		newJavaClassDialog.finish();
		new WaitUntilCondition(new AllRunningJobsAreNotActive(), 30000);
		if (error){
			Bot.get().activeEditor().toTextEditor().insertText(2, 1, "test error;\n"); //this should generate error
		}
		if (warning){
			Bot.get().activeEditor().toTextEditor().insertText(2, 1, "public void test(){String test;}\n"); //this should generate warning
		}
		Bot.get().activeEditor().save();
		problemsView.open();
		new WaitUntilCondition(new AllRunningJobsAreNotActive(), 30000);
		Bot.get().sleep(1000);
	}
}
