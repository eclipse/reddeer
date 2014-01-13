package org.jboss.reddeer.eclipse.jdt.ui;

import org.jboss.reddeer.eclipse.jface.wizard.NewWizardDialog;

public class NewAnnotationCreationWizard extends NewWizardDialog{
	
	public NewAnnotationCreationWizard() {
		super("Java", "Annotation");
	}
	
	@Override
	public NewAnnotationWizardPage getFirstPage() {
		return new NewAnnotationWizardPage();
	}

}
