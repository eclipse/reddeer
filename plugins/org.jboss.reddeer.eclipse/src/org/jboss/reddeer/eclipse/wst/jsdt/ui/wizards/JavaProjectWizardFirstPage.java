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

import org.jboss.reddeer.jface.wizard.WizardPage;
import org.jboss.reddeer.swt.impl.text.LabeledText;

/**
 * Represents the first page displayed when invoked {@link JavaProjectWizardDialog}
 * 
 * @author Pavol Srna
 *
 */
public class JavaProjectWizardFirstPage  extends WizardPage {

	/**
	 * Sets project name.
	 *
	 * @param name the new name
	 */
	public void setName(String name){
		new LabeledText("Project name:").setText(name);
	}
}
