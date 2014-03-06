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

public class EarProjectFirstPage extends WizardPage{
	
	public void setProjectName(String projectName){
		new LabeledText("Project name:").setText(projectName);
	}
	
	public String getProjectName(){
		return new LabeledText("Project name:").getText();
	}
	
	public void setUseDefaultLocation(boolean useDefaultLocation){
		new CheckBox(new DefaultGroup("Project location"),"Use default location").toggle(useDefaultLocation);
	}
	
	public boolean isUseDefaultLocation(){
		return new CheckBox(new DefaultGroup("Project location"),"Use default location").isChecked();
	}
	
	public void setLocation(String location){
		new LabeledText("Location:").setText(location);
	}
	
	public String getLocation(){
		return new LabeledText("Location:").getText();
	}
	
	public void setTargetRuntime(String targetRuntime){
		new DefaultCombo(new DefaultGroup("Target runtime")).setSelection(targetRuntime);
	}
	
	public String getTargetRuntime(){
		return new DefaultCombo(new DefaultGroup("Target runtime")).getSelection();
	}
	
	public void setEARVersion(String version){
		new DefaultCombo(new DefaultGroup("EAR version")).setSelection(version);
	}
	
	public String getEARVersion(){
		return new DefaultCombo(new DefaultGroup("EAR version")).getSelection();
	}
	
	public void setConfiguration(String configuration){
		new DefaultCombo(new DefaultGroup("Configuration")).setSelection(configuration);
	}
	
	public String getConfiguration(){
		return new DefaultCombo(new DefaultGroup("Configuration")).getSelection();
	}
	
	public void toggleWorkingSets(boolean workingSets){
		new CheckBox(new DefaultGroup("Working sets"),"Add project to working sets").toggle(workingSets);
	}
	
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
