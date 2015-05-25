package org.jboss.reddeer.eclipse.jdt.ui.preferences.internal;

import org.jboss.reddeer.jface.wizard.WizardPage;
import org.jboss.reddeer.swt.impl.list.DefaultList;

/**
 * First page of "Add JRE" wizard accesible from Preferences->Java->Installed
 * JREs.
 * 
 * @author rhopp
 *
 */

public class VMTypePage extends WizardPage {

	/**
	 * Selects desired type.
	 * 
	 * @param type
	 */
	
	public void selectType(String type){
		new DefaultList().select(type);
	}
	
}
