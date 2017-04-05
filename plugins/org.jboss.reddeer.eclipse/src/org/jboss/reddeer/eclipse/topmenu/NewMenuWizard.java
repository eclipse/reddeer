/******************************************************************************* 
 * Copyright (c) 2017 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.eclipse.topmenu;

import org.jboss.reddeer.eclipse.ui.dialogs.NewWizard;
import org.jboss.reddeer.eclipse.ui.dialogs.NewWizardSelectionPage;
import org.jboss.reddeer.jface.wizard.WizardDialog;

/**
 * Represents wizard which can be found in new wizard dialog (menu 'File -&gt; New -&gt; Other...').
 * @author rawagner
 *
 */
public abstract class NewMenuWizard extends AbstractSelectionWizardDialog{
	
	/**
	 * Constructs new Wizard that can be found in NewWizard.
	 * @param shellText new wizard text when next is clicked (ie 'New File')
	 * @param wizardCategory new wizard category (ie 'General')
	 * @param wizardName new wizard name (ie 'File')
	 */
	public NewMenuWizard(String shellText, String wizardCategory, String wizardName) {
		super(shellText, wizardCategory, wizardName);
	}
	
	/**
	 * Constructs new Wizard that can be found in NewWizard.
	 * @param shellText new wizard text when next is clicked (ie 'New File')
	 * @param wizardPath wizard path in new wizard (ie 'General, File')
	 */
	public NewMenuWizard(String shellText, String[] wizardPath) {
		super(shellText,wizardPath);
	}
	
	@Override
	public void open(){
		if(!isOpen()){
			NewWizard nw = new NewWizard();
			nw.open();
			NewWizardSelectionPage selectionPage = new NewWizardSelectionPage();
			selectionPage.selectProject(wizardPath);
			nw.next();
			setShell(new WizardDialog(matcher).getShell());
		}
	}
}