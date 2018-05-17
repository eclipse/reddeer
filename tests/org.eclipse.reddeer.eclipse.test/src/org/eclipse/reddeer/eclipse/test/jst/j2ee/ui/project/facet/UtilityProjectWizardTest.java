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
package org.eclipse.reddeer.eclipse.test.jst.j2ee.ui.project.facet;

import static org.junit.Assert.*;

import org.eclipse.reddeer.eclipse.jst.j2ee.ui.project.facet.UtilityProjectFirstPage;
import org.eclipse.reddeer.eclipse.jst.j2ee.ui.project.facet.UtilityProjectWizard;
import org.eclipse.reddeer.eclipse.ui.navigator.resources.ProjectExplorer;
import org.eclipse.reddeer.eclipse.utils.DeleteUtils;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class UtilityProjectWizardTest {

	
	private static final String PROJECT_NAME ="UtilityProject";
	
	@After
	public void deleteProject() {
		ProjectExplorer pe = new ProjectExplorer();
		pe.open();
		DeleteUtils.forceProjectDeletion(pe.getProject(PROJECT_NAME), true);
	}
	
	@Test
	public void createProject(){
		UtilityProjectWizard uw = new UtilityProjectWizard();
		uw.open();
		UtilityProjectFirstPage up = new UtilityProjectFirstPage(uw);
		up.setProjectName(PROJECT_NAME);
		uw.finish();
		ProjectExplorer pe = new ProjectExplorer();
		pe.open();
		assertTrue(pe.containsProject(PROJECT_NAME));
	}
}
