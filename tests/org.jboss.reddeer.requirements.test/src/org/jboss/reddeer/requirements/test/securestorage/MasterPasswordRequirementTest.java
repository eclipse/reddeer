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
package org.jboss.reddeer.requirements.test.securestorage;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.jboss.reddeer.eclipse.equinox.security.ui.StoragePreferencePage;
import org.jboss.reddeer.junit.internal.configuration.NullTestRunConfiguration;
import org.jboss.reddeer.junit.internal.configuration.TestRunConfiguration;
import org.jboss.reddeer.junit.internal.requirement.Requirements;
import org.jboss.reddeer.junit.internal.requirement.RequirementsBuilder;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.jboss.reddeer.requirements.security.DisableSecureStorageRequirement.DisableSecureStorage;
import org.jboss.reddeer.swt.api.TableItem;
import org.jboss.reddeer.workbench.ui.dialogs.WorkbenchPreferenceDialog;

@RunWith(RedDeerSuite.class)
public class MasterPasswordRequirementTest {
	
	@Test
	public void testSecureStorageMasterPasswords() {
		Requirements requirement = getRequirements(SecureStorageReqTest.class);
		assertTrue(requirement.canFulfill());
		requirement.fulfill();
		assertTrue(checkMasterPasswords(false));
		requirement.cleanUp();
		assertTrue(checkMasterPasswords(true));
	}
	
	@DisableSecureStorage
	public static class SecureStorageReqTest {}

	private boolean checkMasterPasswords(boolean checked) {
		WorkbenchPreferenceDialog preferenceDialog = new WorkbenchPreferenceDialog();
        StoragePreferencePage storagePage = new StoragePreferencePage();

        preferenceDialog.open();
        preferenceDialog.select(storagePage);	
        List<TableItem> items = storagePage.getMasterPasswordProviders();
		for (TableItem item : items) {
			if (item.isChecked() != checked) {
				return false;
			}
		}
        preferenceDialog.ok();
        return true;
	}
	
	private Requirements getRequirements(Class<?> klass) {
		RequirementsBuilder reqBuilder = new RequirementsBuilder();
		TestRunConfiguration config = new NullTestRunConfiguration();
		return reqBuilder.build(klass, config.getRequirementConfiguration(), config.getId());
	}
	
}
