/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.eclipse.test.debug.core;

import static org.eclipse.reddeer.eclipse.test.Activator.getTestResourcesLocation;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.File;

import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.eclipse.condition.LaunchIsSuspended;
import org.eclipse.reddeer.eclipse.condition.LaunchIsTerminated;
import org.eclipse.reddeer.eclipse.core.resources.Project;
import org.eclipse.reddeer.eclipse.core.resources.ProjectItem;
import org.eclipse.reddeer.eclipse.debug.ui.views.breakpoints.Breakpoint;
import org.eclipse.reddeer.eclipse.debug.ui.views.breakpoints.BreakpointsView;
import org.eclipse.reddeer.eclipse.debug.ui.views.launch.LaunchView;
import org.eclipse.reddeer.eclipse.debug.ui.views.launch.ResumeButton;
import org.eclipse.reddeer.eclipse.debug.ui.views.variables.VariablesView;
import org.eclipse.reddeer.eclipse.m2e.core.ui.wizard.MavenProjectWizard;
import org.eclipse.reddeer.eclipse.m2e.core.ui.wizard.MavenProjectWizardArchetypePage;
import org.eclipse.reddeer.eclipse.m2e.core.ui.wizard.MavenProjectWizardArchetypeParametersPage;
import org.eclipse.reddeer.eclipse.ui.navigator.resources.ProjectExplorer;
import org.eclipse.reddeer.eclipse.ui.perspectives.DebugPerspective;
import org.eclipse.reddeer.eclipse.utils.DeleteUtils;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.requirements.openperspective.OpenPerspectiveRequirement.OpenPerspective;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test for debugger manipulation.
 * 
 * @author Andrej Podhradsky
 *
 */
@OpenPerspective(DebugPerspective.class)
@RunWith(RedDeerSuite.class)
public class DebuggerTest {

	public static final File BREAKPOINST_FILE = new File(getTestResourcesLocation(DebuggerTest.class), "AppTest.bkpt");

	public static final String BREAKPOINT_1 = "AppTest [line: 28]";
	public static final String BREAKPOINT_2 = "AppTest [line: 36]";
	public static final ProjectExplorer projectExplorer = new ProjectExplorer();

	@BeforeClass
	public static void createProject() {
		DebuggerTest.projectExplorer.open();
		DebuggerTest.projectExplorer.deleteAllProjects(true);
		MavenProjectWizard mavenProjectWizard = new MavenProjectWizard();
		mavenProjectWizard.open();
		mavenProjectWizard.next();
		MavenProjectWizardArchetypePage archetypePage = new MavenProjectWizardArchetypePage(mavenProjectWizard);
		archetypePage.selectArchetype("org.apache.maven.archetypes", "maven-archetype-quickstart", "1.1");
		mavenProjectWizard.next();
		MavenProjectWizardArchetypeParametersPage artifactPage = new MavenProjectWizardArchetypeParametersPage(mavenProjectWizard);
		artifactPage.setGroupId("com.example");
		artifactPage.setArtifactId("debugger");
		mavenProjectWizard.finish(TimePeriod.VERY_LONG);
	}

	@Before
	public void setUp(){
		removeAllBreakpoints();
	}
	
	@After
	public void tearDown(){
		removeAllBreakpoints();
	}
	@AfterClass
	public static void removeProjext(){
		DebuggerTest.projectExplorer.open();
		DeleteUtils.forceProjectDeletion(DebuggerTest.projectExplorer.getProject("debugger"), true);
	}
	
	private void removeAllBreakpoints() {
		new BreakpointsView().removeAllBreakpoints();
	}

	@Test
	public void debugWithBothBreakpointsTest() {
		BreakpointsView breakpointsView = new BreakpointsView();
		breakpointsView.importBreakpoints(BREAKPOINST_FILE.getAbsolutePath());
		Assert.assertTrue(breakpointsView.isBreakpointAvailable(BREAKPOINT_1));
		Assert.assertTrue(breakpointsView.isBreakpointAvailable(BREAKPOINT_2));

		DebuggerTest.projectExplorer.open();
		Project appProject = new ProjectExplorer().getProject("debugger");
		ProjectItem appTest = appProject.getProjectItem("src/test/java", "com.example.debugger", "AppTest.java");
		appTest.debugAs("JUnit Test");

		new WaitUntil(new LaunchIsSuspended());
		Assert.assertEquals("AppTest.suite() line: 28", new LaunchView().getSelectedText());
		new ResumeButton().click();
		new WaitUntil(new LaunchIsSuspended());
		assertEquals("AppTest.testApp() line: 36", new LaunchView().getSelectedText());
		new LaunchView().getSelectedItem().select();
		assertEquals("testApp(com.example.debugger.AppTest)", new VariablesView().getValue("this"));
		new ResumeButton().click();
		new WaitUntil(new LaunchIsTerminated());
	}

	@Test
	public void debugWithFirstBreakpointTest() {
		BreakpointsView breakpointsView = new BreakpointsView();
		breakpointsView.importBreakpoints(BREAKPOINST_FILE.getAbsolutePath());
		Assert.assertTrue(breakpointsView.isBreakpointAvailable(BREAKPOINT_1));
		Assert.assertTrue(breakpointsView.isBreakpointAvailable(BREAKPOINT_2));

		Breakpoint breakpoint = breakpointsView.getBreakpoint(BREAKPOINT_2);
		breakpoint.remove();
		assertFalse(breakpointsView.isBreakpointAvailable(BREAKPOINT_2));

		DebuggerTest.projectExplorer.open();
		Project appProject = new ProjectExplorer().getProject("debugger");
		ProjectItem appTest = appProject.getProjectItem("src/test/java", "com.example.debugger", "AppTest.java");
		appTest.debugAs("JUnit Test");

		new WaitUntil(new LaunchIsSuspended());
		assertEquals("AppTest.suite() line: 28", new LaunchView().getSelectedText());
		new ResumeButton().click();
		new WaitUntil(new LaunchIsTerminated());
	}

	@Test
	public void debugWithSecondBreakpointTest() {
		BreakpointsView breakpointsView = new BreakpointsView();
		breakpointsView.importBreakpoints(BREAKPOINST_FILE.getAbsolutePath());
		Assert.assertTrue(breakpointsView.isBreakpointAvailable(BREAKPOINT_1));
		Assert.assertTrue(breakpointsView.isBreakpointAvailable(BREAKPOINT_2));

		Breakpoint breakpoint = breakpointsView.getBreakpoint(BREAKPOINT_1);
		breakpoint.disable();
		assertFalse(breakpoint.isEnabled());
		assertFalse(breakpoint.isChecked());

		DebuggerTest.projectExplorer.open();
		Project appProject = new ProjectExplorer().getProject("debugger");
		ProjectItem appTest = appProject.getProjectItem("src/test/java", "com.example.debugger", "AppTest.java");
		appTest.debugAs("JUnit Test");

		new WaitUntil(new LaunchIsSuspended());
		assertEquals("AppTest.testApp() line: 36", new LaunchView().getSelectedText());
		new ResumeButton().click();
		new WaitUntil(new LaunchIsTerminated());
	}

}
