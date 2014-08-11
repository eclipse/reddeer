package org.jboss.reddeer.eclipse.jst.servlet.ui;

import org.jboss.reddeer.jface.wizard.NewWizardDialog;

/**
 * Wizard dialog for creating web project.
 */
public class WebProjectWizard extends NewWizardDialog{
	
	public static final String CATEGORY="Web";
	public static final String NAME="Dynamic Web Project";
	
	/**
	 * Construct the wizard with {@value #CATEGORY} > {@value #NAME}.
	 */
	public WebProjectWizard(){
		super(CATEGORY,NAME);
		addWizardPage(new WebProjectFirstPage(), 0);
		addWizardPage(new WebProjectSecondPage(), 1);
		addWizardPage(new WebProjectThirdPage(), 2);
	}

}
