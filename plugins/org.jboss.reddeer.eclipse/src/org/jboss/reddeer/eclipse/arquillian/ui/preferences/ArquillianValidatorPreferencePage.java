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
package org.jboss.reddeer.eclipse.arquillian.ui.preferences;

import org.jboss.reddeer.jface.preference.PreferencePage;
import org.jboss.reddeer.swt.impl.button.CheckBox;
import org.jboss.reddeer.swt.impl.combo.LabeledCombo;
import org.jboss.reddeer.swt.impl.text.LabeledText;

/**
 * Class represents Arquillian preference page
 * 
 * @author Vlado Pakan, Len DiMaggio
 *
 */
public class ArquillianValidatorPreferencePage extends PreferencePage {

	private static final String TEST_ARQUILLIAN_CONTAINER = "Test Arquillian Container";
	private static final String ENABLE_VALIDATION = "Enable Validation";
	private static final String SELECT_SEVERITY_LEVEL = "Select the severity level for the following optional problems:";
	private static final String MISSING_DEPLOY_METHOD = "Missing @Deployment method";
	private static final String MISSING_TEST_METHOD = "Missing @Test method";
	private static final String TYPE_NOT_INCLUDED = "Type is not included in any deployment";
	private static final String IMPORT_NOT_INCLUDED = "Import is not included in any deployment";
	private static final String DEPLOY_ARCHIVE_NOT_INCLUDED = "Deployment archive cannot be created";
	private static final String INVALID_ARCHIVE_NAME = "Invalid archive name";
	private static final String DEPLOY_METHOD_PUBLIC_STATIC = "Deployment method has to be public and static";
	private static final String INVALID_ARCHIVE_FILE_LOCATION = "Invalid archive file location";

	/**
	 * Constructs the preference page with "Arquillian".
	 */
	public ArquillianValidatorPreferencePage() {
		super(new String[] { "JBoss Tools", "Arquillian", "Arquillian Validator" });
	}

	/**
	 * Returns true when "Test Arquillian Container" checkbox is checked.
	 *
	 * @return true, if is checked
	 */
	public boolean isTestArquillianContainerChecked() {
		return new CheckBox(ArquillianValidatorPreferencePage.TEST_ARQUILLIAN_CONTAINER).isChecked();
	}

	/**
	 * Sets "Test Arquillian Container" checkbox.
	 *
	 * @param the checkbox
	 */
	public void setTestArquillianContainer(boolean check) {
		new CheckBox(ArquillianValidatorPreferencePage.TEST_ARQUILLIAN_CONTAINER).toggle(check);
	}

	/**
	 * Returns true when "Enable Validation" checkbox is checked.
	 *
	 * @return true, if is checked
	 */
	public boolean isTestEnableValidationChecked() {
		return new CheckBox(ArquillianValidatorPreferencePage.ENABLE_VALIDATION).isChecked();
	}

	/**
	 * Sets "Enable Validation" checkbox.
	 *
	 * @param the checkbox
	 */
	public void setEnableValidation(boolean check) {
		new CheckBox(ArquillianValidatorPreferencePage.ENABLE_VALIDATION).toggle(check);
	}
	
	/**
	 * Returns the current selection in the "Select the severity level for the following optional problems" labeled text
	 * 
	 * @return Arquillian string
	 */
	public String getSelectedSeverity () {
		return (new LabeledText(SELECT_SEVERITY_LEVEL).getText());
	}

	/**
	 * Sets the "Select the severity level for the following optional problems" in the labeled text
	 * 
	 * @param The string to be set
	 */
	public void setSelectedSeverity(String filterString) {
		new LabeledText(SELECT_SEVERITY_LEVEL).setText(filterString);
	}
	
	/**
	 * Returns the current selection in the "Missing @Deployment method" combo
	 * 
	 * @return Arquillian combo selection string
	 */
	public String getDeploymentMethod () {
		return (new LabeledCombo(MISSING_DEPLOY_METHOD).getSelection());
	}

	/**
	 * Sets the "Missing @Deployment method" combo
	 * 
	 * @param The string to be selected
	 */
	public void setDeploymentMethod (String severityString) {
		new LabeledCombo(MISSING_DEPLOY_METHOD).setSelection(severityString);
	}
		
