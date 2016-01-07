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
package org.jboss.reddeer.eclipse.datatools.ui.wizard;

import org.jboss.reddeer.jface.wizard.WizardPage;
import org.jboss.reddeer.swt.impl.table.DefaultTable;
import org.jboss.reddeer.swt.impl.text.LabeledText;

/**
 * A wizard page for selecting a connection profile.
 * 
 * @author apodhrad
 * 
 */
public class ConnectionProfileSelectPage extends WizardPage {

	public static final String TITLE = "New Connection Profile";
	public static final String LABEL_NAME = "Name:";
	public static final String LABEL_DESCRIPTION = "Description (optional):";

	/**
	 * Instantiates a new connection profile select page.
	 */
	public ConnectionProfileSelectPage() {
		super();
	}

	/**
	 * Sets a given connection profile.
	 * 
	 * @param connectionProfile
	 *            Connection profile
	 */
	public void setConnectionProfile(String connectionProfile) {
		new DefaultTable().select(connectionProfile);
	}

	/**
	 * Sets a given name.
	 * 
	 * @param name
	 *            Name
	 */
	public void setName(String name) {
		new LabeledText(LABEL_NAME).setText(name);
	}

	/**
	 * Sets a given description.
	 * 
	 * @param description
	 *            Description
	 */
	public void setDescription(String description) {
		new LabeledText(LABEL_DESCRIPTION).setText(description);
	}
}
