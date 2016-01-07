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
package org.jboss.reddeer.eclipse.datatools.ui.preference;

import java.util.List;

import org.jboss.reddeer.eclipse.datatools.ui.DriverDefinition;
import org.jboss.reddeer.eclipse.datatools.ui.wizard.DriverDefinitionWizard;
import org.jboss.reddeer.jface.preference.PreferencePage;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;

/**
 * Preference page for managing driver definitions.
 * 
 * @author apodhrad
 * 
 */
public class DriverDefinitionPreferencePage extends PreferencePage {

	/**
	 * Construct a preference page with Data Management > Connectivity > Driver Definitions.
	 */
	public DriverDefinitionPreferencePage() {
		super(new String[] {"Data Management", "Connectivity", "Driver Definitions"});
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
	public DriverDefinitionWizard addDriverDefinition() {
		new PushButton("Add...").click();
		new DefaultShell("New Driver Definition");
		return new DriverDefinitionWizard();
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
