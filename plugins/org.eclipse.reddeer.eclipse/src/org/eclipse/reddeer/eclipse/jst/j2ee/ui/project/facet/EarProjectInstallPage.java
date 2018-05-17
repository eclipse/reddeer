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
package org.eclipse.reddeer.eclipse.jst.j2ee.ui.project.facet;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.eclipse.jst.j2ee.wizard.DefaultJ2EEComponentCreationWizard;
import org.eclipse.reddeer.jface.wizard.WizardPage;
import org.eclipse.reddeer.swt.api.TableItem;
import org.eclipse.reddeer.swt.impl.button.CheckBox;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.table.DefaultTable;
import org.eclipse.reddeer.swt.impl.text.LabeledText;

/**
 * The second wizard page for creating  EAR project.
 */
public class EarProjectInstallPage extends WizardPage{
	
	public EarProjectInstallPage(ReferencedComposite referencedComposite) {
		super(referencedComposite);
	}
	
	/**
	 * Opens a wizard page for adding new module.
	 * @return Wizard page for adding new module
	 */
	public DefaultJ2EEComponentCreationWizard newModule(){
		new PushButton(this, "New Module...").click();
		return new DefaultJ2EEComponentCreationWizard();
	}
	
	/**
	 * Selects all.
	 */
	public EarProjectInstallPage selectAll(){
		new PushButton(this, "Select All").click();
		return this;
	}
	
	/**
	 * Deselects all.
	 */
	public EarProjectInstallPage deselectAll(){
		new PushButton(this, "Deselect All").click();
		return this;
	}
	
	/**
	 * Returns list of module dependencies.
	 * 
	 * @return List of module dependencies
	 */
	public List<String> getJavaEEModuleDependencies(){
		List<String> modules = new ArrayList<String>();
		for(TableItem i: new DefaultTable(this).getItems()){
			modules.add(i.getText());
		}
		return modules;
	}
	
	/**
	 * Sets whether to select a given dependency.
	 * 
	 * @param dependency Dependency
	 * @param toggle Whether to select the dependency
	 */
	public EarProjectInstallPage toggleJavaEEModuleDependency(String dependency, boolean toggle){
		new DefaultTable(this).getItem(dependency).setChecked(toggle);
		return this;
	}
	
	/**
	 * Returns whether a given dependency is selected.
	 * 
	 * @param dependency Dependency
	 * @return Whether the dependency is selected
	 */
	public boolean isJavaEEModuleDependency(String dependency){
		return new DefaultTable(this).getItem(dependency).isChecked();
	}
	
	/**
	 * Sets a given content directory.
	 * 
	 * @param directory Content directory
	 */
	public EarProjectInstallPage setContentDirectory(String directory){
		new LabeledText(this, "Content directory:").setText(directory);
		return this;
	}
	
	/**
	 * Sets whether to generate application XML.
	 * 
	 * @param toggle Indicates whether to generate application XML
	 */
	public EarProjectInstallPage toggleGenerateApplicationXML(boolean toggle){
		new CheckBox(this, "Generate application.xml deployment descriptor").toggle(toggle);
		return this;
	}
	
	/**
	 * Returns whether application.xml is generated.
	 * 
	 * @return Whether application.xml is generated
	 */
	public boolean isGenerateApplicationXML(){
		return new CheckBox(this, "Generate application.xml deployment descriptor").isChecked();
	}

}
