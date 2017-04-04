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
package org.jboss.reddeer.requirements.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

import org.jboss.reddeer.common.exception.WaitTimeoutExpiredException;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.core.condition.JobIsRunning;
import org.jboss.reddeer.core.condition.WidgetIsFound;
import org.jboss.reddeer.core.matcher.ClassMatcher;
import org.jboss.reddeer.core.matcher.WithMnemonicTextMatcher;
import org.jboss.reddeer.eclipse.equinox.security.ui.StoragePreferencePage;
import org.jboss.reddeer.junit.requirement.Requirement;
import org.jboss.reddeer.swt.api.TableItem;
import org.jboss.reddeer.workbench.ui.dialogs.WorkbenchPreferenceDialog;
import org.jboss.reddeer.requirements.security.DisableSecureStorageRequirement.DisableSecureStorage;

/**
 * Requirement class for switching off all master passwords requests
 * from Secure Storage page in preferences
 * @author odockal
 */
public class DisableSecureStorageRequirement implements Requirement<DisableSecureStorage> {
	
	private static final Logger log = Logger.getLogger(DisableSecureStorageRequirement.class);
	
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
		setSecureStorageMasterPasswords(false);		
	}

	@Override
	public void setDeclaration(DisableSecureStorage declaration) {
		// no code here
	}

	@Override
	public void cleanUp() {
		setSecureStorageMasterPasswords(true);
	}
	
    private void setSecureStorageMasterPasswords(boolean checked) {
        WorkbenchPreferenceDialog preferenceDialog = new WorkbenchPreferenceDialog();
        StoragePreferencePage storagePage = new StoragePreferencePage();

        preferenceDialog.open();
        preferenceDialog.select(storagePage);
        try {
	        new WaitUntil(new WidgetIsFound<org.eclipse.swt.custom.CLabel>(
	        		new ClassMatcher(org.eclipse.swt.custom.CLabel.class), 
	        		new WithMnemonicTextMatcher("Secure Storage")), TimePeriod.NORMAL);
	        log.info("Getting master password providers");
	        List<TableItem> items = storagePage.getMasterPasswordProviders();
	        for (TableItem item : items) {
	        	log.info("Uncheking table item: " + item.getText());
	            item.setChecked(checked);
	        }
	        new WaitUntil(new JobIsRunning(), TimePeriod.NORMAL, false);
	        storagePage.apply();
        } catch (WaitTimeoutExpiredException exc) {
        	log.error("Secure Storage preferences page has timed out");
        } finally {
	        preferenceDialog.ok();
		}
    }

}

