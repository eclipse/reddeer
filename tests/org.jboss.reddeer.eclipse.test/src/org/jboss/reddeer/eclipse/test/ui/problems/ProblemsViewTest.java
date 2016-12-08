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
package org.jboss.reddeer.eclipse.test.ui.problems;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.core.Is;
import org.hamcrest.core.StringStartsWith;
import org.jboss.reddeer.common.exception.WaitTimeoutExpiredException;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.core.condition.JobIsRunning;
import org.jboss.reddeer.eclipse.condition.ExactNumberOfProblemsExists;
import org.jboss.reddeer.eclipse.condition.ProblemExists;
import org.jboss.reddeer.eclipse.condition.ProblemsViewIsEmpty;
import org.jboss.reddeer.eclipse.jdt.ui.NewJavaClassWizardDialog;
import org.jboss.reddeer.eclipse.jdt.ui.NewJavaClassWizardPage;
import org.jboss.reddeer.eclipse.jdt.ui.ide.NewJavaProjectWizardDialog;
import org.jboss.reddeer.eclipse.jdt.ui.ide.NewJavaProjectWizardPage;
import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.PackageExplorer;
import org.jboss.reddeer.eclipse.ui.markers.matcher.MarkerDescriptionMatcher;
import org.jboss.reddeer.eclipse.ui.markers.matcher.MarkerLocationMatcher;
import org.jboss.reddeer.eclipse.ui.markers.matcher.MarkerPathMatcher;
import org.jboss.reddeer.eclipse.ui.markers.matcher.MarkerResourceMatcher;
import org.jboss.reddeer.eclipse.ui.markers.matcher.MarkerTypeMatcher;
import org.jboss.reddeer.eclipse.ui.problems.Problem;
import org.jboss.reddeer.eclipse.ui.views.AbstractMarkersSupportView.Column;
import org.jboss.reddeer.eclipse.ui.views.ProblemsView;
import org.jboss.reddeer.eclipse.ui.views.ProblemsView.ProblemType;
import org.jboss.reddeer.eclipse.ui.views.markers.QuickFixPage;
import org.jboss.reddeer.eclipse.ui.views.markers.QuickFixWizard;
import org.jboss.reddeer.eclipse.utils.DeleteUtils;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.requirements.cleanworkspace.CleanWorkspaceRequirement.CleanWorkspace;
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
 * @author mlabuda@redhat.com
 */
