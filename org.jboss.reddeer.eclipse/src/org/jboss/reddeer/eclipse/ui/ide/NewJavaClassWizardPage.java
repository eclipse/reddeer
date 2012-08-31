package org.jboss.reddeer.eclipse.ui.ide;

import org.jboss.reddeer.eclipse.jface.wizard.WizardDialog;
import org.jboss.reddeer.eclipse.jface.wizard.WizardPage;
import org.jboss.reddeer.swt.impl.button.CheckBox;
import org.jboss.reddeer.swt.impl.text.LabeledText;

public class NewJavaClassWizardPage extends WizardPage {

	public NewJavaClassWizardPage(WizardDialog wizardDialog) {
		super(wizardDialog, 0);
	}
	
	public void setName(String name){
		new LabeledText("Name:").setText(name);
	}
	
	public void setPackage(String packageName) {
		new LabeledText("Package:").setText(packageName);
	}
	
	public void setStaticMainMethod(boolean setMainMethod) {
//		new CheckBox("public static void main(String[] args)").toggle(setMainMethod);
		new CheckBox(4).toggle(setMainMethod); // initiate with label doesnt work
	}
}
