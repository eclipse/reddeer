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
package org.eclipse.reddeer.eclipse.datatools.connectivity.ui.preferences;

import java.util.List;

import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.eclipse.datatools.connectivity.ui.dialogs.DriverDialog;
import org.eclipse.reddeer.eclipse.datatools.ui.DriverDefinition;
import org.eclipse.reddeer.jface.preference.PreferencePage;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;

/**
 * Preference page for managing driver definitions.
 * 
 * @author apodhrad
 * 
 */
public class DriverPreferences extends PreferencePage {

	/**
	 * Construct a preference page with Data Management &gt; Connectivity &gt; Driver Definitions.
	 */
	public DriverPreferences(ReferencedComposite referencedComposite) {
		super(referencedComposite, new String[] {"Data Management", "Connectivity", "Driver Definitions"});
	}

	/**
	 * Returns all available driver definitions. Not yet implemented!
	 * 
	 * @return List of driver definitions
	 */
	public List<DriverDefinition> getDriverDefinitions() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Push 'Add..' button and return a wizard for creating a driver definition.
	 * 
	 * @return a wizard for creating a driver definition
	 */
	public DriverDialog addDriverDefinition() {
		new PushButton(this, "Add...").click();
		new DefaultShell("New Driver Definition");
		return new DriverDialog();
	}

	/**
	 * Edits the driver definition. Not yet implemented!
	 */
	public void editDriverDefinition() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Removes the driver definition. Not yet implemented!
	 */
	public void removeDriverDefinition() {
		throw new UnsupportedOperationException();
	}
}
