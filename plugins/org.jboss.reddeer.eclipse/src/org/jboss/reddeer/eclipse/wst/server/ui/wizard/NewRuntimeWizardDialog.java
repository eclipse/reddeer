package org.jboss.reddeer.eclipse.wst.server.ui.wizard;

import org.jboss.reddeer.jface.wizard.WizardDialog;

/**
 * Represents the wizard for creating new servers. It provides access to the first wizard page {@link NewRuntimeWizardPage}. 
 * Since the other pages depend on the selection of the concrete runtime type this wizard does not provide them.  
 * 
 * @author Lucia Jelinkova
 *
 */
public class NewRuntimeWizardDialog extends WizardDialog{

	public static final String DIALOG_TITLE = "New Server Runtime Environment";

	@Deprecated
	public NewRuntimeWizardPage getFirstPage(){
		return new NewRuntimeWizardPage();
	}
}
