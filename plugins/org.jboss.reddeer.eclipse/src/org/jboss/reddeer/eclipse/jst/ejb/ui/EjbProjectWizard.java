package org.jboss.reddeer.eclipse.jst.ejb.ui;

import org.jboss.reddeer.jface.wizard.NewWizardDialog;

/**
 * This class represents EJB Wizard dialog.
 * @author rawagner
 *
 */
public class EjbProjectWizard extends NewWizardDialog {
	
	public static final String CATEGORY="EJB";
	public static final String NAME="EJB Project";
	/**
	 * Default constructor.
	 */
	public EjbProjectWizard() {
		super(CATEGORY, NAME);
	}

}
