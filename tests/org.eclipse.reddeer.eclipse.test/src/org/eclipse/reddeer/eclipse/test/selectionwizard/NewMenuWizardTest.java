/*******************************************************************************
 * Copyright (c) 2018 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.eclipse.test.selectionwizard;

import static org.junit.Assert.assertTrue;

import org.eclipse.reddeer.common.exception.RedDeerException;
import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.common.util.ResultRunnable;
import org.eclipse.reddeer.direct.preferences.PreferencesUtil;
import org.eclipse.reddeer.eclipse.jdt.ui.packageview.PackageExplorerPart;
import org.eclipse.reddeer.eclipse.jst.servlet.ui.project.facet.WebProjectFirstPage;
import org.eclipse.reddeer.eclipse.jst.servlet.ui.project.facet.WebProjectWizard;
import org.eclipse.reddeer.eclipse.ui.perspectives.AbstractPerspective;
import org.eclipse.reddeer.eclipse.ui.perspectives.JavaPerspective;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.workbench.handler.WorkbenchShellHandler;
import org.eclipse.ui.PlatformUI;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * A test class for NewMenuWizardDialog for opening perspectives when creating
 * new projects.
 * 
 * @author jkopriva@redhat.com
 * 
 */
@RunWith(RedDeerSuite.class)
public class NewMenuWizardTest {
	private PackageExplorerPart packageExplorer;
	private String projectName = "webProject";

	@BeforeClass
	public static void setUpPreferences() {
		PreferencesUtil.setOpenAssociatedPerspective("prompt");
	}

	@Before
	public void prepareWorkspace() {
		// open package explorer
		packageExplorer = new PackageExplorerPart();
		packageExplorer.open();

		// open Java Perspective - could be done with requirement, but we need to set up
		// perspective before every test
		AbstractPerspective perspective = new JavaPerspective();
		perspective.open();
	}

	@After
	public void deleteProject() {
		WorkbenchShellHandler.getInstance().closeAllNonWorbenchShells();
		// Need to reopen packageExplorer, otherwise it fails
		// See https://bugs.eclipse.org/bugs/show_bug.cgi?id=473278
		packageExplorer.close();
		packageExplorer.open();
		packageExplorer.deleteAllProjects(true);
	}

	@AfterClass
	public static void cleanUpPreferences() {
		PreferencesUtil.setOpenAssociatedPerspective("never");
	}

	@Test
	public void openPerspective() {
		createProjectAndOpenPerspective(projectName, true);
		assertTrue(checkPerspective().equals("Java EE"));
	}

	@Test
	public void doNotOpenPerspective() {
		createProjectAndOpenPerspective(projectName, false);
		assertTrue(checkPerspective().equals("Java"));
	}

	@Test(expected = RedDeerException.class)
	public void testThrowException() {
		PreferencesUtil.setOpenAssociatedPerspective("never");
		createProjectAndOpenPerspective(projectName, false);
	}

	private void createProjectAndOpenPerspective(String projectName, boolean openPerspective) {
		WebProjectWizard ww = new WebProjectWizard();
		ww.open();
		WebProjectFirstPage fp = new WebProjectFirstPage(ww);
		fp.setProjectName(projectName);
		ww.finish(openPerspective);
	}

	private String checkPerspective() {
		String activePerspectiveLabel = Display.syncExec(new ResultRunnable<String>() {
			@Override
			public String run() {
				return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getPerspective().getLabel();
			}
		});
		return activePerspectiveLabel;
	}

}
