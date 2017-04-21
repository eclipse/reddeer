/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.eclipse.test.m2e.core.ui.wizard;

import static org.eclipse.reddeer.eclipse.m2e.core.ui.wizard.MavenImportWizard.importProject;

import java.io.File;
import java.util.List;

import org.eclipse.reddeer.eclipse.core.resources.DefaultProject;
import org.eclipse.reddeer.eclipse.test.Activator;
import org.eclipse.reddeer.eclipse.ui.navigator.resources.ProjectExplorer;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
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
		List<DefaultProject> projects = pe.getProjects();
		for (DefaultProject project : projects) {
			if (project.getName().equals(projectName)) {
				return;
			}
		}
		Assert.fail("Project '" + projectName + "' wasn't created properly");
	}
}
