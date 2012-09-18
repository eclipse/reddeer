package org.jboss.reddeer.eclipse.ui.wizards.datatransfer;

import org.jboss.reddeer.eclipse.jface.wizard.ImportWizardDialog;
import org.jboss.reddeer.eclipse.jface.wizard.WizardPage;

/**
 * Wizard for importing external projects into the workspace. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class ExternalProjectImportWizardDialog extends ImportWizardDialog {

	public ExternalProjectImportWizardDialog() {
		super(new String[]{"General", "Existing Projects into Workspace"});
	}

	@Override
	public WizardProjectsImportPage getFirstPage() {
		return new WizardProjectsImportPage(this, 0);
	}

}
