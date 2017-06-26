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
package org.eclipse.reddeer.eclipse.m2e.core.ui.preferences;

import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.jface.preference.PreferencePage;
import org.eclipse.reddeer.swt.impl.button.CheckBox;

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

	/**
	 * Constructs the preference page with "Maven".
	 */
	public MavenPreferencePage(ReferencedComposite referencedComposite) {
		super(referencedComposite, new String[] {"Maven"});
	}
	
	/**
	 * Returns true when Offline checkbox is checked .
	 *
	 * @return true, if is offline checked
	 */
	public boolean isOfflineChecked() {
		return new CheckBox(referencedComposite, MavenPreferencePage.OFFLINE).isChecked();
	}
	
	/**
	 * Sets Offline checkbox .
	 *
	 * @param check the new offline
	 */
	public void setOffline(boolean check) {
		new CheckBox(referencedComposite, MavenPreferencePage.OFFLINE).toggle(check);
	}
	
	/**
	 * Returns true when Do not automatically update dependencies from remote repositories checkbox is checked .
	 *
	 * @return true, if is do not auto update deps checked
	 */
	public boolean isDoNotAutoUpdateDepsChecked() {
		return new CheckBox(referencedComposite, MavenPreferencePage.DO_NOT_AUTO_UPDATE_DEPS).isChecked();
	}
	
	/**
	 * Sets Offline Do not automatically update dependencies from remote repositories .
	 *
	 * @param check the new do not auto update deps
	 */
	public void setDoNotAutoUpdateDeps(boolean check) {
		new CheckBox(referencedComposite, MavenPreferencePage.DO_NOT_AUTO_UPDATE_DEPS).toggle(check);
	}
	
	/**
	 * Returns true when Debug Output checkbox is checked .
	 *
	 * @return true, if is debug output checked
	 */
	public boolean isDebugOutputChecked() {
		return new CheckBox(referencedComposite, MavenPreferencePage.DEBUG_OUTPUT).isChecked();
	}
	
	/**
	 * Sets Debug Output checkbox .
	 *
	 * @param check the new debug output
	 */
	public void setDebugOutput(boolean check) {
		new CheckBox(referencedComposite, MavenPreferencePage.DEBUG_OUTPUT).toggle(check);
	}
	
	/**
	 * Returns true when Download Artifact Sources checkbox is checked .
	 *
	 * @return true, if is download artifact sources checked
	 */
	public boolean isDownloadArtifactSourcesChecked() {
		return new CheckBox(referencedComposite, MavenPreferencePage.DOWNLOAD_ARTIFACT_SOURCES).isChecked();
	}
	
	/**
	 * Sets Download Artifact Sources checkbox .
	 *
	 * @param check the new download artifact sources
	 */	
	public void setDownloadArtifactSources(boolean check) {
		new CheckBox(referencedComposite, MavenPreferencePage.DOWNLOAD_ARTIFACT_SOURCES).toggle(check);
	}
	
	/**
	 * Returns true when Download Artifact JavaDoc checkbox is checked .
	 *
	 * @return true, if is download artifact javadoc checked
	 */
	public boolean isDownloadArtifactJavadocChecked() {
		return new CheckBox(referencedComposite, MavenPreferencePage.DOWNLOAD_ARTIFACT_JAVADOC).isChecked();
	}
	
	/**
	 * Sets Download Artifact JavaDoc checkbox .
	 *
	 * @param check the new download artifact javadoc
	 */
	public void setDownloadArtifactJavadoc(boolean check) {
		new CheckBox(referencedComposite, MavenPreferencePage.DOWNLOAD_ARTIFACT_JAVADOC).toggle(check);
	}
	
	/**
	 * Returns true when Download repository index updates on startup checkbox is checked .
	 *
	 * @return true, if is download repo index on startup checked
	 */
	public boolean isDownloadRepoIndexOnStartupChecked() {
		return new CheckBox(referencedComposite, MavenPreferencePage.DOWNLOAD_REPO_INDEX_UPDATES_ON_STARTUP).isChecked();
	}
	
	/**
	 * Sets Download repository index updates on startup checkbox .
	 *
	 * @param check the new download repo index on startup
	 */
	public void setDownloadRepoIndexOnStartup(boolean check) {
		new CheckBox(referencedComposite, MavenPreferencePage.DOWNLOAD_REPO_INDEX_UPDATES_ON_STARTUP).toggle(check);
	}
	
	/**
	 * Returns true when Update Maven projects on startup checkbox is checked .
	 *
	 * @return true, if is update maven projects on startup checked
	 */
	public boolean isUpdateMavenProjectsOnStartupChecked() {
		return new CheckBox(referencedComposite, MavenPreferencePage.UPDATE_MAVEN_PROJECTS_ON_STARTUP).isChecked();
	}
	
	/**
	 * Sets Update Maven projects on startup checkbox .
	 *
	 * @param check the new update maven projects on startup
	 */
	public void setUpdateMavenProjectsOnStartup(boolean check) {
		new CheckBox(referencedComposite, MavenPreferencePage.UPDATE_MAVEN_PROJECTS_ON_STARTUP).toggle(check);
	}
	
	/**
	 * Returns true when Hide folders of physically nested modules checkbox is checked .
	 *
	 * @return true, if is hide folders of physicaly nested modules checked
	 */
	public boolean isHideFoldersOfPhysicalyNestedModulesChecked() {
		return new CheckBox(referencedComposite, MavenPreferencePage.HIDE_FOLDERS_OF_PHYSICALLY_NESTED_MODULES).isChecked();
	}
	
	/**
	 * Sets Hide folders of physically nested modules checkbox .
	 *
	 * @param check the new hide folders of physicaly nested modules
	 */
	public void setHideFoldersOfPhysicalyNestedModules(boolean check) {
		new CheckBox(referencedComposite, MavenPreferencePage.HIDE_FOLDERS_OF_PHYSICALLY_NESTED_MODULES).toggle(check);
	}
}
