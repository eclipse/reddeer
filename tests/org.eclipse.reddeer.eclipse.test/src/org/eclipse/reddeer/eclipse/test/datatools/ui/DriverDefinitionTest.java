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
package org.eclipse.reddeer.eclipse.test.datatools.ui;

import java.io.File;

import org.eclipse.reddeer.eclipse.datatools.connectivity.ui.preferences.DriverPreferences;
import org.eclipse.reddeer.eclipse.datatools.ui.DriverDefinition;
import org.eclipse.reddeer.eclipse.datatools.ui.DriverTemplate;
import org.eclipse.reddeer.workbench.ui.dialogs.WorkbenchPreferenceDialog;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.swt.impl.table.DefaultTable;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 * 
 */
@RunWith(RedDeerSuite.class)
public class DriverDefinitionTest {

	@Test
	public void driverDefinitionTest() {
		WorkbenchPreferenceDialog dialog = new WorkbenchPreferenceDialog();
		dialog.open();
		
		DriverPreferences preferencePage = new DriverPreferences(dialog);
		dialog.select(preferencePage);
		preferencePage.addDriverDefinition().create(createTestDriverDefinition());
		// test if a driver was successfully created
		new DefaultTable().getItem("Test HSLQDB Driver");
		dialog.ok();
	}

	private DriverDefinition createTestDriverDefinition() {
		DriverDefinition dd = new DriverDefinition();
		dd.setDriverTemplate(new DriverTemplate("HSQLDB JDBC Driver", "1.8"));
		dd.setDriverName("Test HSLQDB Driver");
		dd.setDriverLibrary(new File("target/lib/hsqldb-1.8.0.10.jar").getAbsolutePath());
		return dd;
	}
}
