/*******************************************************************************
 * Copyright (c) 2018 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.eclipse.jdt.ui.wizards;

import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.jface.wizard.WizardPage;
import org.eclipse.reddeer.swt.impl.button.CheckBox;
import org.eclipse.reddeer.swt.impl.text.LabeledText;

/**
 * New Java Project wizard page number two
 * @author odockal
 *
 */
public class NewJavaProjectWizardPageTwo extends WizardPage {

	public NewJavaProjectWizardPageTwo(ReferencedComposite referencedComposite) {
		super(referencedComposite);
	}
	
	/**
	 * Sets whether to create module-info.java file. Section was moved to 
	 * the first page of Java Project creation wizard. 
	 * @param check
	 * 			Indicates whether to create module-info.java file
	 * @return
	 */
	@Deprecated
	public NewJavaProjectWizardPageTwo createModuleInfoFile(boolean check) {
		CheckBox box = new CheckBox(this, "Create module-info.java file");
		log.debug("Setting 'Create module-info.java file' to " + check);
		box.toggle(check);
		return this;
	}
	
	/**
	 * Sets whether to allow output folder for source folders
	 * @param check
	 *			Indicates whether to allow output folder for source folders
	 * @return 
	 * 			this object
	 */
	public NewJavaProjectWizardPageTwo allowOutputFoldersForSourceFolders(boolean check) {
		CheckBox box = new CheckBox(this, "Allow output folders for source folders");
		log.debug("Setting 'Allow output folders for source folders' to " + check);
		box.toggle(check);
		return this;
	}
	
	/**
	 * Sets default output folder location
	 * @param location
	 * 				Default output folder location
	 * @return 
	 * 				this object
	 */
	public NewJavaProjectWizardPageTwo setDefaultOutputFolder(String location) {
		log.debug("Setting 'Default output folder' to '" + location + "'");
		LabeledText text = new LabeledText(this, "Default output folder:");
		text.setText(location);
		return this;
	}

}
