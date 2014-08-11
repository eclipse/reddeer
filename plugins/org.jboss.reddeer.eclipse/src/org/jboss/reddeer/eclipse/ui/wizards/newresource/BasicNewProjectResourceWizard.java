package org.jboss.reddeer.eclipse.ui.wizards.newresource;

import org.jboss.reddeer.jface.wizard.NewWizardDialog;
import org.jboss.reddeer.eclipse.ui.dialogs.WizardNewProjectCreationPage;
import org.jboss.reddeer.eclipse.ui.dialogs.WizardNewProjectReferencePage;

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
	
	@Override
	public WizardNewProjectCreationPage getFirstPage() {
		return new WizardNewProjectCreationPage();
	}
	
	public WizardNewProjectReferencePage getSecondPage(){
		return new WizardNewProjectReferencePage();
	}
}
