package org.jboss.reddeer.eclipse.jdt.ui;

import org.jboss.reddeer.jface.wizard.NewWizardDialog;

/**
 * Wizard dialog for creating a java class.
 */
public class NewJavaClassWizardDialog extends NewWizardDialog {
	
	/**
	 * Constructs the wizard with Java > Class.
	 */
	public NewJavaClassWizardDialog() {
		super("Java", "Class");
	}
	
	@Deprecated
	public NewJavaClassWizardPage getFirstPage() {
		return new NewJavaClassWizardPage();
	}
}