@RunWith(RedDeerSuite.class)
@CleanWorkspace
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
		NewJavaProjectWizardPage page1 = new NewJavaProjectWizardPage(); 
		page1.setProjectName(PROJECT_NAME);
		dialog.finish();
		problemsView = new ProblemsView();
		problemsView.open();
		pkgExplorer = new PackageExplorer();
		pkgExplorer.open();
	}
	
	@After
	public void tearDown() {
		problemsView.showDefaultProblemColumns();
		pkgExplorer.open();
		DeleteUtils.forceProjectDeletion(pkgExplorer.getProject(PROJECT_NAME),true);
	}
	
	@Test
	public void testShowProblemColumns() {
		problemsView.showDefaultProblemColumns();
	
		assertFalse("ID column should not be shown at this point, but it is.",
				problemsView.getProblemColumns().contains(Column.ID.toString()));
		assertFalse("Creation Time column should not be shown at this point, but it is.",
				problemsView.getProblemColumns().contains(Column.CREATION_TIME.toString()));
		
		problemsView.showProblemColumns(Column.ID, Column.CREATION_TIME, Column.DESCRIPTION);
		
		assertTrue("ID column should be shown at this point, but it is not.",
				problemsView.getProblemColumns().contains(Column.ID.toString()));
		assertTrue("Creation Time column should be shown at this point, but it is not.",
				problemsView.getProblemColumns().contains(Column.CREATION_TIME.toString()));
	}
	
	@Test
	public void testHideProblemColumns() {
		problemsView.showDefaultProblemColumns();
		
		assertTrue("Resource problem column should be shown at this point, but it is not",
				problemsView.getProblemColumns().contains(Column.RESOURCE.toString()));
		
		problemsView.hideProblemColumn(Column.RESOURCE, Column.LOCATION, Column.ID);
		
		assertFalse("Resource problem column should be hidden at this point, but it is not.",
				problemsView.getProblemColumns().contains(Column.RESOURCE.toString()));
		assertFalse("Location problem column should be hidden at this point, but it is not.",
				problemsView.getProblemColumns().contains(Column.LOCATION.toString()));
	}
	
	@Test
	public void testShowProblemColumnNullArgument() {
		problemsView.showProblemColumns((Column[]) null);
		// pass
	}
	
	@Test
	public void testShowProblemColumnEmptyArrayArgument() {
		problemsView.showProblemColumns(new Column[] {});
		// pass
	}
	
	@Test
	public void testShowVisibleProblemColumn() {
		problemsView.showDefaultProblemColumns();
		problemsView.showProblemColumns(Column.DESCRIPTION);
		
		assertTrue("Description problem column should be visible, but it is not.",
				problemsView.getProblemColumns().contains(Column.DESCRIPTION.toString()));
	}
	
	@Test
	public void testHideProblemColumnsNullArgument() {
		problemsView.hideProblemColumn((Column[]) null);
		// pass
	}
	
	@Test
	public void testHideProblemColumnsEmptyArrayArgument() {
		problemsView.hideProblemColumn(new Column[] {});
		// pass
	}
	
	@Test
	public void testHideHiddenProblemColumn() {
		problemsView.showDefaultProblemColumns();
		problemsView.hideProblemColumn(Column.ID);
		
		assertFalse("ID problem column should not be visible, but it is.",
				problemsView.getProblemColumns().contains(Column.ID.toString()));
	}
	
	@Test
	public void testGetProblemColumns() {
		pkgExplorer.open();
		createError();
		List<String> foundColumns = problemsView.getProblemColumns();		
		List<String> requiredColumns = new ArrayList<String>(Arrays.asList(
				new String[] {"Description", "Resource", "Path", "Location", "Type"}));
		assertTrue("Number of retrieved columns should be " + requiredColumns.size() + " but there were found " + 
				foundColumns.size() + " columns.", foundColumns.size() == requiredColumns.size());
		for (int i = 0; i < foundColumns.size(); i++) {
			assertTrue("Found columns was " + foundColumns.get(i) + " but should be " + requiredColumns.get(i),
					foundColumns.get(i).equals(requiredColumns.get(i)));
		}
	}
	
	@Test
	public void testNoErrorNoWarning() {
		problemsView.open();
		new WaitUntil(new ProblemsViewIsEmpty(), TimePeriod.NORMAL);
		assertTrue("Errors node should be empty, but:\n" + getProblems(),
				problemsView.getProblems(ProblemType.ERROR).isEmpty());
		assertTrue("Warnings node should be empty, but:\n" + getProblems(),
				problemsView.getProblems(ProblemType.WARNING).isEmpty());
	}
	
	@Test
	public void testOneErrorNoWarning() {
		createError();		
		new WaitUntil(new ProblemExists(ProblemType.ERROR), TimePeriod.NORMAL);
		new WaitWhile(new ProblemExists(ProblemType.WARNING),
				TimePeriod.NORMAL);
		assertEquals("Errors node should contain one error, but:\n"
		+ getProblems(), 1, problemsView.getProblems(ProblemType.ERROR).size());
		assertTrue("Warnings node should be empty, but:\n" + getProblems(),
				problemsView.getProblems(ProblemType.WARNING).isEmpty());
	}
	
	@Test
	public void testNoErrorOneWarning() {
		createWarning();
		new WaitWhile(new ProblemExists(ProblemType.ERROR), TimePeriod.NORMAL);
		new WaitUntil(new ProblemExists(ProblemType.WARNING),
				TimePeriod.NORMAL);
		assertTrue("Errors node should be empty, but:\n" + getProblems(),
				problemsView.getProblems(ProblemType.ERROR).isEmpty());
		assertEquals("Warnings node should contain one warning, but:\n"
				+ getProblems(), 1, problemsView.getProblems(ProblemType.WARNING).size());
	}
	
	@Test
	public void testOneErrorOneWarning() {
		createError();
		createWarning();
		new WaitUntil(new ProblemExists(ProblemType.ERROR), TimePeriod.NORMAL);
		new WaitUntil(new ProblemExists(ProblemType.WARNING), TimePeriod.NORMAL);
		assertEquals("Errors node should contain one error, but:\n"
		+ getProblems(), 1, problemsView.getProblems(ProblemType.ERROR).size());
		assertEquals("Warnings node should contain one warning, but:\n"
		+ getProblems(), 1, problemsView.getProblems(ProblemType.WARNING).size());
	}
	
	@Test
	public void testOneErrorExists() {
		createError();
		try {
			new WaitUntil(new ExactNumberOfProblemsExists(ProblemType.ERROR, 1), TimePeriod.NORMAL);
		} catch (WaitTimeoutExpiredException ex) {
			fail("Wait condition exact number of problems exists did not pass altough it should. There is "
				+ "following amount of errors: " + problemsView.getProblems(ProblemType.ERROR)
				+ " altough there should be precisely 1 error.");
		}
	}
	
	@Test
	public void testOneWarningExists() {
		createWarning();
		try {
			new WaitUntil(new ExactNumberOfProblemsExists(ProblemType.WARNING, 1), TimePeriod.NORMAL);
		} catch (WaitTimeoutExpiredException ex) {
			fail("Wait condition exact number of problems exists did not pass altough it should. There is "
					+ "following amount of warnings: " + problemsView.getProblems(ProblemType.WARNING)
					+ " altough there should be precisely 1 warning.");
		}
	}
	
	@Test
	public void testTwoProblemsExist() {
		createWarning();
		createError();
		try {
			new WaitUntil(new ExactNumberOfProblemsExists(ProblemType.ALL, 2), TimePeriod.NORMAL);
		} catch (WaitTimeoutExpiredException ex) {
			fail("Wait condition exact number of problems exists did not pass altough it should. There is "
					+ "following amount of problems: " + problemsView.getProblems(ProblemType.ALL) 
					+ " altough there should be precisely 2 problems");
		}
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
		new WaitUntil(new ProblemExists(ProblemType.WARNING), TimePeriod.NORMAL);
		new WaitUntil(new ProblemExists(ProblemType.ERROR), TimePeriod.NORMAL);
		List<Problem> errors = new ProblemsView().getProblems(ProblemType.ERROR,
				new MarkerDescriptionMatcher(Is.is(ERROR_DESCRIPTION)),
				new MarkerResourceMatcher(resource),
				new MarkerPathMatcher(StringStartsWith.startsWith(projectPath)),
				new MarkerLocationMatcher(Is.is(ERROR_LOCATION)),
				new MarkerTypeMatcher(Is.is(JAVA_PROBLEM)));

		assertEquals(1, errors.size());
		Problem error = errors.get(0);
		assertEquals("Error description", ERROR_DESCRIPTION, error.getDescription());
		assertEquals("Error resource", resource, error.getResource());
		assertEquals("Error path", projectPath, error.getPath());
		assertEquals("Error location", ERROR_LOCATION, error.getLocation());
		assertEquals("Error type", JAVA_PROBLEM, error.getType());

		/* verify filtered java problem errors with the specified description */
		errors = problemsView.getProblems(ProblemType.ERROR,
				new MarkerDescriptionMatcher(Is.is(ERROR_DESCRIPTION)),
				new MarkerTypeMatcher(Is.is(JAVA_PROBLEM)));
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
		new WaitUntil(new ProblemExists(ProblemType.WARNING), TimePeriod.NORMAL);
		new WaitUntil(new ProblemExists(ProblemType.ERROR), TimePeriod.NORMAL);
		List<Problem> warnings = new ProblemsView().getProblems(ProblemType.WARNING,
				new MarkerDescriptionMatcher(Is.is(warningDescription)),
				new MarkerResourceMatcher(Is.is(resource)),
				new MarkerPathMatcher(StringStartsWith.startsWith(projectPath)),
				new MarkerLocationMatcher(Is.is(WARNING_LOCATION)),
				new MarkerTypeMatcher(Is.is(JAVA_PROBLEM)));

		assertEquals(1, warnings.size());
		Problem warning = warnings.get(0);
		assertEquals("Warning description", warningDescription,
				warning.getDescription());
		assertEquals("Warning resource", resource, warning.getResource());
		assertEquals("Warning path", projectPath, warning.getPath());
		assertEquals("Warning location", WARNING_LOCATION, warning.getLocation());
		assertEquals("Warning type", JAVA_PROBLEM, warning.getType());

		/* verify filtered warnings in the specified location of the specified project*/
		warnings = problemsView.getProblems(ProblemType.WARNING,
				new MarkerPathMatcher(StringStartsWith.startsWith(projectPath)),
				new MarkerLocationMatcher(Is.is(WARNING_LOCATION)));
		assertEquals("Warnings node should contain two " + JAVA_PROBLEM
				+ " warnings in the location \"" + WARNING_LOCATION
				+ "\" of the project with path \"" + projectPath
				+ "\", but:\n" + getProblems(), 2, warnings.size());
	}
	
	@Test
	public void testProblemQuickfix(){
		createWarning();
		QuickFixWizard qw = new ProblemsView().getProblems(ProblemType.WARNING).get(0).openQuickFix();
		QuickFixPage qp = new QuickFixPage();
		List<String> problems = qp.getAvailableFixes();
		assertTrue(problems.size() == 4);
		qp.selectFix("Add @SuppressWarnings 'unused' to 'i'");
		qw.finish();
		TextEditor te = new TextEditor("WarningTestClass.java");
		assertTrue(te.getText().contains("@SuppressWarnings(\"unused\")"));
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
		
		NewJavaClassWizardPage wizardPage = new NewJavaClassWizardPage();
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
		TextEditor textEditor = new TextEditor(newClassName + ".java");		
		textEditor.setText(sbJavaCode.toString());
		textEditor.save();
		
		problemsView.open();
		if (error) {
			new WaitUntil(new ProblemExists(ProblemType.ERROR), TimePeriod.NORMAL);
		} else {
			new WaitUntil(new ProblemExists(ProblemType.WARNING), TimePeriod.NORMAL);
		}
	}

	private void createProblem(boolean error) {
		createProblem(error, (error ? DEFAULT_ERROR_CLASS_NAME
				: DEFAULT_WARNING_CLASS_NAME));
	}

	private String getProblems() {
		StringBuilder builder = new StringBuilder();
		builder.append("Errors:\n");
		for (Problem problem: problemsView.getProblems(ProblemType.ERROR)) {
			builder.append("\t");
			builder.append(problem);
			builder.append("\n");
		}
		builder.append("Warnings:\n");
		for (Problem problem: problemsView.getProblems(ProblemType.WARNING)) {
			builder.append("\t");
			builder.append(problem);
			builder.append("\n");
		}
		return builder.toString();
	}
}
