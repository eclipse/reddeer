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
 * Class representing "Maven" &gt; "Errors/Warnings" preference page.
 * 
 * @author jkopriva, ldimaggi
 */

public class WarningsPreferencePage extends PreferencePage {

	public static final String GROUP_ID = "\"groupId\" duplicate of parent groupId";
	public static final String VERSION = "\"version\" duplicate of parent version";
	public static final String PROJECT_CONFIG = "Out-of-date project configuration";
	public static final String PLUGIN_EXECUTION = "Plugin execution not covered by lifecycle configuration";
	public static final String MANAGED_VERSION = "Overriding managed version";
	private static final Logger log = Logger.getLogger(WarningsPreferencePage.class);
	
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
	 * Construct the preference page with "Maven" &gt; "Errors/Warnings".
	 */
	public WarningsPreferencePage() {
		super(new String[] { "Maven", "Errors/Warnings" });
	}
		
	/**
	 * Gets message severity level of {@value #GROUP_ID} in Maven Error/Warning Preference page
	 * @return severity of {@value #GROUP_ID}
	 */	
	public String getGroupId () {
		return new LabeledCombo(GROUP_ID).getSelection();
	}
	
	/**
	 * Sets message severity level of {@value #GROUP_ID} from Maven Error/Warning Preference page
	 * @param groupIdSeverity group id severity
	 */
	public void setGroupId (MavenErrorSeverity groupIdSeverity) {
		new LabeledCombo(GROUP_ID).setSelection(groupIdSeverity.getValue());
		log.info("Set Maven error/warning Group ID to: " + groupIdSeverity.getValue());
	}
		
	/**
	 * Gets message severity level of {@value #VERSION} in Maven Error/Warning Preference page
	 * @return severity of {@value #VERSION}
	 */	
	public String getVersion () {
		return new LabeledCombo(VERSION).getSelection();
	}
	
	/**
	 * Sets message severity level of {@value #VERSION} from Maven Error/Warning Preference page
	 * @param versionSeverity version severity
	 */
	public void setVersion (MavenErrorSeverity versionSeverity) {
		new LabeledCombo(VERSION).setSelection(versionSeverity.getValue());
		log.info("Set Maven error/warning Version to: " + versionSeverity);
	}
	
	/**
	 * Gets message severity level of {@value #PROJECT_CONFIG} in Maven Error/Warning Preference page
	 * @return severity of {@value #PROJECT_CONFIG}
	 */		
	public String getProjectConfig () {
		return new LabeledCombo(PROJECT_CONFIG).getSelection();
	}
	
	/**
	 * Sets message severity level of {@value #PROJECT_CONFIG} 
	 * from Maven Error/Warning Preference page
	 * @param projectConfigSeverity project configuration severity
	 */
	public void setProjectConfig(MavenErrorSeverity projectConfigSeverity) {
		new LabeledCombo(PROJECT_CONFIG).setSelection(projectConfigSeverity.getValue());
		log.info("Set Maven error/warning Project Config to: " + projectConfigSeverity);
	}
	
	/**
	 * Gets message severity level of {@value #PLUGIN_EXECUTION} in Maven Error/Warning Preference page
	 * @return severity of {@value #PLUGIN_EXECUTION}
	 */		
	public String getPluginExecution () {
		return new LabeledCombo(PLUGIN_EXECUTION).getSelection();
	}
	
	/**
	 * Sets message severity level of {@value #PLUGIN_EXECUTION} 
	 * from Maven Error/Warning Preference page
	 * @param pluginExecutionSeverity plugin execution severity
	 */
	public void setPluginExecution (MavenErrorSeverity pluginExecutionSeverity) {
		new LabeledCombo(PLUGIN_EXECUTION).setSelection(pluginExecutionSeverity.getValue());
		log.info("Set Maven error/warning Plugin Execution to: " + pluginExecutionSeverity);
	}
	
	/**
	 * Gets message severity level of  {@value #MANAGED_VERSION} in Maven Error/Warning Preference page
	 * @return severity of {@value #MANAGED_VERSION}
	 */		
	public String getManagedVersion () {
		return new LabeledCombo(MANAGED_VERSION).getSelection();
	}
	
	/**
	 * Sets message severity level of {@value #MANAGED_VERSION} 
	 * from Maven Error/Warning Preference page
	 * @param managedVersionSeverity severity of managedVersion
	 */
	public void setManagedVersion (MavenErrorSeverity managedVersionSeverity) {
		new LabeledCombo(MANAGED_VERSION).setSelection(managedVersionSeverity.getValue());
		log.info("Set Maven error/warning Managed Version to: " + managedVersionSeverity);
	}


}

