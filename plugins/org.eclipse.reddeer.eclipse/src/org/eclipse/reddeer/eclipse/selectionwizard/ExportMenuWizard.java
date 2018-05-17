/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.eclipse.selectionwizard;

import org.eclipse.reddeer.eclipse.ui.dialogs.ImportExportWizard;
import org.eclipse.reddeer.jface.window.Openable;

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
	protected Openable getOpenAction() {
		return new SelectionWizardOpenable(new ImportExportWizard(false), wizardPath, matcher);
	}

}
