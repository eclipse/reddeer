/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.eclipse.jdt.ui.wizards;

import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.jface.wizard.WizardPage;
import org.eclipse.reddeer.swt.impl.button.CheckBox;
import org.eclipse.reddeer.swt.impl.text.LabeledText;

/**
 * Represents first page of New Java Project Wizard.
 * 
 * @author rhopp
 *
 */

public class NewJavaProjectWizardPageOne extends WizardPage {
	
	public NewJavaProjectWizardPageOne(ReferencedComposite referencedComposite) {
		super(referencedComposite);
	}

	/**
	 * Sets a given project name.
	 * 
	 * @param projectName
	 *            Project name
	 */
	public void setProjectName(String projectName) {
		log.debug("Set General Project name to '" + projectName + "'");
		new LabeledText(referencedComposite, "Project name:").setText(projectName);
	}

	/**
	 * Sets whether to use default location.
	 * 
	 * @param check
	 *            Indicates whether to use dafualt location
	 */
	public void useDefaultLocation(boolean check) {
		CheckBox box = new CheckBox(referencedComposite, "Use default location");
		log.debug("Setting default location to " + check);
		box.toggle(check);
	}

	/**
	 * Sets a given location.
	 * 
	 * @param location
	 *            Location
	 */
	public void setLocation(String location) {
		log.debug("Setting Location to '" + location + "'");
		LabeledText text = new LabeledText(referencedComposite, "Location:");
		text.setText(location);
	}

}
