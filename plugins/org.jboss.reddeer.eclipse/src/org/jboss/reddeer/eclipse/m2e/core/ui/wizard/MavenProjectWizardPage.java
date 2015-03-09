package org.jboss.reddeer.eclipse.m2e.core.ui.wizard;

import org.jboss.reddeer.jface.wizard.WizardPage;
import org.jboss.reddeer.swt.impl.button.CheckBox;
import org.jboss.reddeer.swt.impl.combo.LabeledCombo;

public class MavenProjectWizardPage extends WizardPage{
	
	public void createSimpleProject(boolean toggle){
		new CheckBox("Create a simple project (skip archetype selection)").toggle(toggle);
	}
	
	public boolean isCreateSimpleProject(){
		return new CheckBox("Create a simple project (skip archetype selection)").isChecked();
	}
	
	public void useDefaultWorkspaceLocation(boolean toggle){
		new CheckBox("Use default Workspace location").toggle(toggle);
	}
	
	public boolean isUseDefaultWorkspaceLocation(){
		return new CheckBox("Use default Workspace location").isChecked();
	}
	
	public void setLocation(String location){
		new LabeledCombo("Location:").setText(location);
	}
	
	public String getLocation(){
		return new LabeledCombo("Location:").getText();
	}
	
	public void addToWorkingSet(boolean toggle){
		new CheckBox("Add project(s) to working set").toggle(toggle);
	}
	
	public boolean isAddToWorkingSet(){
		return new CheckBox("Add project(s) to working set").isChecked();
	}
	
	public void setWorkingSet(String workingSet){
		new LabeledCombo("Working set:").setText(workingSet);
	}
	
	public String getWorkingSet(){
		return new LabeledCombo("Working set:").getText();
	}
	
	

}
