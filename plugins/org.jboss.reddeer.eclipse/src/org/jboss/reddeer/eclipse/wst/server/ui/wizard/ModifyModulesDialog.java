package org.jboss.reddeer.eclipse.wst.server.ui.wizard;

import org.jboss.reddeer.jface.wizard.WizardDialog;

/**
 * Wizard for adding and removing modules on the server. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class ModifyModulesDialog extends WizardDialog {

	public static final String DIALOG_TITLE = "Add and Remove...";
	
	@Deprecated
	public ModifyModulesPage getFirstPage() {
		return new ModifyModulesPage();
	}
}
