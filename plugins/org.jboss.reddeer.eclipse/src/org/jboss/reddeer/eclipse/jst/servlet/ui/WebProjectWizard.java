package org.jboss.reddeer.eclipse.jst.servlet.ui;

import org.jboss.reddeer.eclipse.jface.wizard.NewWizardDialog;

public class WebProjectWizard extends NewWizardDialog{
	
	public static final String CATEGORY="Web";
	public static final String NAME="Dynamic Web Project";
	
	public WebProjectWizard(){
		super(CATEGORY,NAME);
		addWizardPage(new WebProjectFirstPage(), 0);
		addWizardPage(new WebProjectSecondPage(), 1);
		addWizardPage(new WebProjectThirdPage(), 2);
	}

}
