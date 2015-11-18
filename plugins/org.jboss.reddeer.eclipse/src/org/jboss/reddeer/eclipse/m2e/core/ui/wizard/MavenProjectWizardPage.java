package org.jboss.reddeer.eclipse.m2e.core.ui.wizard;

import org.jboss.reddeer.jface.wizard.WizardPage;
import org.jboss.reddeer.swt.impl.button.CheckBox;
import org.jboss.reddeer.swt.impl.combo.LabeledCombo;

public class MavenProjectWizardPage extends WizardPage{
	
	/**
	 * Creates the simple project.
	 *
	 * @param toggle the toggle
	 */
	public void createSimpleProject(boolean toggle){
		new CheckBox("Create a simple project (skip archetype selection)").toggle(toggle);
	}
	
	/**
	 * Checks if is creates the simple project.
	 *
	 * @return true, if is creates the simple project
	 */
	public boolean isCreateSimpleProject(){
		return new CheckBox("Create a simple project (skip archetype selection)").isChecked();
	}
	
	/**
	 * Use default workspace location.
	 *
	 * @param toggle the toggle
	 */
	public void useDefaultWorkspaceLocation(boolean toggle){
		new CheckBox("Use default Workspace location").toggle(toggle);
	}
	
	/**
	 * Checks if is use default workspace location.
	 *
	 * @return true, if is use default workspace location
	 */
	public boolean isUseDefaultWorkspaceLocation(){
		return new CheckBox("Use default Workspace location").isChecked();
	}
	
	/**
	 * Sets the location.
	 *
	 * @param location the new location
	 */
	public void setLocation(String location){
		new LabeledCombo("Location:").setText(location);
	}
	
	/**
	 * Gets the location.
	 *
	 * @return the location
	 */
	public String getLocation(){
		return new LabeledCombo("Location:").getText();
	}
	
	/**
	 * Adds the to working set.
	 *
	 * @param toggle the toggle
	 */
	public void addToWorkingSet(boolean toggle){
		new CheckBox("Add project(s) to working set").toggle(toggle);
	}
	
	/**
	 * Checks if is adds the to working set.
	 *
	 * @return true, if is adds the to working set
	 */
	public boolean isAddToWorkingSet(){
		return new CheckBox("Add project(s) to working set").isChecked();
	}
	
	/**
	 * Sets the working set.
	 *
	 * @param workingSet the new working set
	 */
	public void setWorkingSet(String workingSet){
		new LabeledCombo("Working set:").setText(workingSet);
	}
	
	/**
	 * Gets the working set.
	 *
	 * @return the working set
	 */
	public String getWorkingSet(){
		return new LabeledCombo("Working set:").getText();
	}
	
	

}
