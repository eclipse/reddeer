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
package org.jboss.reddeer.eclipse.test.m2e.core.ui.wizard;

import static org.jboss.reddeer.eclipse.m2e.core.ui.wizard.MavenImportWizard.importProject;

import java.io.File;
import java.util.List;

import org.jboss.reddeer.eclipse.core.resources.Project;
import org.jboss.reddeer.eclipse.jdt.ui.ProjectExplorer;
import org.jboss.reddeer.eclipse.test.Activator;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * 
 * @author apodhrad
 *
 */
@RunWith(RedDeerSuite.class)
public class MavenImportWizardTest {

	@Test
	public void testImportingMavenProject() {
		String projectName = "maven-test-project";

		importProject(getPath(projectName));
		assertProject(projectName);
	}

	@Test
	public void testImportingWrongMavenProject() {
		String projectName = "maven-wrong-project";

		importProject(getPath(projectName));
		assertProject(projectName);
	}

	private String getPath(String projectName) {
		File file = new File(Activator.getTestResourcesLocation(MavenImportWizardTest.class), projectName);
		return file.getAbsolutePath();
	}

	private void assertProject(String projectName) {
		ProjectExplorer pe = new ProjectExplorer();
		pe.open();
		List<Project> projects = pe.getProjects();
		for (Project project : projects) {
			if (project.getName().equals(projectName)) {
				return;
			}
		}
		Assert.fail("Project '" + projectName + "' wasn't created properly");
	}
}
