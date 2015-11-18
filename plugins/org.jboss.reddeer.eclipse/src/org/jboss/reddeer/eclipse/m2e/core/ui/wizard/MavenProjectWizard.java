package org.jboss.reddeer.eclipse.m2e.core.ui.wizard;

import org.jboss.reddeer.jface.wizard.NewWizardDialog;

/**
 * New Maven Project Wizard Dialog
 * @author rawagner
 *
 */
public class MavenProjectWizard extends NewWizardDialog{
	
	public static final String CATEGORY="Maven";
	public static final String NAME="Maven Project";
	
	/**
	 * Default constructor.
	 */
	public MavenProjectWizard(){
		super(CATEGORY,NAME);
	}

}
