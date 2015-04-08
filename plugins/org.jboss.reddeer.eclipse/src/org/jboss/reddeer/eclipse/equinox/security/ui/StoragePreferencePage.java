package org.jboss.reddeer.eclipse.equinox.security.ui;

import java.util.List;

import org.jboss.reddeer.swt.api.TableItem;
import org.jboss.reddeer.swt.condition.ButtonWithTextIsActive;
import org.jboss.reddeer.common.exception.WaitTimeoutExpiredException;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.tab.DefaultTabItem;
import org.jboss.reddeer.swt.impl.table.DefaultTable;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.workbench.preference.WorkbenchPreferencePage;

/**
 * Storage preference page represent page for secure storage in Eclipse preferences.
 * Purpose of this preference page is storage handling of master password and passwords in Eclipse.
 * 
 * @author mlabuda@redhat.com
 *
 */
public class StoragePreferencePage extends WorkbenchPreferencePage {

	public StoragePreferencePage() {
		super("General", "Security", "Secure Storage");
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
			new WaitUntil(new ButtonWithTextIsActive(new PushButton("Clear Passwords")));
			new PushButton("Clear Passwords").click();
			return true;
		} catch(WaitTimeoutExpiredException ex) {
			return false;
		}
	}
}
