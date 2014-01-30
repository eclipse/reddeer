package org.jboss.reddeer.eclipse.wst.server.ui.wizard;

import org.jboss.reddeer.eclipse.jface.wizard.WizardDialog;

/**
 * Represents the wizard for creating new servers. It provides access to the first wizard page {@link NewServerWizardPage}. 
 * Since the other pages depend on the selection of the concrete server type this wizard does not provide them.  
 * 
 * @author Lucia Jelinkova
 *
 */
public class NewServerWizardDialog extends WizardDialog {

	public static final String TITLE = "New Server";
	
	public NewServerWizardDialog() {
		addWizardPage(new NewServerWizardPage(), 0);
	}

	public NewServerWizardPage getFirstPage(){
		return new NewServerWizardPage();
	}
}
