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

import org.jboss.reddeer.eclipse.ui.dialogs.ExportPage;
import org.jboss.reddeer.eclipse.ui.dialogs.ImportExportWizard;
import org.jboss.reddeer.jface.wizard.WizardDialog;

/**
 * Represents wizard which can be found in export wizard dialog (menu 'File -&gt; Export...').
 * @author rawagner
 *
 */
public abstract class ExportMenuWizard extends AbstractSelectionWizardDialog{
	
	/**
	 * Constructs new export Wizard that can be found in ImportExportWizard. 
	 * @param shellText export wizard text when next is clicked (ie 'JAR Export')
	 * @param wizardCategory export wizard category (ie 'Java')
	 * @param wizardName export wizard name  (ie 'JAR file')
	 */
	public ExportMenuWizard(String shellText, String wizardCategory, String wizardName) {
		super(shellText, wizardCategory, wizardName);
	}
	
	/**
	 * Constructs new export Wizard that can be found in ImportExportWizard.
	 * @param shellText export wizard text when next is clicked (ie 'JAR Export')
	 * @param wizardPath wizard path in export wizard (ie 'Java, JAR file')
	 */
	public ExportMenuWizard(String shellText, String[] wizardPath) {
		super(shellText, wizardPath);
	}
	
	@Override
	public void open(){
		if(!isOpen()){
			ImportExportWizard ieWizard = new ImportExportWizard(false);
			ieWizard.open();
			ExportPage exportPage = new ExportPage();
			exportPage.selectProject(wizardPath);
			ieWizard.next();
			setShell(new WizardDialog(matcher).getShell());
		}
	}

}
