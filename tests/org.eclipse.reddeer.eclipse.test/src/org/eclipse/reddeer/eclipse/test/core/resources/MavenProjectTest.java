/*******************************************************************************
 * Copyright (c) 2017, 2018 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/

package org.eclipse.reddeer.eclipse.test.core.resources;

import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.eclipse.core.resources.MavenProject;
import org.eclipse.reddeer.eclipse.m2e.core.ui.wizard.MavenProjectWizard;
import org.eclipse.reddeer.eclipse.m2e.core.ui.wizard.MavenProjectWizardArchetypeParametersPage;
import org.eclipse.reddeer.eclipse.m2e.core.ui.wizard.MavenProjectWizardPage;
import org.eclipse.reddeer.eclipse.test.debug.core.DebuggerTest;
import org.eclipse.reddeer.eclipse.ui.navigator.resources.ProjectExplorer;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.swt.api.TreeItem;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class MavenProjectTest {

	private static final String GROUP_ID = "mavenProjectTest";
	private static final String ARTEFACT_ID = "mavenProjectTest";
	
	@BeforeClass
	public static void createProject() {
		clean();
		MavenProjectWizard mavenProjectWizard = new MavenProjectWizard();
		mavenProjectWizard.open();
		MavenProjectWizardPage newMavenProjectPage = new MavenProjectWizardPage(mavenProjectWizard);
		newMavenProjectPage.createSimpleProject(true);
		mavenProjectWizard.next();
		MavenProjectWizardArchetypeParametersPage artifactPage = new MavenProjectWizardArchetypeParametersPage(mavenProjectWizard);
		artifactPage.setGroupId(GROUP_ID);
		artifactPage.setArtifactId(ARTEFACT_ID);
		mavenProjectWizard.finish(TimePeriod.VERY_LONG);
	}
	
	@Test
	public void updateMavenProject(){
		ProjectExplorer pe = new ProjectExplorer();
		TreeItem projectItem = pe.getProject(ARTEFACT_ID).getTreeItem();
		MavenProject mavenProject = new MavenProject(projectItem);
		mavenProject.updateMavenProject();
	}
	
	@AfterClass
	public static void clean() {
		ProjectExplorer pe = new ProjectExplorer();
		pe.open();
		pe.deleteAllProjects(true);	
	}
}
