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
package org.eclipse.reddeer.eclipse.test.m2e.core.ui.preferences;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.reddeer.eclipse.m2e.core.ui.preferences.MavenPreferencePage;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.workbench.ui.dialogs.WorkbenchPreferenceDialog;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class MavenPreferencePageTest {

	private WorkbenchPreferenceDialog preferencesDialog = new WorkbenchPreferenceDialog();
	
	private MavenPreferencePage mavenPreferencePage = new MavenPreferencePage(preferencesDialog);
	
	@Test
	public void checkGlobalUpdatePolicy() {
		preferencesDialog.open();
		preferencesDialog.select(mavenPreferencePage);

		mavenPreferencePage.setGlobalUpdatePolicy("Always");
		assertTrue("Always".equals(mavenPreferencePage.getGlobalUpdatePolicy()));
		mavenPreferencePage.setGlobalUpdatePolicy("Daily");
		assertTrue("Daily".equals(mavenPreferencePage.getGlobalUpdatePolicy()));
		mavenPreferencePage.setGlobalUpdatePolicy("Hourly");
		assertTrue("Hourly".equals(mavenPreferencePage.getGlobalUpdatePolicy()));;
		mavenPreferencePage.setGlobalUpdatePolicy("Default");
		assertTrue("Default".equals(mavenPreferencePage.getGlobalUpdatePolicy()));
		mavenPreferencePage.setGlobalUpdatePolicy("Never");
		assertTrue("Never".equals(mavenPreferencePage.getGlobalUpdatePolicy()));

		preferencesDialog.cancel();
	}
	
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
