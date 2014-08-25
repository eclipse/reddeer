package org.jboss.reddeer.eclipse.test.ui.problems;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.hamcrest.core.Is;
import org.hamcrest.core.StringStartsWith;
import org.jboss.reddeer.eclipse.condition.ProblemsExists;
import org.jboss.reddeer.eclipse.condition.ProblemsExists.ProblemType;
import org.jboss.reddeer.eclipse.jdt.ui.NewJavaClassWizardDialog;
import org.jboss.reddeer.eclipse.jdt.ui.NewJavaClassWizardPage;
import org.jboss.reddeer.eclipse.jdt.ui.ide.NewJavaProjectWizardDialog;
import org.jboss.reddeer.eclipse.jdt.ui.ide.NewJavaProjectWizardPage;
import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.PackageExplorer;
import org.jboss.reddeer.eclipse.ui.problems.ProblemsView;
import org.jboss.reddeer.eclipse.utils.DeleteUtils;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.condition.JobIsRunning;
import org.jboss.reddeer.swt.wait.TimePeriod;
import org.jboss.reddeer.swt.wait.WaitUntil;
import org.jboss.reddeer.swt.wait.WaitWhile;
import org.jboss.reddeer.workbench.impl.editor.TextEditor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Testing {@link ProblemsView}
 * Just a draft.
 * 
 * @author rhopp
 * @author jjankovi
 * @author Radoslav Rabara
 */
@RunWith(RedDeerSuite.class)
public class ProblemsViewTest {

	private PackageExplorer pkgExplorer;
	private ProblemsView problemsView;	

	private static final String JAVA_PROBLEM = "Java Problem";
	private static final String PROJECT_NAME = "ProblemsViewTestProject";

	private static final String DEFAULT_ERROR_CLASS_NAME = "ErrorTestClass";
	private static final String ERROR_DESCRIPTION =
			"Syntax error on token \"testError\", delete this token";
	private static final String ERROR_LOCATION = "line 1";

	private static final String DEFAULT_WARNING_CLASS_NAME = "WarningTestClass";
	private static final String WARNING_LOCATION = "line 2";

	@Before
	public void setUp() {
		NewJavaProjectWizardDialog dialog = new NewJavaProjectWizardDialog();
		dialog.open();
		NewJavaProjectWizardPage page1 = dialog.getFirstPage(); 
		page1.setProjectName(PROJECT_NAME);
		dialog.finish();
		problemsView = new ProblemsView();
		problemsView.open();
		pkgExplorer = new PackageExplorer();
		pkgExplorer.open();
	}
	
	@After
	public void tearDown() {
		pkgExplorer.open();
		DeleteUtils.forceProjectDeletion(pkgExplorer.getProject(PROJECT_NAME),true);
	}
	
	@Test
	public void testNoErrorNoWarning() {
		problemsView.open();
		new WaitUntil(new ProblemsExists(ProblemType.NONE), TimePeriod.NORMAL);
		assertTrue("Errors node should be empty, but:\n" + getProblems(),
				problemsView.getAllErrors().isEmpty());
		assertTrue("Warnings node should be empty, but:\n" + getProblems(),
				problemsView.getAllWarnings().isEmpty());
	}
	
	@Test
	public void testOneErrorNoWarning() {
		createError();
		new WaitUntil(new ProblemsExists(ProblemType.ERROR), TimePeriod.NORMAL);
		new WaitWhile(new ProblemsExists(ProblemType.WARNING),
				TimePeriod.NORMAL);
		assertEquals("Errors node should contain one error, but:\n"
		+ getProblems(), 1, problemsView.getAllErrors().size());
		assertTrue("Warnings node should be empty, but:\n" + getProblems(),
				problemsView.getAllWarnings().isEmpty());
	}
	
	@Test
	public void testNoErrorOneWarning() {
		createWarning();
		new WaitWhile(new ProblemsExists(ProblemType.ERROR), TimePeriod.NORMAL);
		new WaitUntil(new ProblemsExists(ProblemType.WARNING),
				TimePeriod.NORMAL);
		assertTrue("Errors node should be empty, but:\n" + getProblems(),
				problemsView.getAllErrors().isEmpty());
		assertEquals("Warnings node should contain one warning, but:\n"
				+ getProblems(), 1, problemsView.getAllWarnings().size());
	}
	
	@Test
	public void testOneErrorOneWarning() {
		createError();
		createWarning();
		new WaitUntil(new ProblemsExists(ProblemType.BOTH), TimePeriod.NORMAL);
		assertEquals("Errors node should contain one error, but:\n"
		+ getProblems(), 1, problemsView.getAllErrors().size());
		assertEquals("Warnings node should contain one warning, but:\n"
		+ getProblems(), 1, problemsView.getAllWarnings().size());
	}
	
