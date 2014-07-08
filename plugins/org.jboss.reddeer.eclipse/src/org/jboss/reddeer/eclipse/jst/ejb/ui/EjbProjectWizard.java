package org.jboss.reddeer.eclipse.jst.ejb.ui;

import org.jboss.reddeer.eclipse.jface.wizard.NewWizardDialog;
import org.jboss.reddeer.eclipse.jst.servlet.ui.WebProjectFirstPage;

/**
 * This class represents EJB Wizard dialog.
 * @author rawagner
 *
 */
public class EjbProjectWizard extends NewWizardDialog{
	
	public static final String CATEGORY="EJB";
	public static final String NAME="EJB Project";
	
	public EjbProjectWizard(){
		super(CATEGORY,NAME);
		addWizardPage(new WebProjectFirstPage(), 0);
	}

}
