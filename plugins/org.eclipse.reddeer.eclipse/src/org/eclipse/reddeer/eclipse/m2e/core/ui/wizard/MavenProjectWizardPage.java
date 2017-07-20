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
package org.eclipse.reddeer.eclipse.m2e.core.ui.wizard;

import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.jface.wizard.WizardPage;
import org.eclipse.reddeer.swt.impl.button.CheckBox;
import org.eclipse.reddeer.swt.impl.combo.LabeledCombo;

public class MavenProjectWizardPage extends WizardPage{
	
	public MavenProjectWizardPage(ReferencedComposite referencedComposite) {
		super(referencedComposite);
	}
	
	/**
	 * Creates the simple project.
	 *
	 * @param toggle the toggle
	 */
	public MavenProjectWizardPage createSimpleProject(boolean toggle){
		new CheckBox(referencedComposite, "Create a simple project (skip archetype selection)").toggle(toggle);
		return this;
	}
	
	/**
	 * Checks if is creates the simple project.
	 *
	 * @return true, if is creates the simple project
	 */
	public boolean isCreateSimpleProject(){
		return new CheckBox(referencedComposite, "Create a simple project (skip archetype selection)").isChecked();
	}
	
	/**
	 * Use default workspace location.
	 *
	 * @param toggle the toggle
	 */
	public MavenProjectWizardPage useDefaultWorkspaceLocation(boolean toggle){
		new CheckBox(referencedComposite, "Use default Workspace location").toggle(toggle);
		return this;
	}
	
	/**
	 * Checks if is use default workspace location.
	 *
	 * @return true, if is use default workspace location
	 */
	public boolean isUseDefaultWorkspaceLocation(){
		return new CheckBox(referencedComposite, "Use default Workspace location").isChecked();
	}
	
	/**
	 * Sets the location.
	 *
	 * @param location the new location
	 */
	public MavenProjectWizardPage setLocation(String location){
		new LabeledCombo(referencedComposite, "Location:").setText(location);
		return this;
	}
	
	/**
	 * Gets the location.
	 *
	 * @return the location
	 */
	public String getLocation(){
		return new LabeledCombo(referencedComposite, "Location:").getText();
	}
	
	/**
	 * Adds the to working set.
	 *
	 * @param toggle the toggle
	 */
	public MavenProjectWizardPage addToWorkingSet(boolean toggle){
		new CheckBox(referencedComposite, "Add project(s) to working set").toggle(toggle);
		return this;
	}
	
	/**
	 * Checks if is adds the to working set.
	 *
	 * @return true, if is adds the to working set
	 */
	public boolean isAddToWorkingSet(){
		return new CheckBox(referencedComposite, "Add project(s) to working set").isChecked();
	}
	
	/**
	 * Sets the working set.
	 *
	 * @param workingSet the new working set
	 */
	public MavenProjectWizardPage setWorkingSet(String workingSet){
		new LabeledCombo(referencedComposite, "Working set:").setText(workingSet);
		return this;
	}
	
	/**
	 * Gets the working set.
	 *
	 * @return the working set
	 */
	public String getWorkingSet(){
		return new LabeledCombo(referencedComposite, "Working set:").getText();
	}
	
	

}
