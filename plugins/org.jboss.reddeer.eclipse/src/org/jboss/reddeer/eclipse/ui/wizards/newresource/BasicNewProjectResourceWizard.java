package org.jboss.reddeer.eclipse.ui.wizards.newresource;

import org.jboss.reddeer.jface.wizard.NewWizardDialog;

/**
 * New General Project wizard
 * 
 * @author vpakan
 * 
 */
public class BasicNewProjectResourceWizard extends NewWizardDialog {
	
	/**
	 * Constructs the wizard with "General" > "Project".
	 */
	public BasicNewProjectResourceWizard() {
		super("General", "Project");
	}
}
