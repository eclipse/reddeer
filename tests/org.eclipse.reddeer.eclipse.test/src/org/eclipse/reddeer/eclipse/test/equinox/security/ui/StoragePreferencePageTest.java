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
package org.eclipse.reddeer.eclipse.test.equinox.security.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.reddeer.workbench.ui.dialogs.WorkbenchPreferenceDialog;
import org.eclipse.reddeer.eclipse.equinox.security.ui.storage.StoragePreferencePage;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.swt.api.TabFolder;
import org.eclipse.reddeer.swt.impl.tab.DefaultTabItem;
import org.eclipse.reddeer.swt.impl.table.DefaultTable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class StoragePreferencePageTest {

	private WorkbenchPreferenceDialog workbenchPreferenceDialog = new WorkbenchPreferenceDialog();
	
	@Before 
	public void openWorkbenchPreferenceDialog() {
		workbenchPreferenceDialog.open();
	}
	
	@Test
	public void openStoragePreferencePageTest() {
		workbenchPreferenceDialog.select(new StoragePreferencePage());
		
		assertTrue("Secure storage preference page has" + "not been opened.", 
				"Secure Storage".equals(workbenchPreferenceDialog.getPageName()));
	}
	
	@Test
	public void selectDifferentTabsOnPreferencePageTest() {
		StoragePreferencePage storagePage = new StoragePreferencePage();
		workbenchPreferenceDialog.select(new StoragePreferencePage());
		
		TabFolder tabFolder = new DefaultTabItem("Contents").getTabFolder();
		storagePage.selectContentTab();
		assertTrue(tabFolder.getSelection().size() == 1);
		assertEquals("Contents", tabFolder.getSelection().get(0).getText());
		
		storagePage.selectAdvancedTab();
		assertTrue(tabFolder.getSelection().size() == 1);
		assertEquals("Advanced", tabFolder.getSelection().get(0).getText());
		
		storagePage.selectPasswordsTab();
		assertTrue(tabFolder.getSelection().size() == 1);
		assertEquals("Password", tabFolder.getSelection().get(0).getText());
	}
	
	@Test
	public void getMasterPasswordProvidersTest() {
		StoragePreferencePage storagePage = new StoragePreferencePage();
		workbenchPreferenceDialog.select(new StoragePreferencePage());
		
		assertTrue("Obtained number of master password providers is not same as in the table on the Password tab", 
				storagePage.getMasterPasswordProviders().size() == new DefaultTable(0).getItems().size());
	}
	
	@After
	public void closeWorkbenchPreferenceDialog() {
		workbenchPreferenceDialog.cancel();
	}
	
}
