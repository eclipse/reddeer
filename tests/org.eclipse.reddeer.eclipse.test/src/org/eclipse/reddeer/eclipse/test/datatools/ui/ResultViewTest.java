/*******************************************************************************
 * Copyright (c) 2017, 2018 Red Hat, Inc and others.
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
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.workbench.core.condition.JobIsKilled;
import org.eclipse.reddeer.workbench.core.condition.JobIsRunning;
import org.eclipse.reddeer.core.matcher.TreeItemRegexMatcher;
import org.eclipse.reddeer.eclipse.datatools.connectivity.ui.dialogs.DriverDialog;
import org.eclipse.reddeer.eclipse.datatools.connectivity.ui.dse.views.DataSourceExplorerView;
import org.eclipse.reddeer.eclipse.datatools.connectivity.ui.preferences.DriverPreferences;
import org.eclipse.reddeer.eclipse.datatools.connectivity.ui.wizards.NewCPWizard;
import org.eclipse.reddeer.eclipse.datatools.sqltools.result.ui.ResultView;
import org.eclipse.reddeer.eclipse.datatools.sqltools.result.ui.SQLResult;
import org.eclipse.reddeer.eclipse.datatools.sqltools.result.ui.SQLResultStatus;
import org.eclipse.reddeer.eclipse.datatools.ui.DatabaseProfile;
import org.eclipse.reddeer.eclipse.datatools.ui.DriverDefinition;
import org.eclipse.reddeer.eclipse.datatools.ui.DriverTemplate;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.swt.api.Shell;
import org.eclipse.reddeer.swt.api.TableItem;
import org.eclipse.reddeer.swt.api.TreeItem;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.button.YesButton;
import org.eclipse.reddeer.swt.impl.combo.DefaultCombo;
import org.eclipse.reddeer.swt.impl.combo.LabeledCombo;
import org.eclipse.reddeer.swt.impl.menu.ContextMenuItem;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.swt.impl.styledtext.DefaultStyledText;
import org.eclipse.reddeer.swt.impl.table.DefaultTable;
import org.eclipse.reddeer.swt.impl.table.DefaultTableItem;
import org.eclipse.reddeer.swt.impl.tree.DefaultTreeItem;
import org.eclipse.reddeer.workbench.impl.editor.DefaultEditor;
import org.eclipse.reddeer.workbench.ui.dialogs.WorkbenchPreferenceDialog;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class ResultViewTest {

	private static String fileName = "h2-1.4.199.jar";
	private static String profile = "myDBProfile";

	@BeforeClass
	public static void genericConnectionProfileTest() {

		// Driver definitions removal
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
		
		DataSourceExplorerView dataSourceExplorer = new DataSourceExplorerView();
		dataSourceExplorer.open();
		List<String> dbSources = dataSourceExplorer.getDatabaseConnections();
		if (!dbSources.contains(profile)) {

			createDatabaseProfile(profile);	
		}
	}		
	
	@After
	public void cleanup() {
		new WaitUntil(new JobIsKilled("SQL Statement Execution"), TimePeriod.DEFAULT, false);
	}
	
	private static void performSQLStatement(String profile, String statement) {
		DataSourceExplorerView dse = new DataSourceExplorerView();
		dse.open();
		@SuppressWarnings("unchecked")
		TreeItem connectionItem = new DefaultTreeItem(new TreeItemRegexMatcher("Database Connections"), new TreeItemRegexMatcher(profile+".*"));
		connectionItem.doubleClick();
		connectionItem.expand();
		new ContextMenuItem("Open SQL Scrapbook").select();
		DefaultEditor scrapbook = new DefaultEditor("SQL Scrapbook 0");
		
		List<String> items = new LabeledCombo("Type:").getItems();
		String generic1x = "Generic JDBC_1.x";
		String generic10 = "Generic JDBC_1.0";
		if (items.contains(generic1x)) {	
			new LabeledCombo("Type:").setSelection(generic1x);
		} else {
			new LabeledCombo("Type:").setSelection(generic10);
		}
			
		new DefaultCombo(1).setSelection(profile);
		DefaultStyledText text = new DefaultStyledText();
		text.setText(statement);
		new WaitWhile(new JobIsRunning());
		new ContextMenuItem("Execute All").select();
		
		new WaitUntil(new ShellIsAvailable("SQL Statement Execution"),TimePeriod.DEFAULT, false);
		new WaitWhile(new ShellIsAvailable("SQL Statement Execution"),TimePeriod.VERY_LONG);
		
		scrapbook.close(false);
	}

	private static void createDatabaseProfile(String profile) {
		final String DRIVER_NAME = "Test H2 Driver";
		File drvFile = new File("target" + File.separator + fileName);

		DriverTemplate dt = new DriverTemplate("Generic JDBC Driver", "1.0");

		DriverDefinition dd = new DriverDefinition();
		dd.setDriverClass("org.h2.Driver");
		dd.setDriverLibrary(drvFile.getAbsolutePath());
		dd.setDriverName(DRIVER_NAME);
		dd.setDriverTemplate(dt);

		// DriverDefinition
		WorkbenchPreferenceDialog d = new WorkbenchPreferenceDialog();
		d.open();
		d.select("Data Management", "Connectivity", "Driver Definitions");
		DriverPreferences preferencePage = new DriverPreferences(d);		
		DriverDialog wizard = preferencePage
				.addDriverDefinition();
		wizard.selectDriverTemplate("Generic JDBC Driver", "1.0");
		wizard.setName(DRIVER_NAME);
		wizard.addDriverLibrary(drvFile.getAbsolutePath());
		wizard.setDriverClass("org.h2.Driver");
		wizard.ok();
		d.ok();

		DatabaseProfile dbProfile = new DatabaseProfile();
		dbProfile.setDatabase("sakila");
		dbProfile.setDatabase(profile);
		dbProfile.setDriverDefinition(dd);
		dbProfile.setHostname("jdbc:h2:~/test");
		dbProfile.setName("myDBProfile");
		dbProfile.setPassword("");
		dbProfile.setPort("8082");
		dbProfile.setUsername("sa");
		dbProfile.setVendor("Generic JDBC");

		// Create connection profile
		NewCPWizard w = new NewCPWizard();
		w.open();
		w.createDatabaseProfile(dbProfile);
	}
	
	@Test
	public void testSQLResultViewFail() {
		testSQLResultView(true);
	}

	@Test
	public void testSQLResultViewSuccess() {
		testSQLResultView(false);
	}

	public void testSQLResultView(boolean fail) {
		dropTable();
		removeResults();

		
		if (fail) {
			performSQLStatement(profile, "CREATE TABLE test(INT ID,VARCHAR(40) NAME);");
		} else {
			performSQLStatement(profile, "CREATE TABLE test(ID INT,NAME VARCHAR(40));");
		}
		
		ResultView view = new ResultView();
		view.open();
		List<SQLResult> result = view.getResults();
		assertTrue(result.size() == 1);
		if (fail) {
			assertTrue(result.get(0).getStatus() == SQLResultStatus.FAILED || result.get(0).getStatus() == SQLResultStatus.STARTED);;
		} else {
			assertTrue(result.get(0).getStatus() == SQLResultStatus.SUCCEEDED || result.get(0).getStatus() == SQLResultStatus.STARTED);
		}

		dropTable();
		removeResults();
	}
	
	private void dropTable() {
		performSQLStatement(profile, "DROP TABLE TEST IF EXISTS;");				
	}
	
	private void removeResults() {
		ResultView view = new ResultView();
		view.open();

		view.removeAllResults();
		List<SQLResult> result = view.getResults();
		assertTrue(result.size() == 0);	
	}
}