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

import java.util.List;

import org.eclipse.reddeer.eclipse.equinox.internal.security.ui.storage.PasswordRecoveryDialog;
import org.eclipse.reddeer.eclipse.equinox.internal.security.ui.storage.ChangePasswordWizardDialog;
import org.eclipse.reddeer.eclipse.equinox.internal.security.ui.storage.DescriptiveStorageNewDialog;
import org.eclipse.reddeer.eclipse.equinox.internal.security.ui.storage.DescriptiveStorageLoginDialog;
import org.eclipse.reddeer.swt.impl.button.OkButton;
import org.eclipse.reddeer.swt.impl.combo.DefaultCombo;
import org.eclipse.reddeer.workbench.ui.dialogs.WorkbenchPreferenceDialog;
import org.eclipse.reddeer.eclipse.equinox.security.ui.storage.StoragePreferencePage;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.swt.api.TabFolder;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.impl.tab.DefaultTabItem;
import org.eclipse.reddeer.swt.impl.table.DefaultTable;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

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

	@Test
	public void getStorageLocationTest() {
		StoragePreferencePage storagePage = new StoragePreferencePage();
		workbenchPreferenceDialog.select(new StoragePreferencePage());
		assertFalse(storagePage.getStorageLocation().isEmpty());
	}

	@Test
	public void getEncryptionAlgorithmTest() {
		StoragePreferencePage storagePage = new StoragePreferencePage();
		workbenchPreferenceDialog.select(new StoragePreferencePage());

		String algorithm = storagePage.getEncryptionAlgorithm();
		assertNotNull(algorithm);
		assertEquals(new DefaultCombo().getSelection(), algorithm);
	}

	@Test
	public void getAvailableEncryptionAlgorithmsTest() {
		StoragePreferencePage storagePage = new StoragePreferencePage();
		workbenchPreferenceDialog.select(new StoragePreferencePage());

		List<String> availableAlgorithms = storagePage.getAvailableEncryptionAlgorithms();
		assertFalse(availableAlgorithms.isEmpty());
		assertTrue(new DefaultCombo().getItems().size() == availableAlgorithms.size());
	}

	@Test
	public void availableAlgorithmsContainsCurrentAlgorithmTest() {
		StoragePreferencePage storagePage = new StoragePreferencePage();
		workbenchPreferenceDialog.select(new StoragePreferencePage());

		List<String> availableAlgorithms = storagePage.getAvailableEncryptionAlgorithms();
		assertTrue(availableAlgorithms.contains(storagePage.getEncryptionAlgorithm()));
	}

	@Test
	public void setEncryptionAlgorithmTest() {
		StoragePreferencePage storagePage = new StoragePreferencePage();
		workbenchPreferenceDialog.select(new StoragePreferencePage());

		List<String> availableAlgorithms = storagePage.getAvailableEncryptionAlgorithms();
		String lastAlgorithm = availableAlgorithms.get(availableAlgorithms.size() - 1);
		storagePage.setEncryptionAlgorithm(lastAlgorithm);

		assertEquals(lastAlgorithm, storagePage.getEncryptionAlgorithm());
	}

	@After
	public void closeWorkbenchPreferenceDialog() {
		workbenchPreferenceDialog.cancel();
	}
	
	@Test
	@Ignore
	//Can be executed with debugger
	public void recoverMasterPasswordTest() {
		StoragePreferencePage storagePage = new StoragePreferencePage();
		workbenchPreferenceDialog.select(new StoragePreferencePage());

		PasswordRecoveryDialog recoveryDialog = storagePage.recoverMasterPassword();
		assertTrue(new ShellIsAvailable("Password Recovery").test());

		recoveryDialog.answerFirstQuestion("answer1");
		recoveryDialog.answerSecondQuestion("answer2");
		assertTrue(new OkButton().isEnabled());

		recoveryDialog.cancel();
	}

	@Test
	@Ignore
	//Can be executed with debugger
	public void changeMasterPasswordTest() {
		StoragePreferencePage storagePage = new StoragePreferencePage();
		workbenchPreferenceDialog.select(new StoragePreferencePage());

		ChangePasswordWizardDialog changePassDialog = storagePage.changeMasterPassword();
		assertTrue(new ShellIsAvailable(changePassDialog.getShell()).test());

		changePassDialog.next();
		DescriptiveStorageLoginDialog oldPasswordDialog = new DescriptiveStorageLoginDialog();
		assertTrue(new ShellIsAvailable(oldPasswordDialog.getShell()).test());

		oldPasswordDialog.setPassword("oldPassword");
		oldPasswordDialog.ok();
		//error very probably bad password - handle in debugger

		changePassDialog.next();
		DescriptiveStorageNewDialog newPasswordDialog = new DescriptiveStorageNewDialog();
		assertTrue(new ShellIsAvailable(newPasswordDialog.getShell()).test());

		newPasswordDialog.setNewPassword("newPassword");
		newPasswordDialog.ok();

		changePassDialog.fillHints("q1", "q2", "a1", "a2");
		//changePassDialog.ignoreHints();

		changePassDialog.finish();
	}

}
