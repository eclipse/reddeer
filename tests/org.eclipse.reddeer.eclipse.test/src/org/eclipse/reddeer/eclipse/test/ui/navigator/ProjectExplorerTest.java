/*******************************************************************************
 * Copyright (c) 2017-2019 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.eclipse.test.ui.navigator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.eclipse.exception.EclipseLayerException;
import org.eclipse.reddeer.eclipse.ui.navigator.resources.ProjectExplorer;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.workbench.exception.WorkbenchLayerException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class ProjectExplorerTest {

	private ProjectExplorer projectExplorer;
	
	@Before
	public void before() {
		projectExplorer = new ProjectExplorer();
	}
	
	@After
	public void after() {
		if (projectExplorer.isOpen()) {
			projectExplorer.close();
		}
	}
	
	@Test
	public void open() {
		projectExplorer.open();
	}
	
	@Test
	public void getEmptyListOfProjects() {
		projectExplorer.open();
		assertEquals(projectExplorer.getProjects().size(), 0);
	}
	
	@Test
	public void selectAllWithEmptyExplorer() {
		projectExplorer.open();
		try {
			projectExplorer.selectAllProjects();
		} catch (Exception exc) {
			exc.printStackTrace();
			fail("Selecting all projects when there is none should do nothing, not throw an exception: " + exc.getCause() + " " + exc.getMessage());
		}
	}
	
	@Test(expected=EclipseLayerException.class)
	public void selectProjectWithEmptyExplorer() {
		projectExplorer.open();
		projectExplorer.selectProjects("non-existing-project");
	}
	
	@Test(expected=WorkbenchLayerException.class)
	public void getProjectsWhenExplorerIsClosed() {
		if (projectExplorer.isOpen()) {
			projectExplorer.close();
		}
		new ProjectExplorer().getProjects();
	}
	
	@Test
	public void testGetProjectsItemsEmpty() {
		projectExplorer.open();
		assertEquals(projectExplorer.getProjectItems().size(), 0);
	}
	
	@Test
	public void testDeleteAllEmpty() {
		projectExplorer.open();
		projectExplorer.deleteAllProjects();
		projectExplorer.deleteAllProjects(true);
		projectExplorer.deleteAllProjects(false, TimePeriod.MEDIUM);
	}
}
