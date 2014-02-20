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
	/**
	 * Returns true when Offline checkbox is checked 
	 * @return
	 */
	public boolean isOfflineChecked() {
		return new CheckBox(MavenPreferencePage.OFFLINE).isChecked();
	}
	/**
	 * Check Offline checkbox 
	 */
	public void enableOffline() {
		new CheckBox(MavenPreferencePage.OFFLINE).toggle(true);
	}
	/**
	 * Uncheck Offline checkbox 
	 */
	public void disableOffline() {
		new CheckBox(MavenPreferencePage.OFFLINE).toggle(false);
	}
	/**
	 * Returns true when Do not automatically update dependencies from remote repositories checkbox is checked 
	 * @return
	 */
	public boolean isDoNotAutoUpdateDepsChecked() {
		return new CheckBox(MavenPreferencePage.DO_NOT_AUTO_UPDATE_DEPS).isChecked();
	}
	/**
	 * Check Offline Do not automatically update dependencies from remote repositories 
	 */
	public void enableDoNotAutoUpdateDeps() {
		new CheckBox(MavenPreferencePage.DO_NOT_AUTO_UPDATE_DEPS).toggle(true);
	}
	/**
	 * Uncheck Do not automatically update dependencies from remote repositories checkbox 
	 */
	public void disableDoNotAutoUpdateDeps() {
		new CheckBox(MavenPreferencePage.DO_NOT_AUTO_UPDATE_DEPS).toggle(false);
	}
	/**
	 * Returns true when Debug Output checkbox is checked 
	 * @return
	 */
	public boolean isDebugOutputChecked() {
		return new CheckBox(MavenPreferencePage.DEBUG_OUTPUT).isChecked();
	}
	/**
	 * Check Debug Output checkbox 
	 */
	public void enableDebugOutput() {
		new CheckBox(MavenPreferencePage.DEBUG_OUTPUT).toggle(true);
	}
	/**
	 * Uncheck Debug Output checkbox 
	 */
	public void disableDebugOutput() {
		new CheckBox(MavenPreferencePage.DEBUG_OUTPUT).toggle(false);
	}
	/**
	 * Returns true when Download Artifact Sources checkbox is checked 
	 * @return
	 */
	public boolean isDownloadArtifactSourcesChecked() {
		return new CheckBox(MavenPreferencePage.DOWNLOAD_ARTIFACT_SOURCES).isChecked();
	}
	/**
	 * Check Download Artifact Sources checkbox 
	 */	
	public void enableDownloadArtifactSources() {
		new CheckBox(MavenPreferencePage.DOWNLOAD_ARTIFACT_SOURCES).toggle(true);
	}
	/**
	 * Uncheck Download Artifact Sources checkbox 
	 */
	public void disableDownloadArtifactSources() {
		new CheckBox(MavenPreferencePage.DOWNLOAD_ARTIFACT_SOURCES).toggle(false);
	}
	/**
	 * Returns true when Download Artifact JavaDoc checkbox is checked 
	 * @return
	 */
	public boolean isDownloadArtifactJavadocChecked() {
		return new CheckBox(MavenPreferencePage.DOWNLOAD_ARTIFACT_JAVADOC).isChecked();
	}
	/**
	 * Check Download Artifact JavaDoc checkbox 
	 */
	public void enableDownloadArtifactJavadoc() {
		new CheckBox(MavenPreferencePage.DOWNLOAD_ARTIFACT_JAVADOC).toggle(true);
	}
	/**
	 * Uncheck Download Artifact JavaDoc checkbox 
	 */
	public void disableDownloadArtifactJavadoc() {
		new CheckBox(MavenPreferencePage.DOWNLOAD_ARTIFACT_JAVADOC).toggle(false);
	}
	/**
	 * Returns true when Download repository index updates on startup checkbox is checked 
	 * @return
	 */
	public boolean isDownloadRepoIndexOnStartupChecked() {
		return new CheckBox(MavenPreferencePage.DOWNLOAD_REPO_INDEX_UPDATES_ON_STARTUP).isChecked();
	}
	/**
	 * Check Download repository index updates on startup checkbox 
	 */
	public void enableDownloadRepoIndexOnStartup() {
		new CheckBox(MavenPreferencePage.DOWNLOAD_REPO_INDEX_UPDATES_ON_STARTUP).toggle(true);
	}
	/**
	 * Uncheck Download repository index updates on startup checkbox 
	 */
	public void disableDownloadRepoIndexOnStartup() {
		new CheckBox(MavenPreferencePage.DOWNLOAD_REPO_INDEX_UPDATES_ON_STARTUP).toggle(false);
	}
	/**
	 * Returns true when Update Maven projects on startup checkbox is checked 
	 * @return
	 */
	public boolean isUpdateMavenProjectsOnStartupChecked() {
		return new CheckBox(MavenPreferencePage.UPDATE_MAVEN_PROJECTS_ON_STARTUP).isChecked();
	}
	/**
	 * Check Update Maven projects on startup checkbox 
	 */
	public void enableUpdateMavenProjectsOnStartup() {
		new CheckBox(MavenPreferencePage.UPDATE_MAVEN_PROJECTS_ON_STARTUP).toggle(true);
	}
	/**
	 * Uncheck Update Maven projects on startup checkbox 
	 */
	public void disableUpdateMavenProjectsOnStartup() {
		new CheckBox(MavenPreferencePage.UPDATE_MAVEN_PROJECTS_ON_STARTUP).toggle(false);
	}
	/**
	 * Returns true when Hide folders of physically nested modules checkbox is checked 
	 * @return
	 */
	public boolean isHideFoldersOfPhysicalyNestedModulesChecked() {
		return new CheckBox(MavenPreferencePage.HIDE_FOLDERS_OF_PHYSICALLY_NESTED_MODULES).isChecked();
	}
	/**
	 * Check Hide folders of physically nested modules checkbox 
	 */
	public void enableHideFoldersOfPhysicalyNestedModules() {
		new CheckBox(MavenPreferencePage.HIDE_FOLDERS_OF_PHYSICALLY_NESTED_MODULES).toggle(true);
	}
	/**
	 * Uncheck Hide folders of physically nested modules checkbox 
	 */
	public void disableHideFoldersOfPhysicalyNestedModules() {
		new CheckBox(MavenPreferencePage.HIDE_FOLDERS_OF_PHYSICALLY_NESTED_MODULES).toggle(false);
	}
}
