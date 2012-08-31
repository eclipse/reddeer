package org.jboss.reddeer.jface.test.wizard;

import org.jboss.reddeer.eclipse.jface.wizard.NewWizardDialog;
import org.jboss.reddeer.eclipse.jface.wizard.WizardPage;

public class NewWizardDialogImpl extends NewWizardDialog {

	public NewWizardDialogImpl() {
		super();
	}
	
	@Override
	public WizardPage getFirstPage() {
		return null;
	}
}
