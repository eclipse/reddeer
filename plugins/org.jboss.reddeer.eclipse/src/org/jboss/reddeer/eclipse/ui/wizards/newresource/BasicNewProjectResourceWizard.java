package org.jboss.reddeer.eclipse.ui.wizards.newresource;

import org.jboss.reddeer.eclipse.ui.dialogs.WizardNewProjectCreationPage;
import org.jboss.reddeer.eclipse.ui.dialogs.WizardNewProjectReferencePage;
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
	
	public WizardNewProjectCreationPage getFirstPage() {
		return new WizardNewProjectCreationPage();
	}
	
	public WizardNewProjectReferencePage getSecondPage(){
		return new WizardNewProjectReferencePage();
	}
}
