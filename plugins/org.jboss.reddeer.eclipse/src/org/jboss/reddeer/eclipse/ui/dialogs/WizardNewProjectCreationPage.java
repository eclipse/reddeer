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
package org.jboss.reddeer.eclipse.ui.dialogs;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.jface.wizard.WizardPage;
import org.jboss.reddeer.swt.impl.button.CheckBox;
import org.jboss.reddeer.swt.impl.combo.LabeledCombo;
import org.jboss.reddeer.swt.impl.text.LabeledText;

/**
 * First page of New General Project wizard
 * 
 * @author vlado pakan
 * 
 */
public class WizardNewProjectCreationPage extends WizardPage {

	private final Logger log = Logger
			.getLogger(WizardNewProjectCreationPage.class);

	/**
	 * Sets a given project name.
	 * 
	 * @param projectName Project name
	 */
	public void setProjectName(String projectName) {
		log.debug("Set General Project name to '" + projectName + "'");
		new LabeledText("Project name:").setText(projectName);
	}

	/**
	 * Sets a given project location.
	 * 
	 * @param projectLocation Project location
	 */
	public void setProjectLocation(String projectLocation) {
		log.debug("Set Project location to '" + projectLocation + "'");
		new CheckBox("Use default location").toggle(false);
		new LabeledText("Location:").setText(projectLocation);
	}

	/**
	 * Adds a project to a given working set.
	 * 
	 * @param workingSet Working set
	 */
	public void addProjectToWorkingSet(String workingSet) {
		log.debug("Add Project to working set '" + workingSet + "'");
		new CheckBox("Add project to working sets").toggle(true);
		LabeledCombo cmbWorkingSet = new LabeledCombo("Working sets:");
		if (cmbWorkingSet.isEnabled()) {
			cmbWorkingSet.setText(workingSet);
		} else {
			throw new EclipseLayerException(
					"Combo box with Working sets is not enabled."
							+ " Probably no working set is defined");
		}
	}
}
