package org.jboss.reddeer.eclipse.ui.ide;

import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.PackageExplorer;
import org.jboss.reddeer.eclipse.jface.wizard.NewWizardDialog;
import org.jboss.reddeer.eclipse.jface.wizard.WizardDialog;
import org.jboss.reddeer.swt.impl.text.LabeledText;

public class NewJavaClassDialog extends NewWizardDialog {
	
	public NewJavaClassDialog() {
		super("Java", "Class");
	}
	
	public void setName(String name){
		new LabeledText("Name:").setText(name);
	}
	
}
