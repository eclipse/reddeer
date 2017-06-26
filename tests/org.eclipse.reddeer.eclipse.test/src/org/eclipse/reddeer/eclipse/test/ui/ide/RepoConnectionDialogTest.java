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
package org.eclipse.reddeer.eclipse.test.ui.ide;

import static org.junit.Assert.assertTrue;

import org.eclipse.reddeer.eclipse.equinox.security.ui.storage.PasswordProvider;
import org.eclipse.reddeer.eclipse.equinox.security.ui.storage.StoragePreferencePage;
import org.eclipse.reddeer.eclipse.mylyn.tasks.ui.views.TaskRepositoriesView;
import org.eclipse.reddeer.eclipse.mylyn.tasks.ui.views.TaskRepository;
import org.eclipse.reddeer.eclipse.mylyn.tasks.ui.wizards.TaskRepositoryWizardDialog;
import org.eclipse.reddeer.workbench.ui.dialogs.WorkbenchPreferenceDialog;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.swt.impl.text.LabeledText;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * 
 * @author ldimaggi
 * 
 */
@RunWith(RedDeerSuite.class)
public class RepoConnectionDialogTest  {
	TaskRepositoryWizardDialog repoConnectionDialog = null;
	
	@Before
	public void disableSecureStorage() {
		setEnabledMasterPasswordPrompt(false);
	}
	
	@Test
	public void getDialogTest() {
		TaskRepositoriesView repositoriesView = new TaskRepositoriesView();
		repositoriesView.open();
		TaskRepository repo = repositoriesView.getTaskRepository("Eclipse.org");
		repoConnectionDialog = repo.openProperties();
		
		assertTrue ("Properties title matches", repoConnectionDialog.getShell().getText().equals("Properties for Task Repository"));
		
		repoConnectionDialog.validateSettings();

		assertTrue("Repo Connection Properties Invalid", new LabeledText("Bugzilla Repository Settings").getText().contains("Repository is valid"));
	
		repoConnectionDialog.cancel();
		
		repoConnectionDialog = null;
		
	}
	
	@After
	public void tearDown(){
		if (repoConnectionDialog != null){
			repoConnectionDialog.cancel();
		}
		
		setEnabledMasterPasswordPrompt(true);
	}
	
	private void setEnabledMasterPasswordPrompt(boolean enabled) {
		WorkbenchPreferenceDialog preferenceDialog = new WorkbenchPreferenceDialog();
		StoragePreferencePage storagePage = new StoragePreferencePage(preferenceDialog);
		
		preferenceDialog.open();
		preferenceDialog.select(storagePage);
		
		for (PasswordProvider provider: storagePage.getMasterPasswordProviders()) {
			provider.setEnabled(enabled);
		}
		storagePage.apply();
		preferenceDialog.ok();
	}
}
