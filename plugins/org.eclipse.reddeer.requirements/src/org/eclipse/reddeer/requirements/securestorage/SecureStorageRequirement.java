/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.eclipse.reddeer.requirements.securestorage;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.eclipse.equinox.security.ui.storage.PasswordProvider;
import org.eclipse.reddeer.eclipse.equinox.security.ui.storage.StoragePreferencePage;
import org.eclipse.reddeer.junit.requirement.Requirement;
import org.eclipse.reddeer.requirements.securestorage.SecureStorageRequirement.DisableSecureStorage;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.workbench.core.condition.JobIsRunning;
import org.eclipse.reddeer.workbench.ui.dialogs.WorkbenchPreferenceDialog;

/**
 * Disable secure storage requirement.
 *
 * @author jnovak@redhat.com
 */
public class SecureStorageRequirement implements Requirement<DisableSecureStorage>{
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface DisableSecureStorage {
		
	}

	@Override
	public boolean canFulfill() {
		return true;
	}

	@Override
	public void fulfill() {
		WorkbenchPreferenceDialog dialog = new WorkbenchPreferenceDialog();
		StoragePreferencePage page = new StoragePreferencePage();
		
		dialog.open();
		dialog.select(page);
		page.selectPasswordsTab();
		
		for (PasswordProvider provider : page.getMasterPasswordProviders()) {
			provider.disable();
		}

		dialog.ok();
		new WaitWhile(new JobIsRunning());
		new WaitWhile(new ShellIsAvailable(dialog.getShell()));
	}

	@Override
	public void setDeclaration(DisableSecureStorage declaration) {
	}

	@Override
	public void cleanUp() {
	}
	
}
