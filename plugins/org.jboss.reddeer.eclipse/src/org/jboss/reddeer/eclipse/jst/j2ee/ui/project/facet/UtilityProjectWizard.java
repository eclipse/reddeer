package org.jboss.reddeer.eclipse.jst.j2ee.ui.project.facet;

import org.jboss.reddeer.jface.wizard.NewWizardDialog;

/**
 * Represents utility project wizard
 * @author rawagner
 *
 */
public class UtilityProjectWizard extends NewWizardDialog{
	
	public static final String CATEGORY="Java EE";
	public static final String NAME="Utility Project";
	
	/**
	 * Constructs the wizard with {@value #NAME}.
	 */
	public UtilityProjectWizard(){
		super(CATEGORY,NAME);
	}

}
