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
 * Superclass for export wizard dialogs. It opens the export wizard by clicking File &gt; Export... 
 * and selects an appropriate wizard in the dialog. 
 *   
 * @author Lucia Jelinkova
 * @since 0.6
 *
 */
public abstract class ExportWizardDialog extends TopMenuWizardDialog {

	public static final String DIALOG_TITLE = "Export";
	
	/**
	 * Constructor set path to specific export item in export dialog.
	 *
	 * @param path the path
	 */
	public ExportWizardDialog(String... path) {
		super(path);
	}

	@Override
	protected String getDialogTitle() {
		return DIALOG_TITLE;
	}
	
	@Override
	protected String[] getMenuPath() {
		return new String[]{"File", "Export..."};
	}
}
