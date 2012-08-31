package org.jboss.reddeer.eclipse.ui.ide;

import org.jboss.reddeer.eclipse.jface.wizard.NewWizardDialog;

/**
 * New General Project wizard
 * 
 * @author vpakan
 * 
 */
public class NewGeneralProjectWizardDialog extends NewWizardDialog {
	
	public NewGeneralProjectWizardDialog() {
		super("General", "Project");
	}
	
	@Override
	public NewGeneralProjectWizardPage getFirstPage() {
		return new NewGeneralProjectWizardPage(this);
	}
	
	public NewGeneralProjectReferencesWizardPage getSecondPage(){
		return new NewGeneralProjectReferencesWizardPage(this);
	}
}
