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
package org.jboss.reddeer.eclipse.jdt.ui;

import org.jboss.reddeer.jface.wizard.NewWizardDialog;

/**
 * Represents new enum wizard
 * 
 * @author rawagner
 *
 */
public class NewEnumWizardDialog extends NewWizardDialog {

	/**
	 * Construct the wizard with Java > Enum.
	 */
	public NewEnumWizardDialog() {
		super("Java", "Enum");
	}

	/**
	 * Gets the first page.
	 *
	 * @return the first page
	 */
	public NewEnumWizardPage getFirstPage() {
		return new NewEnumWizardPage();
	}

}
