package org.jboss.reddeer.eclipse.jst.j2ee.ui.project.facet;

import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.eclipse.jface.wizard.WizardPage;
import org.jboss.reddeer.eclipse.jst.j2ee.wizard.NewJ2EEComponentSelectionPage;
import org.jboss.reddeer.swt.api.TableItem;
import org.jboss.reddeer.swt.impl.button.CheckBox;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.table.DefaultTable;
import org.jboss.reddeer.swt.impl.text.LabeledText;

public class EarProjectInstallPage extends WizardPage{
	
	public NewJ2EEComponentSelectionPage newModule(){
		new PushButton("New Module...").click();
		new DefaultShell("Create default Java EE modules.");
		return new NewJ2EEComponentSelectionPage();
	}
	
	public void selectAll(){
		new PushButton("Select All").click();
	}
	
	public void deselectAll(){
		new PushButton("Deselect All").click();
	}
	
	public List<String> getJavaEEModuleDependencies(){
		List<String> modules = new ArrayList<String>();
		for(TableItem i: new DefaultTable().getItems()){
			modules.add(i.getText());
		}
		return modules;
	}
	
	public void toggleJavaEEModuleDependency(String dependency, boolean toggle){
		new DefaultTable().getItem(dependency).setChecked(toggle);
	}
	
	public boolean isJavaEEModuleDependency(String dependency){
		return new DefaultTable().getItem(dependency).isChecked();
	}
	
	public void setContentDirectory(String directory){
		new LabeledText("Content directory:").setText(directory);
	}
	
	public void toggleGenerateApplicationXML(boolean toggle){
		new CheckBox("Generate application.xml deployment descriptor").toggle(toggle);
	}
	
	public boolean isGenerateApplicationXML(){
		return new CheckBox("Generate application.xml deployment descriptor").isChecked();
	}

}
