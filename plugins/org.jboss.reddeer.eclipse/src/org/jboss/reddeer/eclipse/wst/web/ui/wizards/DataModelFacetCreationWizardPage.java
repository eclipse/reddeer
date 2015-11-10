package org.jboss.reddeer.eclipse.wst.web.ui.wizards;

import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.core.condition.ShellWithTextIsActive;
import org.jboss.reddeer.jface.wizard.WizardPage;
import org.jboss.reddeer.swt.impl.button.CheckBox;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.combo.DefaultCombo;
import org.jboss.reddeer.swt.impl.combo.LabeledCombo;
import org.jboss.reddeer.swt.impl.group.DefaultGroup;
import org.jboss.reddeer.swt.impl.menu.ContextMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.text.LabeledText;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;

/**
 * Represents DataModelFacetCreationWizardPage.
 * This class implements common functionality for WebProject pages etc.
 * @author rawagner
 *
 */
public class DataModelFacetCreationWizardPage extends WizardPage {
	
	/**
	 * Sets project name.
	 * @param projectName project name to set
	 */
	public void setProjectName(final String projectName) {
		new LabeledText("Project name:").setText(projectName);
	}
	
	/**
	 * Returns project name.
	 * @return project name
	 */
	public String getProjectName() {
		return new LabeledText("Project name:").getText();
	}
	
	/**
	 * Use default location.
	 * @param useDefaultLocation true or false
	 */
	public void setUseDefaultLocation(final boolean useDefaultLocation) {
		new CheckBox(new DefaultGroup("Project location"),"Use default location").toggle(useDefaultLocation);
	}
	
	/**
	 * Checks if default location is used.
	 * @return true if default location is used, false otherwise
	 */
	public boolean isUseDefaultLocation() {
		return new CheckBox(new DefaultGroup("Project location"),"Use default location").isChecked();
	}
	
	/**
	 * Sets location.
	 * @param location to set
	 */
	public void setLocation(final String location) {
		new LabeledText("Location:").setText(location);
	}
	
	/**
	 * Returns current location.
	 * @return location
	 */
	public String getLocation() {
		return new LabeledText("Location:").getText();
	}
	
	/**
	 * Sets target runtime.
	 * @param targetRuntime to be set
	 */
	public void setTargetRuntime(final String targetRuntime) {
		new DefaultCombo(new DefaultGroup("Target runtime")).setSelection(targetRuntime);
	}
	
	/**
	 * Returns currently selected target runtime.
	 * @return current target runtime
	 */
	public String getTargetRuntime() {
		return new DefaultCombo(new DefaultGroup("Target runtime")).getSelection();
	}
	
	/**
	 * Sets configuration.
	 * @param configuration to be set
	 */
	public void setConfiguration(final String configuration) {
		new DefaultCombo(new DefaultGroup("Configuration")).setSelection(configuration);
	}
	
	/**
	 * Returns currently set configuration.
	 * @return configuration
	 */
	public String getConfiguration() {
		return new DefaultCombo(new DefaultGroup("Configuration")).getSelection();
	}
	
	/**
	 * Sets EAR membership.
	 * @param membership if EAR membership should be enabled
	 */
	public void setEARMembership(final boolean membership) {
		new CheckBox(new DefaultGroup("EAR membership"),"Add project to an EAR").toggle(membership);
	}
	
	/**
	 * Checks if EAR membership is enabled.
	 * @return true if EAR membership is enabled, false otherwise
	 */
	public boolean isEARMembership() {
		return new CheckBox(new DefaultGroup("EAR membership"),"Add project to an EAR").isChecked();
	}
	
	/**
	 * Sets EAR project name in EAR membership.
	 * @param name project name
	 */
	public void setEARProjectName(final String name) {
		new LabeledCombo(new DefaultGroup("EAR membership"),"EAR project name:").setText(name);
	}
	
	/**
	 * Returns EAR project name in EAR membership.
	 * @return EAR project name
	 */
	public String getEARProjectName() {
		return new LabeledCombo(new DefaultGroup("EAR membership"),"EAR project name:").getText();
	}
	
	/**
	 * Enables/Disables working sets.
	 * @param workingSets to be set
	 */
	public void setWorkingSets(final boolean workingSets) {
		new CheckBox(new DefaultGroup("Working sets"),"Add project to working sets").toggle(workingSets);
	}
	
	/**
	 * Sets working sets.
	 * @param workingSets to be set
	 */
	public void setWorkingSets(final String workingSets) {
		new LabeledCombo(new DefaultGroup("Working sets"),"Working sets:").setSelection(workingSets);
	}
	
	/**
	 * Returns current working sets.
	 * @return working sets
	 */
	public String getWorkingSets() {
		return new LabeledCombo(new DefaultGroup("Working sets"),"Working sets:").getSelection();
	}
	
	/**
	 * Activates project facet.
	 * @param facet facet name
	 * @param version facet version, can be null - than version is left default
	 */
	public void activateFacet(final String facet, final String version) {
		new PushButton("Modify...").click();
		new DefaultShell("Project Facets");
		new DefaultTreeItem(facet).select();
		new DefaultTreeItem(facet).setChecked(true);
		if (version != null) {
			new ContextMenu("Change Version...").select();
			new DefaultShell("Change Version");
			new LabeledCombo("Version:").setSelection(version);
			new PushButton("OK").click();
			new DefaultShell("Project Facets");
		}
		new PushButton("OK").click();
		new WaitWhile(new ShellWithTextIsActive("Project Facets"));
	}

}
