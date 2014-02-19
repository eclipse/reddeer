package org.jboss.reddeer.eclipse.m2e.core.ui.preferences;

import org.jboss.reddeer.eclipse.jface.preference.PreferencePage;
import org.jboss.reddeer.swt.impl.button.CheckBox;

/**
 * Class represents Maven preference page
 * 
 * @author Vlado Pakan
 *
 */
public class MavenPreferencePage extends PreferencePage {
	
	private static final String OFFLINE="Offline";
	private static final String DO_NOT_AUTO_UPDATE_DEPS="Do not automatically update dependencies from remote repositories";
	private static final String DEBUG_OUTPUT="Debug Output";
	private static final String DOWNLOAD_ARTIFACT_SOURCES="Download Artifact Sources";
	private static final String DOWNLOAD_ARTIFACT_JAVADOC="Download Artifact JavaDoc";
	private static final String DOWNLOAD_REPO_INDEX_UPDATES_ON_STARTUP="Download repository index updates on startup";
	private static final String UPDATE_MAVEN_PROJECTS_ON_STARTUP="Update Maven projects on startup";
	private static final String HIDE_FOLDERS_OF_PHYSICALLY_NESTED_MODULES="Hide folders of physically nested modules (experimental)";

	public MavenPreferencePage() {
		super("Maven");
	}
	
	public boolean isOfflineChecked() {
		return new CheckBox(MavenPreferencePage.OFFLINE).isChecked();
	}
	
	public void enableOffline() {
		new CheckBox(MavenPreferencePage.OFFLINE).toggle(true);
	}
	
	public void disableOffline() {
		new CheckBox(MavenPreferencePage.OFFLINE).toggle(false);
	}
	
	public boolean isDoNotAutoUpdateDepsChecked() {
		return new CheckBox(MavenPreferencePage.DO_NOT_AUTO_UPDATE_DEPS).isChecked();
	}
	
	public void enableDoNotAutoUpdateDeps() {
		new CheckBox(MavenPreferencePage.DO_NOT_AUTO_UPDATE_DEPS).toggle(true);
	}
	
	public void disableDoNotAutoUpdateDeps() {
		new CheckBox(MavenPreferencePage.DO_NOT_AUTO_UPDATE_DEPS).toggle(false);
	}
	public boolean isDebugOutputChecked() {
		return new CheckBox(MavenPreferencePage.DEBUG_OUTPUT).isChecked();
	}
	
	public void enableDebugOutput() {
		new CheckBox(MavenPreferencePage.DEBUG_OUTPUT).toggle(true);
	}
	
	public void disableDebugOutput() {
		new CheckBox(MavenPreferencePage.DEBUG_OUTPUT).toggle(false);
	}
	public boolean isDownloadArtifactSourcesChecked() {
		return new CheckBox(MavenPreferencePage.DOWNLOAD_ARTIFACT_SOURCES).isChecked();
	}
	
	public void enableDownloadArtifactSources() {
		new CheckBox(MavenPreferencePage.DOWNLOAD_ARTIFACT_SOURCES).toggle(true);
	}
	
	public void disableDownloadArtifactSources() {
		new CheckBox(MavenPreferencePage.DOWNLOAD_ARTIFACT_SOURCES).toggle(false);
	}
	public boolean isDownloadArtifactJavadocChecked() {
		return new CheckBox(MavenPreferencePage.DOWNLOAD_ARTIFACT_JAVADOC).isChecked();
	}
	
	public void enableDownloadArtifactJavadoc() {
		new CheckBox(MavenPreferencePage.DOWNLOAD_ARTIFACT_JAVADOC).toggle(true);
	}
	
	public void disableDownloadArtifactJavadoc() {
		new CheckBox(MavenPreferencePage.DOWNLOAD_ARTIFACT_JAVADOC).toggle(false);
	}
	public boolean isDownloadRepoIndexOnStartupChecked() {
		return new CheckBox(MavenPreferencePage.DOWNLOAD_REPO_INDEX_UPDATES_ON_STARTUP).isChecked();
	}
	
	public void enableDownloadRepoIndexOnStartup() {
		new CheckBox(MavenPreferencePage.DOWNLOAD_REPO_INDEX_UPDATES_ON_STARTUP).toggle(true);
	}
	
	public void disableDownloadRepoIndexOnStartup() {
		new CheckBox(MavenPreferencePage.DOWNLOAD_REPO_INDEX_UPDATES_ON_STARTUP).toggle(false);
	}
	public boolean isUpdateMavenProjectsOnStartupChecked() {
		return new CheckBox(MavenPreferencePage.UPDATE_MAVEN_PROJECTS_ON_STARTUP).isChecked();
	}
	
	public void enableUpdateMavenProjectsOnStartup() {
		new CheckBox(MavenPreferencePage.UPDATE_MAVEN_PROJECTS_ON_STARTUP).toggle(true);
	}
	
	public void disableUpdateMavenProjectsOnStartup() {
		new CheckBox(MavenPreferencePage.UPDATE_MAVEN_PROJECTS_ON_STARTUP).toggle(false);
	}
	public boolean isHideFoldersOfPhysicalyNestedModulesChecked() {
		return new CheckBox(MavenPreferencePage.HIDE_FOLDERS_OF_PHYSICALLY_NESTED_MODULES).isChecked();
	}
	
	public void enableHideFoldersOfPhysicalyNestedModules() {
		new CheckBox(MavenPreferencePage.HIDE_FOLDERS_OF_PHYSICALLY_NESTED_MODULES).toggle(true);
	}
	
	public void disableHideFoldersOfPhysicalyNestedModules() {
		new CheckBox(MavenPreferencePage.HIDE_FOLDERS_OF_PHYSICALLY_NESTED_MODULES).toggle(false);
	}
}
