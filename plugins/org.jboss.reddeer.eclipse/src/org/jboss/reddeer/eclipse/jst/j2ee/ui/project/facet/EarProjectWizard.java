package org.jboss.reddeer.eclipse.jst.j2ee.ui.project.facet;

import org.jboss.reddeer.eclipse.jface.wizard.NewWizardDialog;

public class EarProjectWizard extends NewWizardDialog{
	
	public static final String CATEGORY="Java EE";
	public static final String NAME="Enterprise Application Project";
	
	public EarProjectWizard(){
		super(CATEGORY,NAME);
		addWizardPage(new EarProjectFirstPage(),0);
		addWizardPage(new EarProjectInstallPage(),1);
	}

}
