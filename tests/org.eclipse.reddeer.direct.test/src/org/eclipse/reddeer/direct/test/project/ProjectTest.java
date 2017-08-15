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
package org.eclipse.reddeer.direct.test.project;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.eclipse.reddeer.direct.project.Project;
import org.eclipse.reddeer.direct.workspace.Workspace;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.requirements.cleanworkspace.CleanWorkspaceRequirement;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test for direct manipulation with a project.
 * 
 * @author Andrej Podhradsky (apodhrad@redhat.com)
 *
 */
@RunWith(RedDeerSuite.class)
public class ProjectTest {

	@Before
	public void CleanWorkspace() {
		new CleanWorkspaceRequirement().fulfill();
	}

	@Test
	public void testGettingLocation() {
		String projectName = "test-location";
		Project.create(projectName);
		assertEquals(new File(Workspace.getLocation(), projectName).getAbsolutePath(),
				Project.getLocation(projectName));
	}

	@Test
	public void testCreatingProject() {
		String projectName = "test-creation";
		Project.create(projectName);
		assertTrue(Project.isProject(projectName));
	}

}
