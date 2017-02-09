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
 * Superclass for new object wizard dialogs. It opens the new object wizard by clicking File &gt; New &gt; Other... 
 * and selects an appropriate wizard in the dialog. 
 * 
 * @author vpakan
 * @since 0.6
 *
 */
public abstract class NewWizardDialog extends TopMenuWizardDialog {
	
	public static final String DIALOG_TITLE = "New";
	
	/**
	 * Constructor set path to the specific item in new wizard dialog.
	 * @param path to the specific item in new wizard dialog
	 */
	public NewWizardDialog(String... path) {
		super(path);
	}
	
	@Override
	protected String getDialogTitle() {
		return DIALOG_TITLE;
	}
	
	@Override
	protected String[] getMenuPath() {
		return new String[]{"File", "New", "Other..."};
	}
}
