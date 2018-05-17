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
package org.eclipse.reddeer.eclipse.test.wst.common.project.facet.ui;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import org.eclipse.reddeer.eclipse.jdt.ui.packageview.PackageExplorerPart;
import org.eclipse.reddeer.eclipse.test.wst.server.ui.view.ServersViewTestCase;
import org.eclipse.reddeer.eclipse.ui.dialogs.PropertyDialog;
import org.eclipse.reddeer.eclipse.ui.wizards.datatransfer.ExternalProjectImportWizardDialog;
import org.eclipse.reddeer.eclipse.ui.wizards.datatransfer.WizardProjectsImportPage;
import org.eclipse.reddeer.eclipse.utils.DeleteUtils;
import org.eclipse.reddeer.eclipse.wst.common.project.facet.ui.FacetsPropertyPage;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class FacetsPropertyPageTest {

	private static final String PROJECT = "server-project";
	private static final String FACET1 = "Java";
	private static final String FACET1_VERSION = "1.7";

	@Before
	public void createProject() {
		ExternalProjectImportWizardDialog wizard = new ExternalProjectImportWizardDialog();
		wizard.open();

		WizardProjectsImportPage wizardPage = new WizardProjectsImportPage(wizard);
		wizardPage.setArchiveFile(ServersViewTestCase.ZIP_FILE
				.getAbsolutePath());
		wizardPage.selectProjects(PROJECT);

		wizard.finish();

		PackageExplorerPart packageExplorer = new PackageExplorerPart();
		packageExplorer.open();
	}

	@After
	public void cleanup() {
		PropertyDialog dialog = new PropertyDialog(PROJECT);
		if (dialog.isOpen()) {
			dialog.cancel();
		}
		PackageExplorerPart explorer = new PackageExplorerPart();
		explorer.open();
		DeleteUtils.forceProjectDeletion(explorer.getProject(PROJECT), true);
	}

	@Test
	public void selectFacet() {
		PropertyDialog dialog = new PackageExplorerPart().getProject(PROJECT).openProperties();
		FacetsPropertyPage facetsPage = new FacetsPropertyPage(dialog);
		
		dialog.select(facetsPage);
		facetsPage.selectFacet(FACET1);
		facetsPage.apply();
		dialog.ok();

		dialog = new PackageExplorerPart().getProject(PROJECT).openProperties();
		facetsPage = new FacetsPropertyPage(dialog);
		dialog.select(facetsPage);
		assertThat(facetsPage.getSelectedFacets().get(0).getText(),
				is(FACET1));

		dialog.ok();
	}

	@Test
	public void selectVersion() {
		PropertyDialog dialog = new PackageExplorerPart().getProject(PROJECT).openProperties();
		FacetsPropertyPage facetsPage = new FacetsPropertyPage(dialog);
		
		dialog.select(facetsPage);
		facetsPage.selectFacet(FACET1);
		facetsPage.selectVersion(FACET1, FACET1_VERSION);
		facetsPage.apply();
		dialog.ok();
		
		dialog = new PackageExplorerPart().getProject(PROJECT).openProperties();
		facetsPage = new FacetsPropertyPage(dialog);
		dialog.select(facetsPage);
		assertThat(facetsPage.getSelectedVersion(FACET1), is(FACET1_VERSION));

		dialog.ok();
	}
}