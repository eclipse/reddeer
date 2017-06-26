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
package org.eclipse.reddeer.eclipse.ui.wizards.newresource;

import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.eclipse.exception.EclipseLayerException;
import org.eclipse.reddeer.jface.wizard.WizardPage;
import org.eclipse.reddeer.swt.impl.button.CheckBox;
import org.eclipse.reddeer.swt.impl.combo.LabeledCombo;
import org.eclipse.reddeer.swt.impl.text.LabeledText;

/**
 * First page of New General Project wizard
 * 
 * @author vlado pakan
 * 
 */
public class BasicNewProjectResourceWizardFirstPage extends WizardPage{

	private final Logger log = Logger
			.getLogger(BasicNewProjectResourceWizardFirstPage.class);
	
	public BasicNewProjectResourceWizardFirstPage(ReferencedComposite referencedComposite) {
		super(referencedComposite);
	}

	/**
	 * Sets a given project name.
	 * 
	 * @param projectName Project name
	 */
	public void setProjectName(String projectName) {
		log.debug("Set General Project name to '" + projectName + "'");
		new LabeledText(referencedComposite, "Project name:").setText(projectName);
	}

	/**
	 * Sets a given project location.
	 * 
	 * @param projectLocation Project location
	 */
	public void setProjectLocation(String projectLocation) {
		log.debug("Set Project location to '" + projectLocation + "'");
		new CheckBox(referencedComposite, "Use default location").toggle(false);
		new LabeledText(referencedComposite, "Location:").setText(projectLocation);
	}

	/**
	 * Adds a project to a given working set.
	 * 
	 * @param workingSet Working set
	 */
	public void addProjectToWorkingSet(String workingSet) {
		log.debug("Add Project to working set '" + workingSet + "'");
		new CheckBox(referencedComposite, "Add project to working sets").toggle(true);
		LabeledCombo cmbWorkingSet = new LabeledCombo(referencedComposite, "Working sets:");
		if (cmbWorkingSet.isEnabled()) {
			cmbWorkingSet.setText(workingSet);
		} else {
			throw new EclipseLayerException(
					"Combo box with Working sets is not enabled."
							+ " Probably no working set is defined");
		}
	}
}
