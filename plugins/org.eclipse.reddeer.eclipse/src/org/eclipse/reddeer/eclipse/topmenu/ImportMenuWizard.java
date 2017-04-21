/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.eclipse.topmenu;

import org.eclipse.reddeer.eclipse.ui.dialogs.ImportExportWizard;
import org.eclipse.reddeer.eclipse.ui.dialogs.ImportPage;
import org.eclipse.reddeer.jface.wizard.WizardDialog;

/**
 * Represents wizard which can be found in import wizard dialog (menu 'File -&gt; Import...').
 * @author rawagner
 *
 */
public abstract class ImportMenuWizard extends AbstractSelectionWizardDialog{
	
	/**
	 * Constructs new import Wizard that can be found in ImportExportWizard.
	 * @param shellText import wizard text when next is clicked (ie 'Import Maven Projects')
	 * @param wizardCategory import wizard category (ie 'Maven')
	 * @param wizardName import wizard name (ie 'Existing Maven Projects')
	 */
	public ImportMenuWizard(String shellText, String wizardCategory, String wizardName) {
		super(shellText, wizardCategory, wizardName);
	}
	
	/**
	 * Constructs new import Wizard that can be found in ImportExportWizard.
	 * @param shellText import wizard text when next is clicked (ie 'Import Maven Projects')
	 * @param wizardPath wizard path in import wizard (ie 'Maven, Existing Maven Projects')
	 */
	public ImportMenuWizard(String shellText, String[] wizardPath) {
		super(shellText, wizardPath);
	}
	
	@Override
	public void open(){
		if(!isOpen()){
			ImportExportWizard ieWizard = new ImportExportWizard(true);
			ieWizard.open();
			ImportPage importPage = new ImportPage();
			importPage.selectProject(wizardPath);
			ieWizard.next();
			setShell(new WizardDialog(matcher).getShell());
		}
	}

}
