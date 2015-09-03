package org.jboss.reddeer.eclipse.wst.jsdt.ui.wizards;

import org.jboss.reddeer.jface.wizard.NewWizardDialog;

/**
 * Represents the wizard for creating new JavaScript project. It provides access to the first wizard page {@link JavaProjectWizardFirstPage}. 
 * 
 * @author Pavol Srna
 *
 */
public class JavaProjectWizardDialog extends NewWizardDialog {

	public static final String TITLE = "New JavaScript Project";
	
	public JavaProjectWizardDialog() {
		super("JavaScript", "JavaScript Project");
	}
	
}
