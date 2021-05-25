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
package org.eclipse.reddeer.eclipse.jdt.ui.wizards;

import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.jface.wizard.WizardPage;
import org.eclipse.reddeer.swt.impl.button.CheckBox;
import org.eclipse.reddeer.swt.impl.button.RadioButton;
import org.eclipse.reddeer.swt.impl.combo.DefaultCombo;
import org.eclipse.reddeer.swt.impl.text.LabeledText;

/**
 * Represents first page of New Java Project Wizard.
 * 
 * @author rhopp, odockal
 *
 */

public class NewJavaProjectWizardPageOne extends WizardPage {
	
	public NewJavaProjectWizardPageOne(ReferencedComposite referencedComposite) {
		super(referencedComposite);
	}

	/**
	 * Sets a given project name.
	 * 
	 * @param projectName
	 *            Project name
	 */
	public NewJavaProjectWizardPageOne setProjectName(String projectName) {
		log.debug("Set General Project name to '" + projectName + "'");
		new LabeledText(this, "Project name:").setText(projectName);
		return this;
	}

	/**
	 * Sets whether to use default location.
	 * 
	 * @param check
	 *            Indicates whether to use default location
	 */
	public NewJavaProjectWizardPageOne useDefaultLocation(boolean check) {
		CheckBox box = new CheckBox(this, "Use default location");
		log.debug("Setting default location to " + check);
		box.toggle(check);
		return this;
	}

	/**
	 * Sets a given location.
	 * 
	 * @param location
	 *            Location
	 */
	public NewJavaProjectWizardPageOne setLocation(String location) {
		log.debug("Setting Location to '" + location + "'");
		LabeledText text = new LabeledText(this, "Location:");
		text.setText(location);
		return this;
	}
	
	/**
	 * Sets whether to use project specific JRE
	 * @param env
	 * 			String representation of specific JRE to use
	 * @return
	 * 			this page object
	 */
	public NewJavaProjectWizardPageOne useProjectSpecificJRE(String env) {
		log.debug("Setting active button 'Use a project specific JRE'");
		new RadioButton(referencedComposite, "Use a project specific JRE: ").toggle(true);
		log.debug("Selecting '" + env + "'");
		new DefaultCombo(referencedComposite, 0).setSelection(env);
		return this;
	}
	
	/**
	 * Sets whether to use execution JRE environment
	 * @param env
	 * 			String representation of specific JRE to use
	 * @return
	 * 			this page object
	 */
	public NewJavaProjectWizardPageOne useExecutionEnvironmentJRE(String env) {
		log.debug("Setting active button 'Use an execution environment JRE'");
		new RadioButton(referencedComposite, "Use an execution environment JRE:").toggle(true);
		log.debug("Selecting '" + env + "'");
		new DefaultCombo(referencedComposite, 0).setSelection(env);
		return this;
	}
	
	/**
	 * Sets whether to create module-info.java file 
	 * @param check
	 * 			Indicates whether to create module-info.java file
	 * @return
	 */
	public NewJavaProjectWizardPageOne createModuleInfoFile(boolean check) {
		CheckBox box = new CheckBox(this, "Create module-info.java file");
		log.debug("Setting 'Create module-info.java file' to " + check);
		box.toggle(check);
		return this;
	}

}
