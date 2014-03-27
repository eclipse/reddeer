package org.jboss.reddeer.eclipse.test.ui.problems;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.jboss.reddeer.eclipse.condition.ProblemsExists;
import org.jboss.reddeer.eclipse.condition.ProblemsExists.ProblemType;
import org.jboss.reddeer.eclipse.jdt.ui.NewJavaClassWizardDialog;
import org.jboss.reddeer.eclipse.jdt.ui.NewJavaClassWizardPage;
import org.jboss.reddeer.eclipse.jdt.ui.ide.NewJavaProjectWizardDialog;
import org.jboss.reddeer.eclipse.jdt.ui.ide.NewJavaProjectWizardPage;
import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.PackageExplorer;
import org.jboss.reddeer.eclipse.ui.problems.ProblemsView;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.condition.JobIsRunning;
import org.jboss.reddeer.swt.test.RedDeerTest;
import org.jboss.reddeer.swt.wait.TimePeriod;
import org.jboss.reddeer.swt.wait.WaitUntil;
import org.jboss.reddeer.swt.wait.WaitWhile;
import org.jboss.reddeer.workbench.impl.editor.TextEditor;
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
	
	public void testNoErrorNoWarning() {
		problemsView.open();
		new WaitUntil(new ProblemsExists(ProblemType.NONE), TimePeriod.NORMAL);
		assertTrue("Errors node should be empty, but:\n" + getProblems(), problemsView.getAllErrors().isEmpty());
		assertTrue("Warnings node should be empty, but:\n" + getProblems(), problemsView.getAllWarnings().isEmpty());
	}
	
	@Test
	public void testOneErrorNoWarning() {
		createError();
		new WaitUntil(new ProblemsExists(ProblemType.ERROR), TimePeriod.NORMAL);
		new WaitWhile(new ProblemsExists(ProblemType.WARNING), TimePeriod.NORMAL);
		assertEquals("Errors node should contain one error, but:\n" + getProblems(), 1, problemsView.getAllErrors().size());
		assertTrue("Warnings node should be empty, but:\n" + getProblems(), problemsView.getAllWarnings().isEmpty());
	}
	
	@Test
	public void testNoErrorOneWarning() {
		createWarning();
		new WaitWhile(new ProblemsExists(ProblemType.ERROR), TimePeriod.NORMAL);
		new WaitUntil(new ProblemsExists(ProblemType.WARNING), TimePeriod.NORMAL);
		assertTrue("Errors node should be empty, but:\n" + getProblems(), problemsView.getAllErrors().isEmpty());
		assertEquals("Warnings node should contain one warning, but:\n" + getProblems(), 1, problemsView.getAllWarnings().size());
	}
	
	@Test
	public void testOneErrorOneWarning() {
		createError();
		createWarning();
		new WaitUntil(new ProblemsExists(ProblemType.BOTH), TimePeriod.NORMAL);
		assertEquals("Errors node should contain one error, but:\n" + getProblems(), 1, problemsView.getAllErrors().size());
		assertEquals("Warnings node should contain one warning, but:\n" + getProblems(), 1, problemsView.getAllWarnings().size());
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
	
	private String getProblems(){
		StringBuilder builder = new StringBuilder();
		builder.append("Errors:\n");
		for (TreeItem item : problemsView.getAllErrors()){
			builder.append("\t");
			builder.append(item.getText());
			builder.append("\n");
		}
		builder.append("Warnings:\n");
		for (TreeItem item : problemsView.getAllWarnings()){
			builder.append("\t");
			builder.append(item.getText());
			builder.append("\n");
		}
		return builder.toString();
	}
}
