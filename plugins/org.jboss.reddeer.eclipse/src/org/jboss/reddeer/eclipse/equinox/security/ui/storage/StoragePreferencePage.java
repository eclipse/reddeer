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
package org.jboss.reddeer.eclipse.equinox.security.ui.storage;

import java.util.List;

import org.jboss.reddeer.common.exception.WaitTimeoutExpiredException;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.jface.preference.PreferencePage;
import org.jboss.reddeer.swt.api.TableItem;
import org.jboss.reddeer.swt.condition.ControlIsEnabled;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.tab.DefaultTabItem;
import org.jboss.reddeer.swt.impl.table.DefaultTable;
import org.jboss.reddeer.swt.impl.tree.DefaultTree;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;

/**
 * Storage preference page represent page for secure storage in Eclipse preferences.
 * Purpose of this preference page is storage handling of master password and passwords in Eclipse.
 * 
 * @author mlabuda@redhat.com
 *
 */
public class StoragePreferencePage extends PreferencePage {

	/**
	 * Instantiates a new storage preference page.
	 */
	public StoragePreferencePage() {
		super(new String[] {"General", "Security", "Secure Storage"});
	}
	
	/**
	 * Select Password tab in Security storage page containing general secure storage options.
	 */
	public void selectPasswordsTab() {
		new DefaultTabItem("Password").activate();
	}
	
	/**
	 * Select Contents tab in Storage preference page containing stored passwords with related user names/accounts.
	 */
	public void selectContentTab() {
		new DefaultTabItem("Contents").activate();
	}
	
	/**
	 * Select Advanced tab in Storage preference page containing decryption of stored passwords.
	 */
	public void selectAdvancedTab() {
		new DefaultTabItem("Advanced").activate();
	}
	
	/**
	 * Get master password providers from table shown on password tab in Storage preference page.
	 * 
	 * @return list of table items representing master passwords
	 */
	public List<TableItem> getMasterPasswordProviders() {
		return new DefaultTable().getItems();
	}
	
	/**
	 * Clears stored passwords. If there are non passwords, nothing happens.
	 * 
	 * @return true if cleared passwords successfully, false otherwise
	 */
	public boolean clearPasswords() {
		try {
			new WaitUntil(new ControlIsEnabled(new PushButton("Clear Passwords")));
			new PushButton("Clear Passwords").click();
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
		new DefaultTreeItem(new DefaultTree(1), pathToPassword).select();
		
		// Reactivation required
		selectContentTab();
		return new DefaultTable(0).getItems().size() > 0;
	}
	
}
