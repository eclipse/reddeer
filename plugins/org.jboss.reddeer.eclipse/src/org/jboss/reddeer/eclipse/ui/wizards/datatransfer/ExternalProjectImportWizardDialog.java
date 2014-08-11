package org.jboss.reddeer.eclipse.ui.wizards.datatransfer;

import org.jboss.reddeer.jface.wizard.ImportWizardDialog;

/**
 * Wizard for importing external projects into the workspace. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class ExternalProjectImportWizardDialog extends ImportWizardDialog {

	/**
	 * Construct the wizard with "General" > "Existing Projects into Workspace".
	 */
	public ExternalProjectImportWizardDialog() {
		super(new String[]{"General", "Existing Projects into Workspace"});
	}

	@Override
	public WizardProjectsImportPage getFirstPage() {
		return new WizardProjectsImportPage(this, 0);
	}

}
