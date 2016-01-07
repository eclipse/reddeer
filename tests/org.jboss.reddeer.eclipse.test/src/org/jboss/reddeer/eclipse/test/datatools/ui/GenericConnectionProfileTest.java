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
package org.jboss.reddeer.eclipse.test.datatools.ui;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.core.condition.JobIsRunning;
import org.jboss.reddeer.core.condition.ShellWithTextIsActive;
import org.jboss.reddeer.eclipse.datatools.ui.DatabaseProfile;
import org.jboss.reddeer.eclipse.datatools.ui.DriverDefinition;
import org.jboss.reddeer.eclipse.datatools.ui.DriverTemplate;
import org.jboss.reddeer.eclipse.datatools.ui.preference.DriverDefinitionPreferencePage;
import org.jboss.reddeer.eclipse.datatools.ui.view.DataSourceExplorer;
import org.jboss.reddeer.eclipse.datatools.ui.wizard.ConnectionProfileWizard;
import org.jboss.reddeer.eclipse.datatools.ui.wizard.DriverDefinitionPage;
import org.jboss.reddeer.eclipse.datatools.ui.wizard.DriverDefinitionWizard;
import org.jboss.reddeer.workbench.ui.dialogs.WorkbenchPreferenceDialog;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.api.TableItem;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.button.YesButton;
import org.jboss.reddeer.swt.impl.menu.ContextMenu;
import org.jboss.reddeer.swt.impl.table.DefaultTable;
import org.jboss.reddeer.swt.impl.table.DefaultTableItem;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
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
		DriverDefinitionPreferencePage preferencePage = new DriverDefinitionPreferencePage();
		preferenceDialog.select(preferencePage);
		
		List<TableItem> items = new DefaultTable().getItems();
		for (int i = 0; i < items.size(); i++) {
			new DefaultTableItem(0).select();
			new PushButton("Remove").click();
			new WaitUntil(new ShellWithTextIsActive("Confirm Driver Removal"));
			new YesButton().click();
			new WaitWhile(new ShellWithTextIsActive("Confirm Driver Removal"));
			new WaitUntil(new ShellWithTextIsActive("Preferences"));
		}
		preferenceDialog.ok();
		new WaitWhile(new JobIsRunning());
		
		// Connection profiles removal
		DataSourceExplorer dse = new DataSourceExplorer();
		dse.open();
		DefaultTreeItem item = new DefaultTreeItem("Database Connections");
		item.expand(TimePeriod.NORMAL);
		List<TreeItem> cpitems = item.getItems();
		for (TreeItem i : cpitems) {
			i.select();
			new ContextMenu("Delete").select();
			new WaitUntil(new ShellWithTextIsActive("Delete confirmation"));
			new YesButton().click();
			new WaitWhile(new ShellWithTextIsActive("Delete confirmation"));				
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
		
		DriverDefinitionPreferencePage preferencePage = new DriverDefinitionPreferencePage();
		dialog.select(preferencePage);
		
		DriverDefinitionWizard wizard = preferencePage.addDriverDefinition();
		DriverDefinitionPage page = new DriverDefinitionPage();
		page.selectDriverTemplate("Generic JDBC Driver", "1.0");
		page.setName(DRIVER_NAME);
		page.addDriverLibrary(drvFile.getAbsolutePath());
		page.setDriverClass("org.h2.Driver");
		wizard.finish();
		dialog.ok();

		
		String profile = "myDBProfile";
		DatabaseProfile dbProfile = new DatabaseProfile();
		dbProfile.setDatabase("sakila");
		dbProfile.setDatabase(profile);
		dbProfile.setDriverDefinition(dd);
		dbProfile.setHostname("jdbc:h2:tcp://localhost/sakila");	// URL 
		dbProfile.setName("myDBProfile");
		dbProfile.setPassword("");
		dbProfile.setPort("8082");
		dbProfile.setUsername("sa");
		dbProfile.setVendor("Generic JDBC");
		
		// Create connection profile
		ConnectionProfileWizard w = new ConnectionProfileWizard();		
		w.open();
		w.createDatabaseProfile(dbProfile);

		DataSourceExplorer dataSourceExplorer = new DataSourceExplorer();
		dataSourceExplorer.open();
		List<String> dbSources = dataSourceExplorer.getDatabaseConnections();
		assertTrue("Profile '" + profile + "' isn't available", dbSources.contains(profile));
	}
}