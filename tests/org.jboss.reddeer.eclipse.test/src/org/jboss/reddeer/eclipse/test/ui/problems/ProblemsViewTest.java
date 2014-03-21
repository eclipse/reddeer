package org.jboss.reddeer.eclipse.test.ui.problems;

import static org.junit.Assert.assertEquals;

import org.jboss.reddeer.eclipse.condition.ProblemsExists;
import org.jboss.reddeer.eclipse.condition.ProblemsExists.ProblemType;
import org.jboss.reddeer.eclipse.jdt.ui.NewJavaClassWizardDialog;
import org.jboss.reddeer.eclipse.jdt.ui.NewJavaClassWizardPage;
import org.jboss.reddeer.eclipse.jdt.ui.ide.NewJavaProjectWizardDialog;
import org.jboss.reddeer.eclipse.jdt.ui.ide.NewJavaProjectWizardPage;
import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.PackageExplorer;
import org.jboss.reddeer.eclipse.ui.problems.ProblemsView;
import org.jboss.reddeer.swt.condition.JobIsRunning;
import org.jboss.reddeer.swt.exception.WaitTimeoutExpiredException;
import org.jboss.reddeer.swt.test.RedDeerTest;
import org.jboss.reddeer.swt.wait.TimePeriod;
import org.jboss.reddeer.swt.wait.WaitUntil;
import org.jboss.reddeer.swt.wait.WaitWhile;
import org.jboss.reddeer.workbench.editor.TextEditor;
import org.junit.Test;

/**
 * Just a draft.
 * 
 * @author rhopp
 * @author jjankovi
 *
 */

public class ProblemsViewTest extends RedDeerTest{
	
	private PackageExplorer pkgExplorer;
	private ProblemsView problemsView;	
	
	@Override
	protected void setUp(){
	  super.setUp();
		NewJavaProjectWizardDialog dialog = new NewJavaProjectWizardDialog();
		dialog.open();
		NewJavaProjectWizardPage page1 = dialog.getFirstPage(); 
		page1.setProjectName("Test");
		dialog.finish();
		problemsView = new ProblemsView();
		problemsView.open();
		pkgExplorer = new PackageExplorer();
		pkgExplorer.open();
	}
	
	@Override
	protected void tearDown(){
		pkgExplorer.open();
		pkgExplorer.getProject("Test").delete(true);
		super.tearDown();
	}
	
	@Test(expected=WaitTimeoutExpiredException.class)
	public void testNoErrorNoWarning() {
		problemsView.open();
		new WaitUntil(new ProblemsExists(), TimePeriod.NORMAL);
	}
	
	@Test
	public void testOneErrorNoWarning() {
		createError();
		new WaitUntil(new ProblemsExists(ProblemType.ERROR), TimePeriod.NORMAL);
		assertEquals("Errors node should contain one error", problemsView.getAllErrors().size(), 1);
		assertEquals("Warnings node should be empty", problemsView.getAllWarnings().size(), 0);
	}
	
	@Test
	public void testNoErrorOneWarning() {
		createWarning();
		new WaitWhile(new ProblemsExists(ProblemType.ERROR), TimePeriod.NORMAL);
		assertEquals("Errors node should be empty", problemsView.getAllErrors().size(), 0);
		assertEquals("Warnings node should contain one warning", problemsView.getAllWarnings().size(), 1);
	}
	
	@Test
	public void testOneErrorOneWarning() {
		createError();
		createWarning();
		new WaitUntil(new ProblemsExists(ProblemType.BOTH), TimePeriod.NORMAL);
		assertEquals("Errors node should contain one error", problemsView.getAllErrors().size(), 1);
		assertEquals("Warnings node should contain one warning", problemsView.getAllWarnings().size(), 1);
	}
	
	private void createError() {
		createProblem(true);
	}
	
	private void createWarning() {
		createProblem(false);
	}
	
	private void createProblem(boolean error){
		pkgExplorer.open();
		pkgExplorer.getProject("Test").getProjectItem("src").select();
		NewJavaClassWizardDialog newJavaClassDialog = new NewJavaClassWizardDialog();
		newJavaClassDialog.open();
		
		NewJavaClassWizardPage wizardPage = newJavaClassDialog.getFirstPage();
		final String newClassName = error ? "ErrorTestClass" : "WarningTestClass";
		wizardPage.setName(newClassName);
		newJavaClassDialog.finish();
		new WaitWhile(new JobIsRunning(), TimePeriod.LONG);
		StringBuilder sbJavaCode = new StringBuilder("");
		if (error){
			sbJavaCode.append("testError\n");
			sbJavaCode.append("public class ");
			sbJavaCode.append(newClassName);
			sbJavaCode.append("{\n");
			sbJavaCode.append("}\n");
		}
		else {
			sbJavaCode.append("public class ");
			sbJavaCode.append(newClassName);
			sbJavaCode.append("{\n");
			sbJavaCode.append("  private int i;\n");
			sbJavaCode.append("}\n");		
		}
		TextEditor textEditor = new TextEditor();		
		textEditor.setText(sbJavaCode.toString());
		textEditor.save();
		
		problemsView.open();
		new WaitUntil(new ProblemsExists(), TimePeriod.NORMAL);
	}
}
