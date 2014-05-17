package org.jboss.reddeer.eclipse.rse.ui.wizard;

import org.jboss.reddeer.eclipse.jface.wizard.NewWizardDialog;

/**
 * This class represents the New Connection wizard dialog.
 * @author Pavol Srna
 *
 */
public class NewConnectionWizardDialog extends NewWizardDialog{
	
	public static final String TITLE = "New Connection";
	
	public NewConnectionWizardDialog() {
		super("Remote System Explorer", "Connection");
	}

}
