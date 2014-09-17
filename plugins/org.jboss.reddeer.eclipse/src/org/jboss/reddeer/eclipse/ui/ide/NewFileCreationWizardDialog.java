package org.jboss.reddeer.eclipse.ui.ide;

import org.jboss.reddeer.jface.wizard.NewWizardDialog;

/**
 * Represents new file creation wizard dialog (General -> File)
 * 
 * @author jjankovi
 *
 */
public class NewFileCreationWizardDialog extends NewWizardDialog {

	/**
	 * Constructs the wizard with "General" > "File".
	 */
	public NewFileCreationWizardDialog() {
		super("General", "File");
	}
	
	public NewFileCreationWizardPage getFirstPage() {
		return new NewFileCreationWizardPage();
	}

}
