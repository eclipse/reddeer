/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.eclipse.test.ui.ide;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.eclipse.equinox.security.ui.StoragePreferencePage;
import org.jboss.reddeer.eclipse.mylyn.tasks.ui.view.TaskRepositoriesView;
import org.jboss.reddeer.eclipse.mylyn.tasks.ui.view.TaskRepository;
import org.jboss.reddeer.workbench.ui.dialogs.WorkbenchPreferenceDialog;
import org.jboss.reddeer.eclipse.ui.ide.RepoConnectionDialog;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.api.TableItem;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.text.LabeledText;
import org.jboss.reddeer.swt.impl.tree.DefaultTree;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
import org.jboss.reddeer.common.wait.AbstractWait;
import org.jboss.reddeer.common.wait.TimePeriod;
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
	RepoConnectionDialog repoConnectionDialog = null;
	
	@Before
	public void disableSecureStorage() {
		setEnabledMasterPasswordPrompt(false);
	}
	
	@Test
	public void getDialogTest() {
		TaskRepositoriesView repositoriesView = new TaskRepositoriesView();
		repositoriesView.open();
		TaskRepository repo = repositoriesView.getTaskRepository("Eclipse.org");
		repo.select();
		
		new ShellMenu("File", "Properties").select();  
		
		repoConnectionDialog = new RepoConnectionDialog();
		
		assertTrue ("Properties title matches", repoConnectionDialog.getText().equals("Properties for Task Repository"));
		
		repoConnectionDialog.validateSettings();

		assertTrue("Repo Connection Properties Invalid", new LabeledText("Bugzilla Repository Settings").getText().contains("Repository is valid"));
	
		repoConnectionDialog.close();
		
		repoConnectionDialog = null;
		
	}
	
	@After
	public void tearDown(){
		if (repoConnectionDialog != null){
			repoConnectionDialog.close();
		}
		
		setEnabledMasterPasswordPrompt(true);
	}
	
	private void setEnabledMasterPasswordPrompt(boolean enabled) {
		WorkbenchPreferenceDialog preferenceDialog = new WorkbenchPreferenceDialog();
		StoragePreferencePage storagePage = new StoragePreferencePage();
		
		preferenceDialog.open();
		preferenceDialog.select(storagePage);
		
		for (TableItem item: storagePage.getMasterPasswordProviders()) {
			item.setChecked(enabled);
		}
		storagePage.apply();
		preferenceDialog.ok();
	}
}
