package org.jboss.reddeer.eclipse.jdt.ui;

import org.jboss.reddeer.jface.wizard.NewWizardDialog;

/**
 * Wizard dialog for creating an annotation.
 */
public class NewAnnotationCreationWizard extends NewWizardDialog {

	/**
	 * Constructs the wizard with Java > Annotation.
	 */
	public NewAnnotationCreationWizard() {
		super("Java", "Annotation");
	}

	@Override
	public NewAnnotationWizardPage getFirstPage() {
		return new NewAnnotationWizardPage();
	}

}
