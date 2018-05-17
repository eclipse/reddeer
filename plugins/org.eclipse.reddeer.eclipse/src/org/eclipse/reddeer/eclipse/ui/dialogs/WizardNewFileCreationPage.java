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
package org.eclipse.reddeer.eclipse.ui.dialogs;

import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.jface.wizard.WizardPage;
import org.eclipse.reddeer.swt.impl.text.LabeledText;

/**
 * Represents first page of new file creation wizard dialog
 * 
 * @author jjankovi
 *
 */
public class WizardNewFileCreationPage extends WizardPage {

	/**
	 * Instantiates a new new file creation wizard page.
	 */
	public WizardNewFileCreationPage(ReferencedComposite referencedComposite) {
		super(referencedComposite);
	}
	
	/**
	 * Sets a given file name.
	 * 
	 * @param fileName File name
	 */
	public WizardNewFileCreationPage setFileName(String fileName) {
		new LabeledText(this, "File name:").setText(fileName);
		return this;
	}
	
	/**
	 * Sets a given folder path.
	 * 
	 * @param folderPath Folder path
	 */
	public WizardNewFileCreationPage setFolderPath(String... folderPath) {
		StringBuilder builder = new StringBuilder();
		for (String pathElement : folderPath) {
			builder.append(pathElement);
			builder.append("/");
		}
		new LabeledText(this, "Enter or select the parent folder:").setText(builder.toString());
		return this;
	}
	
}
