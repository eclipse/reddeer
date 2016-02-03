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
import org.jboss.reddeer.swt.impl.table.DefaultTable;
import org.jboss.reddeer.swt.impl.text.DefaultText;

/**
 * Class represents Arquillian preference page
 * 
 * @author Vlado Pakan, Len DiMaggio
 *
 */
public class ArquillianPreferencePage extends PreferencePage {

	private static final String ARQUILLIAN_VERSION = "Arquillian version:";
	private static final String ENABLE_DEFAULT_VM_ARGUMENTS = "Enable default VM arguments";
	private static final String ADD_DEFAULT_VM_ARGS_TO_JUNIT_LAUNCH = "Add the default VM arguments to the JUnit/TestNG launch configurations";
	private static final String ADD_DEFAULT_VM_ARGS_TO_LAUNCH = "Add the default VM arguments to the existing launch configurations";
	private static final String ALLOW_OS_COMMAND_WHEN_ANALYZING = "Allow running an OS command when analyzing a deployment method";
	/* https://issues.jboss.org/browse/JBIDE-21632 */
	private static final String ALLOW_SYS_PROPERTY_WHEN_ANALYZING = "Allow setting a system propery when analyzing a deployment method";

	/**
	 * Constructs the preference page with "Arquillian".
	 */
	public ArquillianPreferencePage() {
		super(new String[] { "JBoss Tools", "Arquillian" });
	}

	/**
	 * Returns true when "Enable default VM arguments" checkbox is checked.
	 *
	 * @return true, if is checked
	 */
	public boolean isDefaultVMArgChecked() {
		return new CheckBox(ArquillianPreferencePage.ENABLE_DEFAULT_VM_ARGUMENTS).isChecked();
	}

	/**
	 * Sets "Enable default VM arguments" checkbox.
	 *
	 * @param check the checkbox
	 */
	public void setDefaultVMArg(boolean check) {
		new CheckBox(ArquillianPreferencePage.ENABLE_DEFAULT_VM_ARGUMENTS).toggle(check);
	}

	/**
	 * Returns the current selection in the "Arquillian version:" combo
	 * 
	 * @return Arquillian version string
	 */
	public String getArquillianVersion() {
		return (new LabeledCombo(ARQUILLIAN_VERSION).getSelection());
	}

	/**
	 * Sets the "Arquillian version:" in the combo
	 * 
	 * @param The version string to be selected
	 */
	public void setArquillianVersion(String versionString) {
		new LabeledCombo(ARQUILLIAN_VERSION).setSelection(versionString);
	}

	/**
	 * Returns the defined VM arguments
	 * 
	 * @return VM arguments String
	 */
	public String getVMArgsText() {
		return (new DefaultText(1).getText());
	}

	/**
	 * Sets the VM arguments
	 * 
	 * @param The arguments String
	 */
	public void setVMArgsText(String argString) {
		new DefaultText(1).setText(argString);
	}

	/**
	 * Returns true when
	 * "Add the default VM arguments to the JUnit/TestNG launch configurations"
	 * checkbox is checked.
	 *
	 * @return true, if is checked
	 */
	public boolean isAddDefaultVMArgsToJUnitChecked() {
		return new CheckBox(ArquillianPreferencePage.ADD_DEFAULT_VM_ARGS_TO_JUNIT_LAUNCH).isChecked();
	}

	/**
	 * Sets
	 * "Add the default VM arguments to the JUnit/TestNG launch configurations"
	 * checkbox.
	 *
	 * @param check the checkbox
	 */
	public void setDefaultVMArgsToJUnit(boolean check) {
		new CheckBox(ArquillianPreferencePage.ADD_DEFAULT_VM_ARGS_TO_JUNIT_LAUNCH).toggle(check);
	}

	/**
	 * Returns true when
	 * "Add the default VM arguments to the existing launch configurations"
	 * checkbox is checked.
	 *
	 * @return true, if is checked
	 */
	public boolean isAddDefaultVMArgsToLaunchChecked() {
		return new CheckBox(ArquillianPreferencePage.ADD_DEFAULT_VM_ARGS_TO_LAUNCH).isChecked();
	}

	/**
	 * Sets "Add the default VM arguments to the existing launch configurations"
	 * checkbox.
	 *
	 * @param check the checkbox
	 */
	public void setDefaultVMArgsToLaunch(boolean check) {
		new CheckBox(ArquillianPreferencePage.ADD_DEFAULT_VM_ARGS_TO_LAUNCH).toggle(check);
	}

	/**
	 * Returns true when
	 * "Allow running an OS command when analyzing a deployment method" checkbox
	 * is checked.
	 *
	 * @return true, if is checked
	 */
	public boolean isAllowOSCommandWhenAnalyzingChecked() {
		return new CheckBox(ArquillianPreferencePage.ALLOW_OS_COMMAND_WHEN_ANALYZING).isChecked();
	}

	/**
	 * Sets "Allow running an OS command when analyzing a deployment method"
	 * checkbox.
	 *
	 * @param check the checkbox
	 */
	public void setAllowOSCommandWhenAnalyzing(boolean check) {
		new CheckBox(ArquillianPreferencePage.ALLOW_OS_COMMAND_WHEN_ANALYZING).toggle(check);
	}

	/**
	 * Returns true when
	 * "Allow setting a system property when analyzing a deployment method"
	 * checkbox is checked.
	 *
	 * @return true, if is checked
	 */
	public boolean isAllowSystemPropWhenAnalyzingChecked() {
		return new CheckBox(ArquillianPreferencePage.ALLOW_SYS_PROPERTY_WHEN_ANALYZING).isChecked();
	}

	/**
	 * Sets "Allow setting a system property when analyzing a deployment method"
	 * checkbox.
	 *
	 * @param check the checkbox
	 */
	public void setAllowSystemPropWhenAnalyzing(boolean check) {
		new CheckBox(ArquillianPreferencePage.ALLOW_SYS_PROPERTY_WHEN_ANALYZING).toggle(check);
	}

	/**
	 * Sets the checkbox on the selected item in the profiles table
	 * 
	 * @param profileName
	 */
	public void checkSelectedProfileCheckBox(String profileName) {
		new DefaultTable().getItem(profileName).setChecked(true);
	}

	/**
	 * Un-set the checkbox on the selected item in the profiles table
	 * 
	 * @param profileName
	 */
	public void unCheckSelectedProfileCheckBox(String profileName) {
		new DefaultTable().getItem(profileName).setChecked(false);
	}

	/**
	 * Return the status of the checkbox on the selected item in the profiles
	 * table
	 * 
	 * @return true, if is checked
	 */
	public boolean isSelectedProfileChecked(String profileName) {
		return new DefaultTable().getItem(profileName).isChecked();
	}

}
