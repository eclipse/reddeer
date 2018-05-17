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
import static org.junit.Assert.assertTrue;

import org.eclipse.reddeer.eclipse.core.resources.DefaultProject;
import org.eclipse.reddeer.eclipse.jdt.ui.packageview.PackageExplorerPart;
import org.eclipse.reddeer.eclipse.test.wst.server.ui.TestServerRuntime;
import org.eclipse.reddeer.eclipse.test.wst.server.ui.view.ServersViewTestCase;
import org.eclipse.reddeer.eclipse.ui.dialogs.PropertyDialog;
import org.eclipse.reddeer.eclipse.ui.wizards.datatransfer.ExternalProjectImportWizardDialog;
import org.eclipse.reddeer.eclipse.ui.wizards.datatransfer.WizardProjectsImportPage;
import org.eclipse.reddeer.eclipse.utils.DeleteUtils;
import org.eclipse.reddeer.eclipse.wst.common.project.facet.ui.RuntimesPropertyPage;
import org.eclipse.reddeer.eclipse.wst.server.ui.RuntimePreferencePage;
import org.eclipse.reddeer.eclipse.wst.server.ui.wizard.NewRuntimeWizardDialog;
import org.eclipse.reddeer.eclipse.wst.server.ui.wizard.NewRuntimeWizardPage;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.workbench.ui.dialogs.WorkbenchPreferenceDialog;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class RuntimesPropertyPageTest {

	private static final String PROJECT = "server-project";
	
	private PropertyDialog dialog;
	
	private DefaultProject project;
	
	@Before
	public void createProject(){
		ExternalProjectImportWizardDialog wizard  = new ExternalProjectImportWizardDialog();
		wizard.open();

		WizardProjectsImportPage wizardPage = new WizardProjectsImportPage(wizard);
		wizardPage.setArchiveFile(ServersViewTestCase.ZIP_FILE.getAbsolutePath());
		wizardPage.selectProjects(PROJECT);

		wizard.finish();
	}
	
	@Before
	public void createRuntime(){
		WorkbenchPreferenceDialog preferencesDialog = new WorkbenchPreferenceDialog();
		RuntimePreferencePage runtimePreference = new RuntimePreferencePage(preferencesDialog);

		preferencesDialog.open();
		preferencesDialog.select(runtimePreference);
		
		NewRuntimeWizardDialog dialog = runtimePreference.addRuntime();
		NewRuntimeWizardPage page = new NewRuntimeWizardPage(dialog);
		page.selectType(TestServerRuntime.CATEGORY, TestServerRuntime.NAME);
		dialog.finish();
		
		preferencesDialog.ok();
	}
	
	@After
	public void cleanup(){
		if(dialog!=null && dialog.isOpen()){
			dialog.cancel();
		}
		
		DeleteUtils.forceProjectDeletion(getProject(),true);
		
		WorkbenchPreferenceDialog preferencesDialog = new WorkbenchPreferenceDialog();
		RuntimePreferencePage runtimePreference = new RuntimePreferencePage(preferencesDialog);

		preferencesDialog.open();
		preferencesDialog.select(runtimePreference);
		
		runtimePreference.removeAllRuntimes();
		preferencesDialog.cancel();
	}
	
	@Test
	public void selectRuntime() {
		dialog = getProject().openProperties();
		RuntimesPropertyPage propertyPage = new RuntimesPropertyPage(dialog);

		dialog.select(propertyPage);
		propertyPage.selectRuntime(TestServerRuntime.NAME);
		assertTrue(propertyPage.getSelectedRuntimes().contains(TestServerRuntime.NAME));
		dialog.ok();
		
		dialog = getProject().openProperties();
		propertyPage = new RuntimesPropertyPage(dialog);
		dialog.select(propertyPage);
 		assertThat(propertyPage.getSelectedRuntimes().get(0), is(TestServerRuntime.NAME));
 	}
	
	public DefaultProject getProject() {
		if (project == null){
			PackageExplorerPart packageExplorer = new PackageExplorerPart();
			packageExplorer.open();
			project = packageExplorer.getProject(PROJECT);
		}
		return project;
	}
}
