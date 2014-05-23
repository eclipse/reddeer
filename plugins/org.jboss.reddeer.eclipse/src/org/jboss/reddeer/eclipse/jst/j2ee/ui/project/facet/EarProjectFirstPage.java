package org.jboss.reddeer.eclipse.jst.j2ee.ui.project.facet;

import java.util.List;

import org.jboss.reddeer.eclipse.jface.wizard.WizardPage;
import org.jboss.reddeer.swt.condition.ShellWithTextIsActive;
import org.jboss.reddeer.swt.impl.button.CheckBox;
import org.jboss.reddeer.swt.impl.button.OkButton;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.combo.DefaultCombo;
import org.jboss.reddeer.swt.impl.group.DefaultGroup;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.table.DefaultTableItem;
import org.jboss.reddeer.swt.impl.text.LabeledText;
import org.jboss.reddeer.swt.wait.WaitWhile;

/**
 * The first Wizard page for creating EAR project.
 */
public class EarProjectFirstPage extends WizardPage{
	
	/**
	 * Sets a given project name.
	 * 
	 * @param projectName Project name
	 */
	public void setProjectName(String projectName){
		new LabeledText("Project name:").setText(projectName);
	}
	
	/**
	 * Returns a project name.
	 * 
	 * @return Project name
	 */
	public String getProjectName(){
		return new LabeledText("Project name:").getText();
	}
	
	/**
	 * Sets whether to use default location.
	 * 
	 * @param useDefaultLocation Indicates whether to use default location
	 */
	public void setUseDefaultLocation(boolean useDefaultLocation){
		new CheckBox(new DefaultGroup("Project location"),"Use default location").toggle(useDefaultLocation);
	}
	
	/**
	 * Returns whether default locations is used.
	 * 
	 * @return Whether default locations is used
	 */
	public boolean isUseDefaultLocation(){
		return new CheckBox(new DefaultGroup("Project location"),"Use default location").isChecked();
	}
	
	/**
	 * Sets a given location.
	 * 
	 * @param location Location
	 */
	public void setLocation(String location){
		new LabeledText("Location:").setText(location);
	}
	
	/**
	 * Returns a location.
	 * 
	 * @return Location
	 */
	public String getLocation(){
		return new LabeledText("Location:").getText();
	}
	
	/**
	 * Sets a given target runtime.
	 * 
	 * @param targetRuntime Target runtime
	 */
	public void setTargetRuntime(String targetRuntime){
		new DefaultCombo(new DefaultGroup("Target runtime")).setSelection(targetRuntime);
	}
	
	/**
	 * Returns target runtime.
	 * 
	 * @return Target runtime
	 */
	public String getTargetRuntime(){
		return new DefaultCombo(new DefaultGroup("Target runtime")).getSelection();
	}
	
	/**
	 * Sets EAR verion.
	 * 
	 * @param version EAR version
	 */
	public void setEARVersion(String version){
		new DefaultCombo(new DefaultGroup("EAR version")).setSelection(version);
	}
	
	/**
	 * Returns EAR version.
	 * 
	 * @return EAR version
	 */
	public String getEARVersion(){
		return new DefaultCombo(new DefaultGroup("EAR version")).getSelection();
	}
	
	/**
	 * Sets a given configuration.
	 * 
	 * @param configuration Configuration
	 */
	public void setConfiguration(String configuration){
		new DefaultCombo(new DefaultGroup("Configuration")).setSelection(configuration);
	}
	
	/**
	 * Returns the selected configuration.
	 * 
	 * @return Selected configuration
	 */
	public String getConfiguration(){
		return new DefaultCombo(new DefaultGroup("Configuration")).getSelection();
	}
	
	/**
	 * Sets whether to add project to working sets.
	 * 
	 * @param workingSets Whether to add project to working sets
	 */
	public void toggleWorkingSets(boolean workingSets){
		new CheckBox(new DefaultGroup("Working sets"),"Add project to working sets").toggle(workingSets);
	}
	
	/**
	 * Sets a given list of working sets.
	 * 
	 * @param workingSets List of working sets
	 */
	public void setWorkingSets(List<String> workingSets){
		new PushButton(new DefaultGroup("Working sets"), "Select...").click();
		new DefaultShell("Select Working Sets");
		for(String workingSet: workingSets){
			new DefaultTableItem(workingSet).setChecked(true);
		}
		new OkButton().click();
		new WaitWhile(new ShellWithTextIsActive("Select Working Sets"));
	}

}
