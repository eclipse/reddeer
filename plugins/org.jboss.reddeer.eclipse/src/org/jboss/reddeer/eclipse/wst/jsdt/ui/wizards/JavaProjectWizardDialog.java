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
package org.jboss.reddeer.eclipse.wst.jsdt.ui.wizards;

import org.jboss.reddeer.jface.wizard.NewWizardDialog;

/**
 * Represents the wizard for creating new JavaScript project. It provides access to the first wizard page {@link JavaProjectWizardFirstPage}. 
 * 
 * @author Pavol Srna
 *
 */
public class JavaProjectWizardDialog extends NewWizardDialog {

	public static final String TITLE = "New JavaScript Project";
	
	/**
	 * Instantiates a new java project wizard dialog.
	 */
	public JavaProjectWizardDialog() {
		super("JavaScript", "JavaScript Project");
	}
	
}
