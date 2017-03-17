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
package org.jboss.reddeer.eclipse.test.equinox.security.ui;

import static org.junit.Assert.assertTrue;

import org.jboss.reddeer.workbench.ui.dialogs.WorkbenchPreferenceDialog;
import org.jboss.reddeer.eclipse.equinox.security.ui.storage.StoragePreferencePage;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.impl.tab.DefaultTabItem;
import org.jboss.reddeer.swt.impl.table.DefaultTable;
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
		
		storagePage.selectContentTab();
		assertTrue("TabItem Content has not been enabled", new DefaultTabItem("Contents").isEnabled());
		
		storagePage.selectAdvancedTab();
		assertTrue("TabItem Advanced has not been enabled", new DefaultTabItem("Advanced").isEnabled());
		
		storagePage.selectPasswordsTab();
		assertTrue("TabItem Password has not been enabled", new DefaultTabItem("Password").isEnabled());
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
