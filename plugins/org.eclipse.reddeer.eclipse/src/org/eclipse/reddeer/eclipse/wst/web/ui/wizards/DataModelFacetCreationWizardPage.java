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
package org.eclipse.reddeer.eclipse.wst.web.ui.wizards;

import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.jface.wizard.WizardPage;
import org.eclipse.reddeer.swt.api.Shell;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.impl.button.CheckBox;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.combo.DefaultCombo;
import org.eclipse.reddeer.swt.impl.combo.LabeledCombo;
import org.eclipse.reddeer.swt.impl.group.DefaultGroup;
import org.eclipse.reddeer.swt.impl.menu.ContextMenuItem;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.swt.impl.text.LabeledText;
import org.eclipse.reddeer.swt.impl.tree.DefaultTree;
import org.eclipse.reddeer.swt.impl.tree.DefaultTreeItem;

/**
 * Represents DataModelFacetCreationWizardPage.
 * This class implements common functionality for WebProject pages etc.
 * @author rawagner
 *
 */
public class DataModelFacetCreationWizardPage extends WizardPage {
	
	public DataModelFacetCreationWizardPage(ReferencedComposite referencedComposite) {
		super(referencedComposite);
	}
	
	/**
	 * Sets project name.
	 * @param projectName project name to set
	 */
	public DataModelFacetCreationWizardPage setProjectName(final String projectName) {
		new LabeledText(this, "Project name:").setText(projectName);
		return this;
	}
	
	/**
	 * Returns project name.
	 * @return project name
	 */
	public String getProjectName() {
		return new LabeledText(this, "Project name:").getText();
	}
	
	/**
	 * Use default location.
	 * @param useDefaultLocation true or false
	 */
	public DataModelFacetCreationWizardPage setUseDefaultLocation(final boolean useDefaultLocation) {
		new CheckBox(new DefaultGroup(this, "Project location"),"Use default location").toggle(useDefaultLocation);
		return this;
	}
	
	/**
	 * Checks if default location is used.
	 * @return true if default location is used, false otherwise
	 */
	public boolean isUseDefaultLocation() {
		return new CheckBox(new DefaultGroup(this, "Project location"),"Use default location").isChecked();
	}
	
	/**
	 * Sets location.
	 * @param location to set
	 */
	public DataModelFacetCreationWizardPage setLocation(final String location) {
		new LabeledText(this, "Location:").setText(location);
		return this;
	}
	
	/**
	 * Returns current location.
	 * @return location
	 */
	public String getLocation() {
		return new LabeledText(this, "Location:").getText();
	}
	
	/**
	 * Sets target runtime.
	 * @param targetRuntime to be set
	 */
	public DataModelFacetCreationWizardPage setTargetRuntime(final String targetRuntime) {
		new DefaultCombo(new DefaultGroup(this, "Target runtime")).setSelection(targetRuntime);
		return this;
	}
	
	/**
	 * Returns currently selected target runtime.
	 * @return current target runtime
	 */
	public String getTargetRuntime() {
		return new DefaultCombo(new DefaultGroup(this, "Target runtime")).getSelection();
	}
	
	/**
	 * Sets configuration.
	 * @param configuration to be set
	 */
	public DataModelFacetCreationWizardPage setConfiguration(final String configuration) {
		new DefaultCombo(new DefaultGroup(this, "Configuration")).setSelection(configuration);
		return this;
	}
	
	/**
	 * Returns currently set configuration.
	 * @return configuration
	 */
	public String getConfiguration() {
		return new DefaultCombo(new DefaultGroup(this, "Configuration")).getSelection();
	}
	
	/**
	 * Sets EAR membership.
	 * @param membership if EAR membership should be enabled
	 */
	public DataModelFacetCreationWizardPage setEARMembership(final boolean membership) {
		new CheckBox(new DefaultGroup(this, "EAR membership"),"Add project to an EAR").toggle(membership);
		return this;
	}
	
	/**
	 * Checks if EAR membership is enabled.
	 * @return true if EAR membership is enabled, false otherwise
	 */
	public boolean isEARMembership() {
		return new CheckBox(new DefaultGroup(this, "EAR membership"),"Add project to an EAR").isChecked();
	}
	
	/**
	 * Sets EAR project name in EAR membership.
	 * @param name project name
	 */
	public DataModelFacetCreationWizardPage setEARProjectName(final String name) {
		new LabeledCombo(new DefaultGroup(this, "EAR membership"),"EAR project name:").setText(name);
		return this;
	}
	
	/**
	 * Returns EAR project name in EAR membership.
	 * @return EAR project name
	 */
	public String getEARProjectName() {
		return new LabeledCombo(new DefaultGroup(this, "EAR membership"),"EAR project name:").getText();
	}
	
	/**
	 * Enables/Disables working sets.
	 * @param workingSets to be set
	 */
	public DataModelFacetCreationWizardPage setWorkingSets(final boolean workingSets) {
		new CheckBox(new DefaultGroup(this, "Working sets"),"Add project to working sets").toggle(workingSets);
		return this;
	}
	
	/**
	 * Sets working sets.
	 * @param workingSets to be set
	 */
	public DataModelFacetCreationWizardPage setWorkingSets(final String workingSets) {
		new LabeledCombo(new DefaultGroup(this, "Working sets"),"Working sets:").setSelection(workingSets);
		return this;
	}
	
	/**
	 * Returns current working sets.
	 * @return working sets
	 */
	public String getWorkingSets() {
		return new LabeledCombo(new DefaultGroup(this, "Working sets"),"Working sets:").getSelection();
	}
	
	/**
	 * Activates project facet.
	 * @param facetPath path to Facet in tree of facets.
	 * @param version facet version, can be null - than version is left default
	 */
	public DataModelFacetCreationWizardPage activateFacet(final String version, final String... facetPath) {
		new PushButton(this, "Modify...").click();
		Shell facetsShell = new DefaultShell("Project Facets");
		DefaultTreeItem facetTreeItem = new DefaultTreeItem(new DefaultTree(facetsShell), facetPath);
		facetTreeItem.select();
		facetTreeItem.setChecked(true);
		if (version != null) {
			new ContextMenuItem("Change Version...").select();
			Shell versionShell = new DefaultShell("Change Version");
			new LabeledCombo(versionShell, "Version:").setSelection(version);
			new PushButton(versionShell, "OK").click();
			new WaitWhile(new ShellIsAvailable(versionShell));
		}
		new PushButton(facetsShell, "OK").click();
		new WaitWhile(new ShellIsAvailable(facetsShell));
		return this;
	}

}
