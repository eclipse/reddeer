package org.jboss.reddeer.eclipse.jst.servlet.ui;

import org.jboss.reddeer.eclipse.jface.wizard.WizardPage;
import org.jboss.reddeer.swt.condition.ShellWithTextIsActive;
import org.jboss.reddeer.swt.impl.button.CheckBox;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.combo.LabeledCombo;
import org.jboss.reddeer.swt.impl.group.DefaultGroup;
import org.jboss.reddeer.swt.impl.menu.ContextMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.text.LabeledText;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
import org.jboss.reddeer.swt.wait.WaitWhile;

public class WebProjectFirstPage extends WizardPage{
	
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
		new LabeledCombo(new DefaultGroup("Target runtime")).setSelection(targetRuntime);
	}
	
	public String getTargetRuntime(){
		return new LabeledCombo(new DefaultGroup("Target runtime")).getSelection();
	}
	
	public void setDynamicWebModuleVersion(String version){
		new LabeledCombo(new DefaultGroup("Dynamic web module version")).setSelection(version);
	}
	
	public String getDynamicWebModuleVersion(){
		return new LabeledCombo(new DefaultGroup("Dynamic web module version")).getSelection();
	}
	
	public void setConfiguration(String configuration){
		new LabeledCombo(new DefaultGroup("Configuration")).setSelection(configuration);
	}
	
	public String getConString(){
		return new LabeledCombo(new DefaultGroup("Configuration")).getSelection();
	}
	
	public void setEARMembership(boolean membership){
		new CheckBox(new DefaultGroup("EAR membership"),"Add project to an EAR").toggle(membership);
	}
	
	public boolean isEARMembership(){
		return new CheckBox(new DefaultGroup("EAR membership"),"Add project to an EAR").isChecked();
	}
	
	public void setEARProjectName(String name){
		new LabeledCombo(new DefaultGroup("EAR membership"),"EAR project name:").setText(name);
	}
	
	public String getEARProjectName(){
		return new LabeledCombo(new DefaultGroup("EAR membership"),"EAR project name:").getText();
	}
	
	public void setWorkingSets(boolean workingSets){
		new CheckBox(new DefaultGroup("Working sets"),"Add project to working sets").toggle(workingSets);
	}
	
	public void setWorkingSets(String workingSets){
		new LabeledCombo(new DefaultGroup("Working sets"),"Working sets:").setSelection(workingSets);
	}
	
	public String getWorkingSets(){
		return new LabeledCombo(new DefaultGroup("Working sets"),"Working sets:").getSelection();
	}
	
	public void activateFacet(String facet, String version){
		new PushButton("Modify...").click();
		new DefaultShell("Project Facets");
		new DefaultTreeItem(facet).select();
		new DefaultTreeItem(facet).setChecked(true);
		if(version!=null){
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
