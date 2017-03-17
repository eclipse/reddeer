package org.jboss.reddeer.eclipse.mylyn.tasks.ui.wizards;

import org.jboss.reddeer.jface.wizard.WizardDialog;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;

public class NewRepositoryWizard extends WizardDialog{
	
	public NewRepositoryWizard(){
		new DefaultShell("Add Task Repository...");
	}

}