	/**
	 * Returns the current selection in the "Missing @Test method" combo
	 * 
	 * @return Arquillian combo selection string
	 */
	public String getMissingTestMethod () {
		return (new LabeledCombo(MISSING_TEST_METHOD).getSelection());
	}

	/**
	 * Sets the "Missing @Test method" combo
	 * 
	 * @param The string to be selected
	 */
	public void setMissingTestMethod (String severityString) {
		new LabeledCombo(MISSING_TEST_METHOD).setSelection(severityString);
	}
	
	/**
	 * Returns the current selection in the "Type is not included in any deployment" combo
	 * 
	 * @return Arquillian combo selection string
	 */
	public String getTypeNotIncluded () {
		return (new LabeledCombo(TYPE_NOT_INCLUDED).getSelection());
	}

	/**
	 * Sets the "Type is not included in any deployment" combo
	 * 
	 * @param The string to be selected
	 */
	public void setTypeNotIncluded (String severityString) {
		new LabeledCombo(TYPE_NOT_INCLUDED).setSelection(severityString);
	}
	
	
	/**
	 * Returns the current selection in the "Import is not included in any deployment" combo
	 * 
	 * @return Arquillian combo selection string
	 */
	public String getImportNotIncluded () {
		return (new LabeledCombo(IMPORT_NOT_INCLUDED).getSelection());
	}

	/**
	 * Sets the "Import is not included in any deployment" combo
	 * 
	 * @param The string to be selected
	 */
	public void setImportNotIncluded (String severityString) {
		new LabeledCombo(IMPORT_NOT_INCLUDED).setSelection(severityString);
	}
	
	
	/**
	 * Returns the current selection in the "Deployment archive cannot be created" combo
	 * 
	 * @return Arquillian combo selection string
	 */
	public String getDeployArchiveNotIncluded () {
		return (new LabeledCombo(DEPLOY_ARCHIVE_NOT_INCLUDED).getSelection());
	}

	/**
	 * Sets the "Deployment archive cannot be created" combo
	 * 
	 * @param The string to be selected
	 */
	public void setDeployArchiveNotIncluded (String severityString) {
		new LabeledCombo(DEPLOY_ARCHIVE_NOT_INCLUDED).setSelection(severityString);
	}
	
	/**
	 * Returns the current selection in the "Invalid archive name" combo
	 * 
	 * @return Arquillian combo selection string
	 */
	public String getInvalidArchiveName () {
		return (new LabeledCombo(INVALID_ARCHIVE_NAME).getSelection());
	}

	/**
	 * Sets the "Invalid archive name" combo
	 * 
	 * @param The string to be selected
	 */
	public void setInvalidArchiveName (String severityString) {
		new LabeledCombo(INVALID_ARCHIVE_NAME).setSelection(severityString);
	}
	
	/**
	 * Returns the current selection in the "Deployment method has to be public and static" combo
	 * 
	 * @return Arquillian combo selection string
	 */
	public String getDeployMethodPublicStatic () {
		return (new LabeledCombo(DEPLOY_METHOD_PUBLIC_STATIC).getSelection());
	}

	/**
	 * Sets the "Deployment method has to be public and static" combo
	 * 
	 * @param The string to be selected
	 */
	public void setDeployMethodPublicStatic (String severityString) {
		new LabeledCombo(DEPLOY_METHOD_PUBLIC_STATIC).setSelection(severityString);
	}
	
	/**
	 * Returns the current selection in the "Invalid archive file location" combo
	 * 
	 * @return Arquillian combo selection string
	 */
	public String getInvalidArchiveFileLocation () {
		return (new LabeledCombo(INVALID_ARCHIVE_FILE_LOCATION).getSelection());
	}

	/**
	 * Sets the "Invalid archive file location" combo
	 * 
	 * @param The string to be selected
	 */
	public void setInvalidArchiveFileLocation (String severityString) {
		new LabeledCombo(INVALID_ARCHIVE_FILE_LOCATION).setSelection(severityString);
	}
	
}
