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

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.workbench.core.condition.JobIsRunning;
import org.eclipse.reddeer.eclipse.datatools.connectivity.ui.dialogs.DriverDialog;
import org.eclipse.reddeer.eclipse.datatools.connectivity.ui.dse.views.DataSourceExplorerView;
import org.eclipse.reddeer.eclipse.datatools.connectivity.ui.preferences.DriverPreferences;
import org.eclipse.reddeer.eclipse.datatools.connectivity.ui.wizards.NewCPWizard;
import org.eclipse.reddeer.eclipse.datatools.ui.DatabaseProfile;
import org.eclipse.reddeer.eclipse.datatools.ui.DriverDefinition;
import org.eclipse.reddeer.eclipse.datatools.ui.DriverTemplate;
import org.eclipse.reddeer.workbench.ui.dialogs.WorkbenchPreferenceDialog;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.swt.api.Shell;
import org.eclipse.reddeer.swt.api.TableItem;
import org.eclipse.reddeer.swt.api.TreeItem;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.button.YesButton;
import org.eclipse.reddeer.swt.impl.menu.ContextMenuItem;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.swt.impl.table.DefaultTable;
import org.eclipse.reddeer.swt.impl.table.DefaultTableItem;
import org.eclipse.reddeer.swt.impl.tree.DefaultTreeItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class GenericConnectionProfileTest {

	private String fileName = "h2-1.4.178.jar";

	@Before
	public void prepare() {
		// Driver definition removal
		WorkbenchPreferenceDialog preferenceDialog = new WorkbenchPreferenceDialog();
		preferenceDialog.open();
		DriverPreferences preferencePage = new DriverPreferences(preferenceDialog);
		preferenceDialog.select(preferencePage);

		List<TableItem> items = new DefaultTable().getItems();
		for (int i = 0; i < items.size(); i++) {
			new DefaultTableItem(0).select();
			new PushButton("Remove").click();
			Shell removalShell = new DefaultShell("Confirm Driver Removal");
			new YesButton().click();
			new WaitWhile(new ShellIsAvailable(removalShell));
			new DefaultShell("Preferences");
		}
		preferenceDialog.ok();
		new WaitWhile(new JobIsRunning());

		// Connection profiles removal
		DataSourceExplorerView dse = new DataSourceExplorerView();
		dse.open();
		DefaultTreeItem item = new DefaultTreeItem("Database Connections");
		item.expand(TimePeriod.DEFAULT);
		List<TreeItem> cpitems = item.getItems();
		for (TreeItem i : cpitems) {
			i.select();
			new ContextMenuItem("Delete").select();
			Shell deleteShell = new DefaultShell("Delete confirmation");
			new YesButton().click();
			new WaitWhile(new ShellIsAvailable(deleteShell));
		}

		new WaitWhile(new JobIsRunning());
	}

	@Test
	public void genericConnectionProfileTest() {
		final String DRIVER_NAME = "Test H2 Driver";
		File drvFile = new File("target" + File.separator + fileName);

		DriverTemplate dt = new DriverTemplate("Generic JDBC Driver", "1.0");

		DriverDefinition dd = new DriverDefinition();
		dd.setDriverClass("org.h2.Driver");
		dd.setDriverLibrary(drvFile.getAbsolutePath());
		dd.setDriverName(DRIVER_NAME);
		dd.setDriverTemplate(dt);

		// DriverDefinition
		WorkbenchPreferenceDialog dialog = new WorkbenchPreferenceDialog();
		dialog.open();

		DriverPreferences preferencePage = new DriverPreferences(dialog);
		dialog.select(preferencePage);

		DriverDialog wizard = preferencePage.addDriverDefinition();
		wizard.selectDriverTemplate("Generic JDBC Driver", "1.0");
		wizard.setName(DRIVER_NAME);
		wizard.addDriverLibrary(drvFile.getAbsolutePath());
		wizard.setDriverClass("org.h2.Driver");
		wizard.ok();
		dialog.ok();

		String profile = "myDBProfile";
		DatabaseProfile dbProfile = new DatabaseProfile();
		dbProfile.setDatabase("sakila");
		dbProfile.setDatabase(profile);
		dbProfile.setDriverDefinition(dd);
		dbProfile.setHostname("jdbc:h2:tcp://localhost/sakila"); // URL
		dbProfile.setName("myDBProfile");
		dbProfile.setPassword("");
		dbProfile.setPort("8082");
		dbProfile.setUsername("sa");
		dbProfile.setVendor("Generic JDBC");

		// Create connection profile
		NewCPWizard w = new NewCPWizard();
		w.open();
		w.createDatabaseProfile(dbProfile);

		DataSourceExplorerView dataSourceExplorer = new DataSourceExplorerView();
		dataSourceExplorer.open();
		List<String> dbSources = dataSourceExplorer.getDatabaseConnections();
		assertTrue("Profile '" + profile + "' isn't available", dbSources.contains(profile));
	}
}