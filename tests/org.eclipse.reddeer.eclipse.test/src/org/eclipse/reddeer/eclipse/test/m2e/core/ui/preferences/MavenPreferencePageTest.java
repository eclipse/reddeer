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
package org.eclipse.reddeer.eclipse.test.m2e.core.ui.preferences;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.reddeer.workbench.ui.dialogs.WorkbenchPreferenceDialog;
import org.eclipse.reddeer.eclipse.m2e.core.ui.preferences.MavenPreferencePage;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.swt.exception.SWTLayerException;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class MavenPreferencePageTest {

	private WorkbenchPreferenceDialog preferencesDialog = new WorkbenchPreferenceDialog();
	
	private MavenPreferencePage mavenPreferencePage = new MavenPreferencePage(preferencesDialog);

	@Test
	public void checkAllPreferences() {
		preferencesDialog.open();
		preferencesDialog.select(mavenPreferencePage);
		
		mavenPreferencePage.setDebugOutput(true);
		mavenPreferencePage.setDoNotAutoUpdateDeps(true);
		mavenPreferencePage.setDownloadArtifactJavadoc(true);
		mavenPreferencePage.setDownloadArtifactSources(true);
		mavenPreferencePage.setDownloadRepoIndexOnStartup(true);
		mavenPreferencePage.setHideFoldersOfPhysicalyNestedModules(true);
		mavenPreferencePage.setOffline(true);
		mavenPreferencePage.setUpdateMavenProjectsOnStartup(true);
		
		assertTrue(mavenPreferencePage.isDebugOutputChecked());
		assertTrue(mavenPreferencePage.isDoNotAutoUpdateDepsChecked());
		assertTrue(mavenPreferencePage.isDownloadArtifactJavadocChecked());
		assertTrue(mavenPreferencePage.isDownloadArtifactSourcesChecked());
		assertTrue(mavenPreferencePage.isDownloadRepoIndexOnStartupChecked());
		assertTrue(mavenPreferencePage.isHideFoldersOfPhysicalyNestedModulesChecked());
		assertTrue(mavenPreferencePage.isOfflineChecked());
		assertTrue(mavenPreferencePage.isUpdateMavenProjectsOnStartupChecked());
		
		preferencesDialog.cancel();
	}

	@Test
	public void uncheckAllPreferences() {
		preferencesDialog.open();
		preferencesDialog.select(mavenPreferencePage);
		
		mavenPreferencePage.setDebugOutput(false);
		mavenPreferencePage.setDoNotAutoUpdateDeps(false);
		mavenPreferencePage.setDownloadArtifactJavadoc(false);
		mavenPreferencePage.setDownloadArtifactSources(false);
		mavenPreferencePage.setDownloadRepoIndexOnStartup(false);
		mavenPreferencePage.setHideFoldersOfPhysicalyNestedModules(false);
		mavenPreferencePage.setOffline(false);
		mavenPreferencePage.setUpdateMavenProjectsOnStartup(false);
		
		assertFalse(mavenPreferencePage.isDebugOutputChecked());
		assertFalse(mavenPreferencePage.isDoNotAutoUpdateDepsChecked());
		assertFalse(mavenPreferencePage.isDownloadArtifactJavadocChecked());
		assertFalse(mavenPreferencePage.isDownloadArtifactSourcesChecked());
		assertFalse(mavenPreferencePage.isDownloadRepoIndexOnStartupChecked());
		assertFalse(mavenPreferencePage.isHideFoldersOfPhysicalyNestedModulesChecked());
		assertFalse(mavenPreferencePage.isOfflineChecked());
		assertFalse(mavenPreferencePage.isUpdateMavenProjectsOnStartupChecked());
		
		preferencesDialog.cancel();
	}
	
	@After
	public void tearDown(){
		if(preferencesDialog.isOpen()){
			preferencesDialog.cancel();
		}
	}
}
