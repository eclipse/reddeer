package org.jboss.reddeer.eclipse.ui.ide;

import org.jboss.reddeer.eclipse.jface.wizard.NewWizardDialog;
import org.jboss.reddeer.swt.impl.button.CheckBox;
import org.jboss.reddeer.swt.impl.text.LabeledText;

public class NewJavaClassDialog extends NewWizardDialog {
	
	public NewJavaClassDialog() {
		super("Java", "Class");
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
