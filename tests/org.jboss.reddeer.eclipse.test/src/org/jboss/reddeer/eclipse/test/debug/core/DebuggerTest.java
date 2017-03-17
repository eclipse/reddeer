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
package org.jboss.reddeer.eclipse.test.debug.core;

import static org.jboss.reddeer.eclipse.test.Activator.getTestResourcesLocation;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.File;

import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.eclipse.core.resources.Project;
import org.jboss.reddeer.eclipse.core.resources.ProjectItem;
import org.jboss.reddeer.eclipse.debug.core.Breakpoint;
import org.jboss.reddeer.eclipse.debug.core.BreakpointsView;
import org.jboss.reddeer.eclipse.debug.core.DebugView;
import org.jboss.reddeer.eclipse.debug.core.IsSuspended;
import org.jboss.reddeer.eclipse.debug.core.IsTerminated;
import org.jboss.reddeer.eclipse.debug.core.ResumeButton;
import org.jboss.reddeer.eclipse.debug.core.VariablesView;
import org.jboss.reddeer.eclipse.jdt.ui.ProjectExplorer;
import org.jboss.reddeer.eclipse.m2e.core.ui.wizard.MavenProjectWizard;
import org.jboss.reddeer.eclipse.m2e.core.ui.wizard.MavenProjectWizardArchetypePage;
import org.jboss.reddeer.eclipse.m2e.core.ui.wizard.MavenProjectWizardArchetypeParametersPage;
import org.jboss.reddeer.eclipse.ui.perspectives.DebugPerspective;
import org.jboss.reddeer.eclipse.utils.DeleteUtils;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.requirements.openperspective.OpenPerspectiveRequirement.OpenPerspective;
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
		MavenProjectWizardArchetypePage archetypePage = new MavenProjectWizardArchetypePage();
		archetypePage.selectArchetype("org.apache.maven.archetypes", "maven-archetype-quickstart", "1.1");
		mavenProjectWizard.next();
		MavenProjectWizardArchetypeParametersPage artifactPage = new MavenProjectWizardArchetypeParametersPage();
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

		new WaitUntil(new IsSuspended());
		Assert.assertEquals("AppTest.suite() line: 28", new DebugView().getSelectedText());
		new ResumeButton().click();
		new WaitUntil(new IsSuspended());
		assertEquals("AppTest.testApp() line: 36", new DebugView().getSelectedText());
		new DebugView().getSelectedItem().select();
		assertEquals("testApp(com.example.debugger.AppTest)", new VariablesView().getValue("this"));
		new ResumeButton().click();
		new WaitUntil(new IsTerminated());
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

		new WaitUntil(new IsSuspended());
		assertEquals("AppTest.suite() line: 28", new DebugView().getSelectedText());
		new ResumeButton().click();
		new WaitUntil(new IsTerminated());
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

		new WaitUntil(new IsSuspended());
		assertEquals("AppTest.testApp() line: 36", new DebugView().getSelectedText());
		new ResumeButton().click();
		new WaitUntil(new IsTerminated());
	}

}
