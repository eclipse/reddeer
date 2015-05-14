package org.jboss.reddeer.snippet.test.example;

import org.jboss.reddeer.eclipse.equinox.security.ui.StoragePreferencePage;
import org.jboss.reddeer.eclipse.jdt.ui.WorkbenchPreferenceDialog;
import org.jboss.reddeer.swt.api.TableItem;
import org.junit.Test;

public class SecureStorageExample {

	@Test
	public void disableSecureStorage() {
		WorkbenchPreferenceDialog preferenceDialog = new WorkbenchPreferenceDialog();
		StoragePreferencePage storagePage = new StoragePreferencePage();

		preferenceDialog.open();

		preferenceDialog.select(storagePage);
		for (TableItem item : storagePage.getMasterPasswordProviders()) {
			item.setChecked(true);
		}

		storagePage.apply();
		preferenceDialog.ok();
	}
}
