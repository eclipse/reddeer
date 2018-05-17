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
package org.eclipse.reddeer.eclipse.datatools.connectivity.ui.wizards;

import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.jface.wizard.WizardPage;
import org.eclipse.reddeer.swt.impl.table.DefaultTable;
import org.eclipse.reddeer.swt.impl.text.LabeledText;

/**
 * A wizard page for selecting a connection profile.
 * 
 * @author apodhrad
 * 
 */
public class CPWizardSelectionPage extends WizardPage {

	public static final String TITLE = "New Connection Profile";
	public static final String LABEL_NAME = "Name:";
	public static final String LABEL_DESCRIPTION = "Description (optional):";

	/**
	 * Instantiates a new connection profile select page.
	 */
	public CPWizardSelectionPage(ReferencedComposite referencedComposite) {
		super(referencedComposite);
	}

	/**
	 * Sets a given connection profile.
	 * 
	 * @param connectionProfile
	 *            Connection profile
	 */
	public CPWizardSelectionPage setConnectionProfile(String connectionProfile) {
		new DefaultTable(this).select(connectionProfile);
		return this;
	}

	/**
	 * Sets a given name.
	 * 
	 * @param name
	 *            Name
	 */
	public CPWizardSelectionPage setName(String name) {
		new LabeledText(this, LABEL_NAME).setText(name);
		return this;
	}

	/**
	 * Sets a given description.
	 * 
	 * @param description
	 *            Description
	 */
	public CPWizardSelectionPage setDescription(String description) {
		new LabeledText(this, LABEL_DESCRIPTION).setText(description);
		return this;
	}
}
