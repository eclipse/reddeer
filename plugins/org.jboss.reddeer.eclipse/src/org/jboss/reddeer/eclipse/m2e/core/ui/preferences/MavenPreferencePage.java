package org.jboss.reddeer.eclipse.m2e.core.ui.preferences;

import org.jboss.reddeer.eclipse.jface.preference.WindowPreferencePage;
import org.jboss.reddeer.swt.impl.button.CheckBox;

/**
 * Class represents Maven preference page
 * 
 * @author Vlado Pakan
 *
 */
public class MavenPreferencePage extends WindowPreferencePage {
	
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
	 * Sets Offline checkbox 
	 * @param check
	 */
	public void setOffline(boolean check) {
		new CheckBox(MavenPreferencePage.OFFLINE).toggle(check);
	}
	/**
	 * Returns true when Do not automatically update dependencies from remote repositories checkbox is checked 
	 * @return
	 */
	public boolean isDoNotAutoUpdateDepsChecked() {
		return new CheckBox(MavenPreferencePage.DO_NOT_AUTO_UPDATE_DEPS).isChecked();
	}
	/**
	 * Sets Offline Do not automatically update dependencies from remote repositories 
	 * @param check
	 */
	public void setDoNotAutoUpdateDeps(boolean check) {
		new CheckBox(MavenPreferencePage.DO_NOT_AUTO_UPDATE_DEPS).toggle(check);
	}
	/**
	 * Returns true when Debug Output checkbox is checked 
	 * @return
	 */
	public boolean isDebugOutputChecked() {
		return new CheckBox(MavenPreferencePage.DEBUG_OUTPUT).isChecked();
	}
	/**
	 * Sets Debug Output checkbox 
	 * @param check
	 */
	public void setDebugOutput(boolean check) {
		new CheckBox(MavenPreferencePage.DEBUG_OUTPUT).toggle(check);
	}
	/**
	 * Returns true when Download Artifact Sources checkbox is checked 
	 * @return
	 */
	public boolean isDownloadArtifactSourcesChecked() {
		return new CheckBox(MavenPreferencePage.DOWNLOAD_ARTIFACT_SOURCES).isChecked();
	}
	/**
	 * Sets Download Artifact Sources checkbox 
	 * @param check
	 */	
	public void setDownloadArtifactSources(boolean check) {
		new CheckBox(MavenPreferencePage.DOWNLOAD_ARTIFACT_SOURCES).toggle(check);
	}
	/**
	 * Returns true when Download Artifact JavaDoc checkbox is checked 
	 * @return
	 */
	public boolean isDownloadArtifactJavadocChecked() {
		return new CheckBox(MavenPreferencePage.DOWNLOAD_ARTIFACT_JAVADOC).isChecked();
	}
	/**
	 * Sets Download Artifact JavaDoc checkbox 
	 * @param check
	 */
	public void setDownloadArtifactJavadoc(boolean check) {
		new CheckBox(MavenPreferencePage.DOWNLOAD_ARTIFACT_JAVADOC).toggle(check);
	}
	/**
	 * Returns true when Download repository index updates on startup checkbox is checked 
	 * @return
	 */
	public boolean isDownloadRepoIndexOnStartupChecked() {
		return new CheckBox(MavenPreferencePage.DOWNLOAD_REPO_INDEX_UPDATES_ON_STARTUP).isChecked();
	}
	/**
	 * Sets Download repository index updates on startup checkbox 
	 * @param check
	 */
	public void setDownloadRepoIndexOnStartup(boolean check) {
		new CheckBox(MavenPreferencePage.DOWNLOAD_REPO_INDEX_UPDATES_ON_STARTUP).toggle(check);
	}
	/**
	 * Returns true when Update Maven projects on startup checkbox is checked 
	 * @return
	 */
	public boolean isUpdateMavenProjectsOnStartupChecked() {
		return new CheckBox(MavenPreferencePage.UPDATE_MAVEN_PROJECTS_ON_STARTUP).isChecked();
	}
	/**
	 * Sets Update Maven projects on startup checkbox 
	 * @param check
	 */
	public void setUpdateMavenProjectsOnStartup(boolean check) {
		new CheckBox(MavenPreferencePage.UPDATE_MAVEN_PROJECTS_ON_STARTUP).toggle(check);
	}
	/**
	 * Returns true when Hide folders of physically nested modules checkbox is checked 
	 * @return
	 */
	public boolean isHideFoldersOfPhysicalyNestedModulesChecked() {
		return new CheckBox(MavenPreferencePage.HIDE_FOLDERS_OF_PHYSICALLY_NESTED_MODULES).isChecked();
	}
	/**
	 * Sets Hide folders of physically nested modules checkbox 
	 * @param check
	 */
	public void setHideFoldersOfPhysicalyNestedModules(boolean check) {
		new CheckBox(MavenPreferencePage.HIDE_FOLDERS_OF_PHYSICALLY_NESTED_MODULES).toggle(check);
	}
}
