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
package org.jboss.reddeer.eclipse.jdt.ui.ide;

import org.jboss.reddeer.jface.wizard.NewWizardDialog;

/**
 * Wizard dialog for creating new Java project.
 */
public class NewJavaProjectWizardDialog extends NewWizardDialog {

	/**
	 * Constructs the wizard with Java &gt; Java Project.
	 */
	public NewJavaProjectWizardDialog() {
		super("Java", "Java Project");
	}
	
}
