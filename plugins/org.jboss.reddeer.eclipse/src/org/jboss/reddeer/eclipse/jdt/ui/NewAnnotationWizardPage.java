package org.jboss.reddeer.eclipse.jdt.ui;

import org.jboss.reddeer.eclipse.jface.wizard.WizardPage;
import org.jboss.reddeer.swt.impl.button.CheckBox;
import org.jboss.reddeer.swt.impl.button.RadioButton;
import org.jboss.reddeer.swt.impl.text.LabeledText;

public class NewAnnotationWizardPage extends WizardPage {
	
	public void setPackage(String packageName) {
		new LabeledText("Package:").setText(packageName);
	}
	
	public void setSourceFolder(String sourceFolder){
		new LabeledText("Source folder:").setText(sourceFolder);
	}
	
	public void setEnclosingType(boolean enclosing){
		new CheckBox("Enclosing type:").toggle(enclosing);
	}
	
	public void setName(String name){
		new LabeledText("Name:").setText(name);
	}
	
	public void setGenerateComments(boolean generate){
		new CheckBox("Generate comments").toggle(generate);
	}
	
	public void setPublic(boolean isPublic){
		new RadioButton("public").toggle(isPublic);
	}
	
	public void setDefault(boolean isDefault){
		new RadioButton("default").toggle(isDefault);
	}
	
	public void setPrivate(boolean isPrivate){
		new RadioButton("private").toggle(isPrivate);
	}

	public void setProtected(boolean isProtected){
		new RadioButton("protected").toggle(isProtected);
	}
	
	

}