	@Test
	public void testFilterErrors() {
		final String projectPath = "/" + PROJECT_NAME + "/" + "src";
		final String resource = DEFAULT_ERROR_CLASS_NAME + ".java";

		/* create 2 errors and 1 warning */
		createError();
		createProblem(true, "NextErrorTestClass");
		createWarning();

		/* get filtered errors */
		new WaitUntil(new ProblemsExists(ProblemType.BOTH), TimePeriod.NORMAL);
		List<TreeItem> errors = new ProblemsView().getErrors(
				Is.is(ERROR_DESCRIPTION), Is.is(resource),
				StringStartsWith.startsWith(projectPath),
				Is.is(ERROR_LOCATION), Is.is(JAVA_PROBLEM));

		assertEquals(1, errors.size());
		TreeItem error = errors.get(0);
		assertEquals("Error description", ERROR_DESCRIPTION, error.getCell(0));
		assertEquals("Error resource", resource, error.getCell(1));
		assertEquals("Error path", projectPath, error.getCell(2));
		assertEquals("Error location", ERROR_LOCATION, error.getCell(3));
		assertEquals("Error type", JAVA_PROBLEM, error.getCell(4));

		/* verify filtered java problem errors with the specified description */
		errors = problemsView.getErrors(Is.is(ERROR_DESCRIPTION), null, null,
				null, Is.is(JAVA_PROBLEM));
		assertEquals("Errors node should contain two " + JAVA_PROBLEM
				+ " errors with description equal to \"" + ERROR_DESCRIPTION
				+ "\", but:\n" + getProblems(), 2, errors.size());
	}

	@Test
	public void testFilterWarnings() {
		final String warningDescription = "The value of the field "
				+ DEFAULT_WARNING_CLASS_NAME + ".i is not used";
		final String projectPath = "/" + PROJECT_NAME + "/" + "src";
		final String resource = DEFAULT_WARNING_CLASS_NAME + ".java";

		/* create 1 error and 2 warnings */
		createWarning();
		createProblem(false, "NextWarningTestClass");
		createError();

		/* get filtered warnings */
		new WaitUntil(new ProblemsExists(ProblemType.BOTH), TimePeriod.NORMAL);
		List<TreeItem> warnings = new ProblemsView().getWarnings(
				Is.is(warningDescription), Is.is(resource),
				StringStartsWith.startsWith(projectPath),
				Is.is(WARNING_LOCATION), Is.is(JAVA_PROBLEM));

		assertEquals(1, warnings.size());
		TreeItem warning = warnings.get(0);
		assertEquals("Warning description", warningDescription,
				warning.getCell(0));
		assertEquals("Warning resource", resource, warning.getCell(1));
		assertEquals("Warning path", projectPath, warning.getCell(2));
		assertEquals("Warning location", WARNING_LOCATION, warning.getCell(3));
		assertEquals("Warning type", JAVA_PROBLEM, warning.getCell(4));

		/* verify filtered warnings in the specified location of the specified project*/
		warnings = problemsView.getWarnings(null, null,
				StringStartsWith.startsWith(projectPath),
				Is.is(WARNING_LOCATION), null);
		assertEquals("Warnings node should contain two " + JAVA_PROBLEM
				+ " warnings in the location \"" + WARNING_LOCATION
				+ "\" of the project with path \"" + projectPath
				+ "\", but:\n" + getProblems(), 2, warnings.size());
	}

	private void createError() {
		createProblem(true);
	}

	private void createWarning() {
		createProblem(false);
	}

	private void createProblem(boolean error, final String newClassName) {
		pkgExplorer.open();
		pkgExplorer.getProject(PROJECT_NAME).getProjectItem("src").select();
		NewJavaClassWizardDialog newJavaClassDialog =
				new NewJavaClassWizardDialog();
		newJavaClassDialog.open();
		
		NewJavaClassWizardPage wizardPage = newJavaClassDialog.getFirstPage();
		wizardPage.setName(newClassName);
		newJavaClassDialog.finish();
		new WaitWhile(new JobIsRunning(), TimePeriod.LONG);
		StringBuilder sbJavaCode = new StringBuilder("");
		if (error) { //see ERROR_DESCRIPTION and ERROR_LOCATION
			sbJavaCode.append("testError\n");
			sbJavaCode.append("public class ");
			sbJavaCode.append(newClassName);
			sbJavaCode.append("{\n");
			sbJavaCode.append("}\n");
		} else { //creates Java Problem warning "The value of the field "+newClassName+".i is not used"
			sbJavaCode.append("public class ");
			sbJavaCode.append(newClassName);
			sbJavaCode.append("{\n");
			sbJavaCode.append("  private int i;\n"); //see WARNING_LOCATION
			sbJavaCode.append("}\n");		
		}
		TextEditor textEditor = new TextEditor();		
		textEditor.setText(sbJavaCode.toString());
		textEditor.save();
		
		problemsView.open();
		new WaitUntil(new ProblemsExists(), TimePeriod.NORMAL);
	}

	private void createProblem(boolean error) {
		createProblem(error, (error ? DEFAULT_ERROR_CLASS_NAME
				: DEFAULT_WARNING_CLASS_NAME));
	}

	private String getProblems() {
		StringBuilder builder = new StringBuilder();
		builder.append("Errors:\n");
		for (TreeItem item : problemsView.getAllErrors()) {
			builder.append("\t");
			builder.append(item.getText());
			builder.append("\n");
		}
		builder.append("Warnings:\n");
		for (TreeItem item : problemsView.getAllWarnings()) {
			builder.append("\t");
			builder.append(item.getText());
			builder.append("\n");
		}
		return builder.toString();
	}
}
