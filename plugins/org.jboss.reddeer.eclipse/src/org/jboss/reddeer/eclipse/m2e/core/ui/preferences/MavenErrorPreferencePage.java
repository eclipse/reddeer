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
package org.jboss.reddeer.eclipse.m2e.core.ui.preferences;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.jface.preference.PreferencePage;
import org.jboss.reddeer.swt.impl.combo.LabeledCombo;

/**
 * Class representing "Maven->Errors/Warnings" preference page.
 * 
 * @author jkopriva, ldimaggi
 */

public class MavenErrorPreferencePage extends PreferencePage {

	public static final String GROUP_ID = "\"groupId\" duplicate of parent groupId";
	public static final String VERSION = "\"version\" duplicate of parent version";
	public static final String PROJECT_CONFIG = "Out-of-date project configuration";
	public static final String PLUGIN_EXECUTION = "Plugin execution not covered by lifecycle configuration";
	public static final String MANAGED_VERSION = "Overriding managed version";
	private static final Logger log = Logger.getLogger(MavenErrorPreferencePage.class);
	
	public enum MavenErrorSeverity {
	    IGNORE("Ignore"),
	    WARNING("Warning"),
	    ERROR("Error");

	    private String value;

	    MavenErrorSeverity(final String value) {
	        this.value = value;
	    }

	    public String getValue() {
	        return value;
	    }

	    @Override
	    public String toString() {
	        return this.getValue();
	    }
	}
	
	/**
	 * Construct the preference page with "Maven" > "Errors/Warnings".
	 */
	public MavenErrorPreferencePage() {
		super(new String[] { "Maven", "Errors/Warnings" });
	}
		
	/**
	 * Gets message severity level of "groupId duplicate of parent groupId" in Maven Error/Warning Preference page
	 * @return String value of selected item in labeled combo
	 */	
	public String getGroupId () {
		return new LabeledCombo(GROUP_ID).getSelection();
	}
	
	/**
	 * Sets message severity level of "groupId duplicate of parent groupId" from Maven Error/Warning Preference page
	 * @param groupId
	 */
	public void setGroupId (MavenErrorSeverity groupId) {
		new LabeledCombo(GROUP_ID).setSelection(groupId.getValue());
		log.info("Set Maven error/warning Group ID to: " + groupId.getValue());
	}
		
	/**
	 * Gets message severity level of "version duplicate of parent version" in Maven Error/Warning Preference page
	 * @return String value of selected item in labeled combo
	 */	
	public String getVersion () {
		return new LabeledCombo(VERSION).getSelection();
	}
	
	/**
	 * Sets message severity level of "version duplicate of parent version" from Maven Error/Warning Preference page
	 * @param version  
	 */
	public void setVersion (MavenErrorSeverity version) {
		new LabeledCombo(VERSION).setSelection(version.getValue());
		log.info("Set Maven error/warning Version to: " + version);
	}
	
	/**
	 * Gets message severity level of "Out-of-date project configuration" in Maven Error/Warning Preference page
	 * @return String value of selected item in labeled combo
	 */		
	public String getProjectConfig () {
		return new LabeledCombo(PROJECT_CONFIG).getSelection();
	}
	
	/**
	 * Sets message severity level of "Out-of-date project configuration" 
	 * from Maven Error/Warning Preference page
	 * @param version  
	 */
	public void setProjectConfig (MavenErrorSeverity projectConfig) {
		new LabeledCombo(PROJECT_CONFIG).setSelection(projectConfig.getValue());
		log.info("Set Maven error/warning Project Config to: " + projectConfig);
	}
	
	/**
	 * Gets message severity level of "Plugin execution not covered by lifecycle configuration" in Maven Error/Warning Preference page
	 * @return String value of selected item in labeled combo
	 */		
	public String getPluginExecution () {
		return new LabeledCombo(PLUGIN_EXECUTION).getSelection();
	}
	
	/**
	 * Sets message severity level of "Plugin execution not covered by lifecycle configuration" 
	 * from Maven Error/Warning Preference page
	 * @param version  
	 */
	public void setPluginExecution (MavenErrorSeverity pluginExecution) {
		new LabeledCombo(PLUGIN_EXECUTION).setSelection(pluginExecution.getValue());
		log.info("Set Maven error/warning Plugin Execution to: " + pluginExecution);
	}
	
	/**
	 * Gets message severity level of "Overriding managed version" in Maven Error/Warning Preference page
	 * @return String value of selected item in labeled combo
	 */		
	public String getManagedVersion () {
		return new LabeledCombo(MANAGED_VERSION).getSelection();
	}
	
	/**
	 * Sets message severity level of "Overriding managed version" 
	 * from Maven Error/Warning Preference page
	 * @param version  
	 */
	public void setManagedVersion (MavenErrorSeverity managedVersion) {
		new LabeledCombo(MANAGED_VERSION).setSelection(managedVersion.getValue());
		log.info("Set Maven error/warning Managed Version to: " + managedVersion);
	}


}

