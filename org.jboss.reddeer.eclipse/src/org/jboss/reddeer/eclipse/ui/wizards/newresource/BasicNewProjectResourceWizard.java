package org.jboss.reddeer.eclipse.ui.wizards.newresource;

import org.jboss.reddeer.eclipse.jface.wizard.NewWizardDialog;
import org.jboss.reddeer.eclipse.ui.dialogs.WizardNewProjectCreationPage;
import org.jboss.reddeer.eclipse.ui.dialogs.WizardNewProjectReferencePage;

/**
 * New General Project wizard
 * 
 * @author vpakan
 * 
 */
public class BasicNewProjectResourceWizard extends NewWizardDialog {
	
	public BasicNewProjectResourceWizard() {
		super("General", "Project");
	}
	
	@Override
	public WizardNewProjectCreationPage getFirstPage() {
		return new WizardNewProjectCreationPage(this);
	}
	
	public WizardNewProjectReferencePage getSecondPage(){
		return new WizardNewProjectReferencePage(this);
	}
}
