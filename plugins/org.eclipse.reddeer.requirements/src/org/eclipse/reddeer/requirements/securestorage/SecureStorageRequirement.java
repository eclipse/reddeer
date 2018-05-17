/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.requirements.securestorage;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.eclipse.equinox.security.ui.storage.PasswordProvider;
import org.eclipse.reddeer.eclipse.equinox.security.ui.storage.StoragePreferencePage;
import org.eclipse.reddeer.junit.requirement.AbstractRequirement;
import org.eclipse.reddeer.requirements.securestorage.SecureStorageRequirement.DisableSecureStorage;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.workbench.core.condition.JobIsRunning;
import org.eclipse.reddeer.workbench.ui.dialogs.WorkbenchPreferenceDialog;

/**
 * Disable secure storage requirement.
 *
 * @author jnovak@redhat.com
 */
public class SecureStorageRequirement extends AbstractRequirement<DisableSecureStorage>{
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface DisableSecureStorage {
		
	}

	@Override
	public void fulfill() {
		WorkbenchPreferenceDialog dialog = new WorkbenchPreferenceDialog();
		StoragePreferencePage page = new StoragePreferencePage(dialog);
		
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
	public void cleanUp() {
	}
}
