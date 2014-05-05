package org.jboss.reddeer.eclipse.jdt.ui;

import org.jboss.reddeer.eclipse.jface.wizard.NewWizardDialog;

public class NewJavaClassWizardDialog extends NewWizardDialog {
	
	public NewJavaClassWizardDialog() {
		super("Java", "Class");
	}
	
	@Override
	public NewJavaClassWizardPage getFirstPage() {
		return new NewJavaClassWizardPage();
	}
}
