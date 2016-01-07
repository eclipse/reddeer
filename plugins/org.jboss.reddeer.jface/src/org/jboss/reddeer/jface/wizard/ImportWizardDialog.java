/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.jface.wizard;


/**
 * Superclass for import wizard dialogs. It opens the import wizard by clicking File -> Import... 
 * and selects an appropriate wizard in the dialog. 
 *   
 * @author Lucia Jelinkova
 * @since 0.6
 *
 */
public abstract class ImportWizardDialog extends TopMenuWizardDialog {

	public static final String DIALOG_TITLE = "Import";
	
	/**
	 * Constructor set path to the specific import item in import dialog.
	 * @param path to the specific item in import dialog
	 */
	public ImportWizardDialog(String... path) {
		super(path);
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.jface.wizard.TopMenuWizardDialog#getDialogTitle()
	 */
	@Override
	protected String getDialogTitle() {
		return DIALOG_TITLE;
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.jface.wizard.TopMenuWizardDialog#getMenuPath()
	 */
	@Override
	protected String[] getMenuPath() {
		return new String[]{"File", "Import..."};
	}
}
