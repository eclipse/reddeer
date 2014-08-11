package org.jboss.reddeer.eclipse.jst.j2ee.ui.project.facet;

import org.jboss.reddeer.jface.wizard.NewWizardDialog;

/**
 * Wizard dialog for creating EAR project.
 */
public class EarProjectWizard extends NewWizardDialog{
	
	public static final String CATEGORY="Java EE";
	public static final String NAME="Enterprise Application Project";
	
	/**
	 * Constructs the wizard with {@value #CATEGORY}.
	 */
	public EarProjectWizard(){
		super(CATEGORY,NAME);
		addWizardPage(new EarProjectFirstPage(),0);
		addWizardPage(new EarProjectInstallPage(),1);
	}

}
