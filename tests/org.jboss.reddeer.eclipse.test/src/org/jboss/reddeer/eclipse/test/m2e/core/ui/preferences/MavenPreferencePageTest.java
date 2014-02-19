package org.jboss.reddeer.eclipse.test.m2e.core.ui.preferences;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jboss.reddeer.eclipse.m2e.core.ui.preferences.MavenPreferencePage;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.test.RedDeerTest;
import org.junit.Test;

public class MavenPreferencePageTest extends RedDeerTest{

	private MavenPreferencePage mavenPreferencePage = new MavenPreferencePage();

	@Test
	public void checkAllPreferences() {
		mavenPreferencePage.open();
		
		mavenPreferencePage.enableDebugOutput();
		mavenPreferencePage.enableDoNotAutoUpdateDeps();
		mavenPreferencePage.enableDownloadArtifactJavadoc();
		mavenPreferencePage.enableDownloadArtifactSources();
		mavenPreferencePage.enableDownloadRepoIndexOnStartup();
		mavenPreferencePage.enableHideFoldersOfPhysicalyNestedModules();
		mavenPreferencePage.enableOffline();
		mavenPreferencePage.enableUpdateMavenProjectsOnStartup();
		
		assertTrue(mavenPreferencePage.isDebugOutputChecked());
		assertTrue(mavenPreferencePage.isDoNotAutoUpdateDepsChecked());
		assertTrue(mavenPreferencePage.isDownloadArtifactJavadocChecked());
		assertTrue(mavenPreferencePage.isDownloadArtifactSourcesChecked());
		assertTrue(mavenPreferencePage.isDownloadRepoIndexOnStartupChecked());
		assertTrue(mavenPreferencePage.isHideFoldersOfPhysicalyNestedModulesChecked());
		assertTrue(mavenPreferencePage.isOfflineChecked());
		assertTrue(mavenPreferencePage.isUpdateMavenProjectsOnStartupChecked());
		
		mavenPreferencePage.cancel();
	}

	@Test
	public void uncheckAllPreferences() {
		mavenPreferencePage.open();
		
		mavenPreferencePage.disableDebugOutput();
		mavenPreferencePage.disableDoNotAutoUpdateDeps();
		mavenPreferencePage.disableDownloadArtifactJavadoc();
		mavenPreferencePage.disableDownloadArtifactSources();
		mavenPreferencePage.disableDownloadRepoIndexOnStartup();
		mavenPreferencePage.disableHideFoldersOfPhysicalyNestedModules();
		mavenPreferencePage.disableOffline();
		mavenPreferencePage.disableUpdateMavenProjectsOnStartup();
		
		assertFalse(mavenPreferencePage.isDebugOutputChecked());
		assertFalse(mavenPreferencePage.isDoNotAutoUpdateDepsChecked());
		assertFalse(mavenPreferencePage.isDownloadArtifactJavadocChecked());
		assertFalse(mavenPreferencePage.isDownloadArtifactSourcesChecked());
		assertFalse(mavenPreferencePage.isDownloadRepoIndexOnStartupChecked());
		assertFalse(mavenPreferencePage.isHideFoldersOfPhysicalyNestedModulesChecked());
		assertFalse(mavenPreferencePage.isOfflineChecked());
		assertFalse(mavenPreferencePage.isUpdateMavenProjectsOnStartupChecked());
		
		mavenPreferencePage.cancel();
	}
	
	@Override
	protected void tearDown(){
		// try to close preference dialog in case it stayed open
		try{
			mavenPreferencePage.cancel();
		} catch (SWTLayerException swtle){
			// do nothing
		}
		super.tearDown();
	}
}
