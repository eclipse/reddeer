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
package org.eclipse.reddeer.eclipse.m2e.core.ui.preferences;

import org.eclipse.reddeer.core.matcher.WithTextMatcher;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.jface.preference.PreferencePage;
import org.eclipse.reddeer.swt.impl.button.CheckBox;
import org.eclipse.reddeer.swt.impl.combo.LabeledCombo;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

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
	private static final String GLOBAL_UPDATE_POLICY="Global Update Policy:";

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
		return new CheckBox(this, MavenPreferencePage.OFFLINE).isChecked();
	}
	
	/**
	 * Sets Offline checkbox .
	 *
	 * @param check the new offline
	 */
	public MavenPreferencePage setOffline(boolean check) {
		new CheckBox(this, MavenPreferencePage.OFFLINE).toggle(check);
		return this;
	}
	
	/**
	 * Returns true when Do not automatically update dependencies from remote repositories checkbox is checked .
	 *
	 * @return true, if is do not auto update deps checked
	 */
	public boolean isDoNotAutoUpdateDepsChecked() {
		try {
			return new CheckBox(this, MavenPreferencePage.DO_NOT_AUTO_UPDATE_DEPS).isChecked();
		} catch (org.eclipse.reddeer.core.exception.CoreLayerException e) { // there is no CheckBox "Do Not Auto update..." starting 2023-09-M3 Eclipse
			return "Never".equals(getGlobalUpdatePolicy());
		}
	}
	
	/**
	 * Sets Offline Do not automatically update dependencies from remote repositories .
	 *
	 * @param check the new do not auto update deps
	 */
	public MavenPreferencePage setDoNotAutoUpdateDeps(boolean check) {
		try {
			new CheckBox(this, MavenPreferencePage.DO_NOT_AUTO_UPDATE_DEPS).toggle(check);
		} catch (org.eclipse.reddeer.core.exception.CoreLayerException e) { // there is no CheckBox "Do Not Auto update..." starting 2023-09-M3 Eclipse
			if (check) {
				setGlobalUpdatePolicy("Never");
			} else {
				setGlobalUpdatePolicy("Default");
			}
		}
		return this;
	}
	
	/**
	 * Returns true when Debug Output checkbox is checked .
	 *
	 * @return true, if is debug output checked
	 */
	public boolean isDebugOutputChecked() {
		return new CheckBox(this, MavenPreferencePage.DEBUG_OUTPUT).isChecked();
	}
	
	/**
	 * Sets Debug Output checkbox .
	 *
	 * @param check the new debug output
	 */
	public MavenPreferencePage setDebugOutput(boolean check) {
		new CheckBox(this, MavenPreferencePage.DEBUG_OUTPUT).toggle(check);
		return this;
	}
	
	/**
	 * Returns true when Download Artifact Sources checkbox is checked .
	 *
	 * @return true, if is download artifact sources checked
	 */
	public boolean isDownloadArtifactSourcesChecked() {
		return new CheckBox(this, MavenPreferencePage.DOWNLOAD_ARTIFACT_SOURCES).isChecked();
	}
	
	/**
	 * Sets Download Artifact Sources checkbox .
	 *
	 * @param check the new download artifact sources
	 */	
	public MavenPreferencePage setDownloadArtifactSources(boolean check) {
		new CheckBox(this, MavenPreferencePage.DOWNLOAD_ARTIFACT_SOURCES).toggle(check);
		return this;
	}
	
	/**
	 * Returns true when Download Artifact JavaDoc checkbox is checked .
	 *
	 * @return true, if is download artifact javadoc checked
	 */
	public boolean isDownloadArtifactJavadocChecked() {
		return getDownloadArtifactJavadocButton().isChecked();
	}
	
	/**
	 * Sets Download Artifact JavaDoc checkbox .
	 *
	 * @param check the new download artifact javadoc
	 */
	public MavenPreferencePage setDownloadArtifactJavadoc(boolean check) {
		getDownloadArtifactJavadocButton().toggle(check);
		return this;
	}
	
	/**
	 * Checkbox text is Download Artifact JavaDoc in Oxygen and Download Artifact Javadoc in Photon
	 * @return
	 */
	private CheckBox getDownloadArtifactJavadocButton() {
		return new CheckBox(this, new WithTextMatcher(new TypeSafeMatcher<String>() {
			@Override
			protected boolean matchesSafely(String text) {
				return text.toLowerCase().replaceAll("&", "").split("\t")[0].equals(DOWNLOAD_ARTIFACT_JAVADOC.toLowerCase());
			}

			@Override
			public void describeTo(Description desc) {
				desc.appendText("Matcher matching text to "+DOWNLOAD_ARTIFACT_JAVADOC);
				
			}
		}));
	}
	
	/**
	 * Returns true when Download repository index updates on startup checkbox is checked .
	 *
	 * @return true, if is download repo index on startup checked
	 */
	public boolean isDownloadRepoIndexOnStartupChecked() {
		return new CheckBox(this, MavenPreferencePage.DOWNLOAD_REPO_INDEX_UPDATES_ON_STARTUP).isChecked();
	}
	
	/**
	 * Sets Download repository index updates on startup checkbox .
	 *
	 * @param check the new download repo index on startup
	 */
	public MavenPreferencePage setDownloadRepoIndexOnStartup(boolean check) {
		new CheckBox(this, MavenPreferencePage.DOWNLOAD_REPO_INDEX_UPDATES_ON_STARTUP).toggle(check);
		return this;
	}
	
	/**
	 * Returns true when Update Maven projects on startup checkbox is checked .
	 *
	 * @return true, if is update maven projects on startup checked
	 */
	public boolean isUpdateMavenProjectsOnStartupChecked() {
		return new CheckBox(this, MavenPreferencePage.UPDATE_MAVEN_PROJECTS_ON_STARTUP).isChecked();
	}
	
	/**
	 * Sets Update Maven projects on startup checkbox .
	 *
	 * @param check the new update maven projects on startup
	 */
	public MavenPreferencePage setUpdateMavenProjectsOnStartup(boolean check) {
		new CheckBox(this, MavenPreferencePage.UPDATE_MAVEN_PROJECTS_ON_STARTUP).toggle(check);
		return this;
	}
	
	/**
	 * Returns true when Hide folders of physically nested modules checkbox is checked .
	 *
	 * @return true, if is hide folders of physicaly nested modules checked
	 */
	public boolean isHideFoldersOfPhysicalyNestedModulesChecked() {
		return new CheckBox(this, MavenPreferencePage.HIDE_FOLDERS_OF_PHYSICALLY_NESTED_MODULES).isChecked();
	}
	
	/**
	 * Sets Hide folders of physically nested modules checkbox .
	 *
	 * @param check the new hide folders of physicaly nested modules
	 */
	public MavenPreferencePage setHideFoldersOfPhysicalyNestedModules(boolean check) {
		new CheckBox(this, MavenPreferencePage.HIDE_FOLDERS_OF_PHYSICALLY_NESTED_MODULES).toggle(check);
		return this;
	}
	
	/**
	 * Returns String with selected Global Update Policy combo item .
	 *
	 * @return selected Global Update Policy combo item 
	 */
	public String getGlobalUpdatePolicy() {
		return new LabeledCombo(MavenPreferencePage.GLOBAL_UPDATE_POLICY).getSelection();
	}
	
	/**
	 * Sets Global Update Policy .
	 *
	 * @param updatePolicyType type of Global Update Policy to select
	 */
	public MavenPreferencePage setGlobalUpdatePolicy(String updatePolicyType) {
		new LabeledCombo(MavenPreferencePage.GLOBAL_UPDATE_POLICY).setSelection(updatePolicyType);
		return this;
	}
}
