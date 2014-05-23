package org.jboss.reddeer.eclipse.mylyn.tasks.ui.wizards;

import org.jboss.reddeer.eclipse.jface.wizard.NewWizardDialog;

/**
 * Represents new file creation wizard dialog (General -> File)
 * 
 * @author ldimaggi
 *
 */
public class NewRepositoryWizard extends NewWizardDialog {

	/**
	 * Construct the wizard with "General" > "File".
	 */
	public NewRepositoryWizard() {
		super("General", "File");
	}

}
