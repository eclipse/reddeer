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
	public void setConnectionProfile(String connectionProfile) {
		new DefaultTable(referencedComposite).select(connectionProfile);
	}

	/**
	 * Sets a given name.
	 * 
	 * @param name
	 *            Name
	 */
	public void setName(String name) {
		new LabeledText(referencedComposite, LABEL_NAME).setText(name);
	}

	/**
	 * Sets a given description.
	 * 
	 * @param description
	 *            Description
	 */
	public void setDescription(String description) {
		new LabeledText(referencedComposite, LABEL_DESCRIPTION).setText(description);
	}
}
