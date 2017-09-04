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
package org.eclipse.reddeer.eclipse.equinox.security.ui.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.eclipse.reddeer.common.exception.WaitTimeoutExpiredException;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.eclipse.equinox.internal.security.ui.storage.PasswordRecoveryDialog;
import org.eclipse.reddeer.eclipse.equinox.internal.security.ui.storage.ChangePasswordWizardDialog;
import org.eclipse.reddeer.eclipse.exception.EclipseLayerException;
import org.eclipse.reddeer.jface.preference.PreferencePage;
import org.eclipse.reddeer.swt.api.TableItem;
import org.eclipse.reddeer.swt.condition.ControlIsEnabled;
import org.eclipse.reddeer.swt.impl.button.*;
import org.eclipse.reddeer.swt.impl.combo.DefaultCombo;
import org.eclipse.reddeer.swt.impl.tab.DefaultTabItem;
import org.eclipse.reddeer.swt.impl.table.DefaultTable;
import org.eclipse.reddeer.swt.impl.text.LabeledText;
import org.eclipse.reddeer.swt.impl.tree.DefaultTree;
import org.eclipse.reddeer.swt.impl.tree.DefaultTreeItem;

/**
 * Storage preference page represent page for secure storage in Eclipse preferences.
 * Purpose of this preference page is storage handling of master password and passwords in Eclipse.
 * 
 * @author mlabuda@redhat.com
 * @author jnovak@redhat.com
 *
 */
public class StoragePreferencePage extends PreferencePage {

	/**
	 * Instantiates a new storage preference page.
	 */
	public StoragePreferencePage(ReferencedComposite referencedComposite) {
		super(referencedComposite, "General", "Security", "Secure Storage");
	}

	/**
	 * Select Password tab in Security storage page containing general secure storage options.
	 */
	public StoragePreferencePage selectPasswordsTab() {
		new DefaultTabItem(this, "Password").activate();
		return this;
	}
	
	/**
	 * Select Contents tab in Storage preference page containing stored passwords with related user names/accounts.
	 */
	public StoragePreferencePage selectContentTab() {
		new DefaultTabItem(this, "Contents").activate();
		return this;
	}
	
	/**
	 * Select Advanced tab in Storage preference page containing decryption of stored passwords.
	 */
	public StoragePreferencePage selectAdvancedTab() {
		new DefaultTabItem(this, "Advanced").activate();
		return this;
	}
	
	/**
	 * Get master password providers from table shown on password tab in Storage preference page.
	 * 
	 * @return list of table items representing master passwords
	 */
	public List<PasswordProvider> getMasterPasswordProviders() {
		ArrayList<PasswordProvider> providers = new ArrayList<>();
		for (TableItem item : new DefaultTable(this).getItems()) {
			providers.add(new PasswordProvider(item));
		}
		return providers;
	}

	/**
	 * Opens master password recovery dialog.
	 *
	 * @return password recover dialog
	 */
	public PasswordRecoveryDialog recoverMasterPassword() {
		new PushButton(this, "Recover Password...").click();
		return new PasswordRecoveryDialog();
	}

	/**
	 * Opens master password change dialog.
	 *
	 * @return password change dialog
	 */
	public ChangePasswordWizardDialog changeMasterPassword() {
		new PushButton(this, "Change Password...").click();
		return new ChangePasswordWizardDialog();
	}

	/**
	 * Clears stored passwords. If there are non passwords, nothing happens.
	 * 
	 * @return true if cleared passwords successfully, false otherwise
	 */
	public boolean clearCachedPasswords() {
		try {
			new WaitUntil(new ControlIsEnabled(new PushButton(this, "Clear Passwords")));
			new PushButton(this, "Clear Passwords").click();
			return true;
		} catch(WaitTimeoutExpiredException ex) {
			return false;
		}
	}

	/**
	 * Finds out whether there is any password in secure storage on the end of specified path.
	 * 
	 * @param pathToPassword full path in tree on Content tab of Secure Storage Preference page
	 * @return true if there is any password on the end of specified path, false otherwise
	 */
	public boolean passwordExists(String... pathToPassword) {
		selectContentTab();
		new DefaultTreeItem(new DefaultTree(this, 1), pathToPassword).select();
		
		// Reactivation required
		selectContentTab();
		return new DefaultTable(this).getItems().size() > 0;
	}

	/**
	 * Retrieves passwords in secure storage on the end of specified path.
	 *
	 * @param pathToPasswords full path in tree on Content tab of Secure Storage Preference page
	 * @return stored keys and values in the Properties object
	 */
	public Properties getPasswordsByPath(String... pathToPasswords) {
		selectContentTab();
		new DefaultTreeItem(new DefaultTree(this, 1), pathToPasswords).select();

		// Reactivation required
		selectContentTab();
		return getPasswordsFromTable(new DefaultTable(this));
	}

	/**
	 * Retrieves the secure storage location.
	 * @return secure storage location
	 */
	public String getStorageLocation() {
		selectContentTab();
		return new LabeledText(this, "Storage location:").getText();
	}

	private Properties getPasswordsFromTable(DefaultTable table) {
		Properties props = new Properties();
		for (TableItem item : table.getItems()) {
			String id = item.getText(0);
			String value = item.getText(1);
			props.put(id, value);
		}
		return props;
	}

	/**
	 * Retrieves available encrypt algorithms.
	 * @return available algorithms
	 */
	public List<String> getAvailableEncryptionAlgorithms() {
		selectAdvancedTab();
		return new DefaultCombo(this).getItems();
	}

	/**
	 * Retrieves currently chosen encrypt algorithms.
	 * @return currently chosen encrypt algorithm name.
	 */
	public String getEncryptionAlgorithm() {
		selectAdvancedTab();
		return new DefaultCombo(this).getSelection();
	}

	/**
	 * Sets encrypt algorithm.
	 * @param algorithmName new algorithm name.
	 */
	public void setEncryptionAlgorithm(String algorithmName) {
		selectAdvancedTab();
		if(getAvailableEncryptionAlgorithms().contains(algorithmName)) {
			new DefaultCombo(this).setSelection(algorithmName);
		} else {
			throw new EclipseLayerException("Algorithm " + algorithmName + " not found!");
		}
	}

}
