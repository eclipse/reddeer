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
import org.jboss.reddeer.workbench.core.condition.JobIsRunning;
import org.jboss.reddeer.core.condition.ShellWithTextIsAvailable;
import org.jboss.reddeer.core.matcher.TreeItemRegexMatcher;
import org.jboss.reddeer.eclipse.datatools.sqltools.result.ui.ResultView;
import org.jboss.reddeer.eclipse.datatools.sqltools.result.ui.SQLResult;
import org.jboss.reddeer.eclipse.datatools.sqltools.result.ui.SQLResultStatus;
import org.jboss.reddeer.eclipse.datatools.ui.DatabaseProfile;
import org.jboss.reddeer.eclipse.datatools.ui.DriverDefinition;
import org.jboss.reddeer.eclipse.datatools.ui.DriverTemplate;
import org.jboss.reddeer.eclipse.datatools.ui.preference.DriverDefinitionPreferencePage;
import org.jboss.reddeer.eclipse.datatools.ui.view.DataSourceExplorer;
import org.jboss.reddeer.eclipse.datatools.ui.wizard.ConnectionProfileWizard;
import org.jboss.reddeer.eclipse.datatools.ui.wizard.DriverDefinitionPage;
import org.jboss.reddeer.eclipse.datatools.ui.wizard.DriverDefinitionWizard;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.api.TableItem;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.condition.ShellIsAvailable;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.button.YesButton;
import org.jboss.reddeer.swt.impl.combo.DefaultCombo;
import org.jboss.reddeer.swt.impl.combo.LabeledCombo;
import org.jboss.reddeer.swt.impl.menu.ContextMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.styledtext.DefaultStyledText;
import org.jboss.reddeer.swt.impl.table.DefaultTable;
import org.jboss.reddeer.swt.impl.table.DefaultTableItem;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
import org.jboss.reddeer.workbench.impl.editor.DefaultEditor;
import org.jboss.reddeer.workbench.ui.dialogs.WorkbenchPreferenceDialog;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class ResultViewTest {

	private static String fileName = "h2-1.4.178.jar";
	private static String profile = "myDBProfile";

	@BeforeClass
	public static void genericConnectionProfileTest() {

		// Driver definitions removal
		WorkbenchPreferenceDialog preferenceDialog = new WorkbenchPreferenceDialog();
		preferenceDialog.open();
		DriverDefinitionPreferencePage preferencePage = new DriverDefinitionPreferencePage();
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
		DataSourceExplorer dse = new DataSourceExplorer();
		dse.open();
		DefaultTreeItem item = new DefaultTreeItem("Database Connections");
		item.expand(TimePeriod.NORMAL);
		List<TreeItem> cpitems = item.getItems();
		for (TreeItem i : cpitems) {
			i.select();
			new ContextMenu("Delete").select();
			Shell deleteShell = new DefaultShell("Delete confirmation");
			new YesButton().click();
			new WaitWhile(new ShellIsAvailable(deleteShell));		
		}

		new WaitWhile(new JobIsRunning());
		
		DataSourceExplorer dataSourceExplorer = new DataSourceExplorer();
		dataSourceExplorer.open();
		List<String> dbSources = dataSourceExplorer.getDatabaseConnections();
		if (!dbSources.contains(profile)) {

			createDatabaseProfile(profile);	
		}
	}		
	
	private static void performSQLStatement(String profile, String statement) {
		DataSourceExplorer dse = new DataSourceExplorer();
		dse.open();
		@SuppressWarnings("unchecked")
		TreeItem connectionItem = new DefaultTreeItem(new TreeItemRegexMatcher("Database Connections"), new TreeItemRegexMatcher(profile+".*"));
		connectionItem.doubleClick();
		connectionItem.expand();
		new ContextMenu("Open SQL Scrapbook").select();
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
		new ContextMenu("Execute All").select();
		
		new WaitUntil(new ShellWithTextIsAvailable("SQL Statement Execution"),TimePeriod.LONG, false);
		new WaitWhile(new ShellWithTextIsAvailable("SQL Statement Execution"),TimePeriod.LONG, false);
		
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
		DriverDefinitionPreferencePage preferencePage = new DriverDefinitionPreferencePage();		
		DriverDefinitionWizard wizard = preferencePage
				.addDriverDefinition();
		DriverDefinitionPage page = new DriverDefinitionPage();
		page.selectDriverTemplate("Generic JDBC Driver", "1.0");
		page.setName(DRIVER_NAME);
		page.addDriverLibrary(drvFile.getAbsolutePath());
		page.setDriverClass("org.h2.Driver");
		wizard.finish();
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
		ConnectionProfileWizard w = new ConnectionProfileWizard();
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