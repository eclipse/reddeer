package org.jboss.reddeer.eclipse.ui.ide;

import org.jboss.reddeer.eclipse.jface.wizard.NewWizardDialog;

/**
 * Represents new file creation wizard dialog (General -> File)
 * 
 * @author jjankovi
 *
 */
public class NewFileCreationWizardDialog extends NewWizardDialog {

	public NewFileCreationWizardDialog() {
		super("General", "File");
	}
	
	@Override
	public NewFileCreationWizardPage getFirstPage() {
		return new NewFileCreationWizardPage();
	}

}
