/*******************************************************************************
 * Copyright (c) 2018 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.eclipse.test.wst.server.ui;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.List;

import org.eclipse.reddeer.eclipse.exception.EclipseLayerException;
import org.eclipse.reddeer.eclipse.wst.server.ui.Runtime;
import org.eclipse.reddeer.eclipse.wst.server.ui.RuntimePreferencePage;
import org.eclipse.reddeer.eclipse.wst.server.ui.wizard.NewRuntimeWizardDialog;
import org.eclipse.reddeer.eclipse.wst.server.ui.wizard.NewRuntimeWizardPage;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.workbench.ui.dialogs.WorkbenchPreferenceDialog;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class RuntimePreferencePageTest {

	private static final String SERVER_NAME = TestServerRuntime.NAME;
	
	private static final String SERVER_NAME_2 = TestServerRuntime2.NAME;
	
	private static final String SERVER_PATH = "Basic";

	private WorkbenchPreferenceDialog preferencesDialog;
	
	private RuntimePreferencePage preferencePage;

	@Before
	public void openPreference(){
		preferencesDialog = new WorkbenchPreferenceDialog();
		preferencePage = new RuntimePreferencePage(preferencesDialog);
		
		preferencesDialog.open();
		preferencesDialog.select(preferencePage);
		preferencePage.removeAllRuntimes();
	}

	@After
	public void closePreference(){
		preferencePage.removeAllRuntimes();
		preferencesDialog.cancel();
	}
	
	@Test
	public void addAndRemoveRuntime() {
		NewRuntimeWizardDialog wizardDialog = preferencePage.addRuntime(); 
		NewRuntimeWizardPage wizardPage = new NewRuntimeWizardPage(wizardDialog);
		wizardPage.selectType(SERVER_PATH, SERVER_NAME);
		wizardDialog.finish();
		
		wizardDialog = preferencePage.addRuntime();
		wizardPage = new NewRuntimeWizardPage(wizardDialog);
		wizardPage.selectType(SERVER_PATH, SERVER_NAME);
		wizardDialog.finish();
		
		List<Runtime> runtimes = preferencePage.getServerRuntimes();
		assertThat(runtimes.size(), is(2));
		assertThat(runtimes.get(0), is(new Runtime(SERVER_NAME, SERVER_NAME)));
		assertThat(runtimes.get(1), is(new Runtime(SERVER_NAME + " (2)", SERVER_NAME)));
		
		preferencePage.removeRuntime(new Runtime(SERVER_NAME + " (2)", null));
		runtimes = preferencePage.getServerRuntimes();
		assertThat(runtimes.size(), is(1));
		assertThat(runtimes.get(0), is(new Runtime(SERVER_NAME, SERVER_NAME)));
		
		preferencePage.removeRuntime(new Runtime(SERVER_NAME, null));
		runtimes = preferencePage.getServerRuntimes();
		assertThat(runtimes.size(), is(0));
	}
	
	@Test
	public void removeAllRuntime() {
		NewRuntimeWizardDialog wizardDialog = preferencePage.addRuntime(); 
		NewRuntimeWizardPage wizardPage = new NewRuntimeWizardPage(wizardDialog);
		wizardPage.selectType(SERVER_PATH, SERVER_NAME);
		wizardDialog.finish();
		
		wizardDialog = preferencePage.addRuntime();
		wizardPage = new NewRuntimeWizardPage(wizardDialog);
		wizardPage.selectType(SERVER_PATH, SERVER_NAME);
		wizardDialog.finish();
		
		List<Runtime> runtimes = preferencePage.getServerRuntimes();
		assertThat(runtimes.size(), is(2));

		preferencePage.removeAllRuntimes();
		runtimes = preferencePage.getServerRuntimes();
		assertThat(runtimes.size(), is(0));
	}
	
	@Test
	public void editRuntime(){
		NewRuntimeWizardDialog wizardDialog = preferencePage.addRuntime(); 
		NewRuntimeWizardPage wizardPage = new NewRuntimeWizardPage(wizardDialog);
		wizardPage.selectType(SERVER_PATH, SERVER_NAME);
		wizardDialog.finish();

		assertFalse(new PushButton(RuntimePreferencePage.EDIT_BUTTON_NAME).isEnabled());
	}
	
	@Test
	public void getServerRuntime() {
		NewRuntimeWizardDialog wizardDialog = preferencePage.addRuntime(); 
		NewRuntimeWizardPage wizardPage = new NewRuntimeWizardPage(wizardDialog);
		wizardPage.selectType(SERVER_PATH, SERVER_NAME);
		wizardDialog.finish();
		
		wizardDialog = preferencePage.addRuntime();
		wizardPage = new NewRuntimeWizardPage(wizardDialog);
		wizardPage.selectType(SERVER_PATH, SERVER_NAME_2);
		wizardDialog.finish();
		
		wizardDialog = preferencePage.addRuntime();
		wizardPage = new NewRuntimeWizardPage(wizardDialog);
		wizardPage.selectType(SERVER_PATH, SERVER_NAME_2);
		wizardDialog.finish();
		
		List<Runtime> runtimes = preferencePage.getServerRuntimes();
		assertThat(runtimes.size(), is(3));
		
		Runtime runtime1 = preferencePage.getServerRuntime(SERVER_NAME, SERVER_NAME);
		assertThat(runtime1, is(new Runtime(SERVER_NAME, SERVER_NAME)));
		
		Runtime runtime2 = preferencePage.getServerRuntime(new Runtime(SERVER_NAME_2, SERVER_NAME_2));
		assertThat(runtime2, is(new Runtime(SERVER_NAME_2, SERVER_NAME_2)));
		
		Runtime runtime3 = null;
		try {
			runtime3 = preferencePage.getServerRuntime(SERVER_NAME_2);
		} catch (EclipseLayerException exc) {
			fail(exc.getMessage());
		}
		assertThat(runtime3, is(new Runtime(SERVER_NAME_2, SERVER_NAME_2)));		
	}
}
