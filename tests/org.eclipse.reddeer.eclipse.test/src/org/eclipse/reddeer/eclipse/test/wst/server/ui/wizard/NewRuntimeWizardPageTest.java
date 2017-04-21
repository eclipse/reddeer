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
package org.eclipse.reddeer.eclipse.test.wst.server.ui.wizard;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.eclipse.reddeer.workbench.ui.dialogs.WorkbenchPreferenceDialog;
import org.eclipse.reddeer.eclipse.test.wst.server.ui.TestServerRuntime;
import org.eclipse.reddeer.eclipse.wst.server.ui.Runtime;
import org.eclipse.reddeer.eclipse.wst.server.ui.RuntimePreferencePage;
import org.eclipse.reddeer.eclipse.wst.server.ui.wizard.NewRuntimeWizardDialog;
import org.eclipse.reddeer.eclipse.wst.server.ui.wizard.NewRuntimeWizardPage;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class NewRuntimeWizardPageTest {

	private RuntimePreferencePage preference;
	
	private NewRuntimeWizardDialog wizard;
	
	private NewRuntimeWizardPage wizardPage;
	
	private WorkbenchPreferenceDialog workbenchPreferencesDialog;

	@Before
	public void setUp(){
		workbenchPreferencesDialog = new WorkbenchPreferenceDialog();
		workbenchPreferencesDialog.open();
		preference = new RuntimePreferencePage();
		workbenchPreferencesDialog.select(preference);
		preference.removeAllRuntimes();
		wizard = preference.addRuntime();
		wizardPage = new NewRuntimeWizardPage();
	}
	
	@Test
	public void selectType(){
		wizardPage.selectType("Basic", TestServerRuntime.NAME);
		wizard.finish();
		List<Runtime> runtimes = preference.getServerRuntimes();
		assertThat(runtimes.size(), is(1));
		assertThat(runtimes.get(0).getType(), is(TestServerRuntime.TYPE));
	}
	
	@After
	public void tearDown(){
		if (workbenchPreferencesDialog != null){
			preference.removeAllRuntimes();
			workbenchPreferencesDialog.cancel();			
		}
	}
}
