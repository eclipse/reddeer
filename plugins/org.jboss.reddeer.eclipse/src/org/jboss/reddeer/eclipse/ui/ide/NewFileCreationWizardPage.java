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
package org.jboss.reddeer.eclipse.ui.ide;

import org.jboss.reddeer.jface.wizard.WizardPage;
import org.jboss.reddeer.swt.impl.text.LabeledText;

/**
 * Represents first page of new file creation wizard dialog
 * 
 * @author jjankovi
 *
 */
public class NewFileCreationWizardPage extends WizardPage {

	/**
	 * Instantiates a new new file creation wizard page.
	 */
	public NewFileCreationWizardPage() {
		super();
	}
	
	/**
	 * Sets a given file name.
	 * 
	 * @param fileName File name
	 */
	public void setFileName(String fileName) {
		new LabeledText("File name:").setText(fileName);
	}
	
	/**
	 * Sets a given folder path.
	 * 
	 * @param folderPath Folder path
	 */
	public void setFolderPath(String... folderPath) {
		StringBuilder builder = new StringBuilder();
		for (String pathElement : folderPath) {
			builder.append(pathElement);
			builder.append("/");
		}
		new LabeledText("Enter or select the parent folder:").setText(builder.toString());
	}
	
}
