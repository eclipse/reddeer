package org.jboss.reddeer.eclipse.m2e.core.ui.wizard;

import org.jboss.reddeer.jface.wizard.NewWizardDialog;

/**
 * New Maven SCM Project Wizard Dialog
 * @author rawagner
 *
 */
public class MavenCheckoutWizard extends NewWizardDialog{
	
	public static final String CATEGORY="Maven";
	public static final String NAME="Check out Maven Projects from SCM";
	

	/**
	 * Default constructor.
	 */
	public MavenCheckoutWizard(){
		super(CATEGORY,NAME);
	}

}
