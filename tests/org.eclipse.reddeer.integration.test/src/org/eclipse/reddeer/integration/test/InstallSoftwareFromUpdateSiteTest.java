/*******************************************************************************
 * Copyright (c) 2020 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/

package org.eclipse.reddeer.integration.test;

import static org.junit.Assert.fail;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.integration.test.installation.common.dialog.InstallNewSoftwareDialog;
import org.eclipse.reddeer.integration.test.installation.common.page.AvailableSoftwarePage;
import org.eclipse.reddeer.integration.test.installation.common.preferences.AvailableSoftwareSitesPreferencePage;
import org.eclipse.reddeer.integration.test.installation.common.util.InstallationOperator;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.workbench.ui.dialogs.WorkbenchPreferenceDialog;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class InstallSoftwareFromUpdateSiteTest {
	
	private static final String UPDATE_SITE = System.getProperty("update.site");
	private static final String P2_ONLY_PROP = "p2.only";
	
	@Rule
	public ErrorCollector collector = new ErrorCollector();
	
	@BeforeClass
	public static void prepareTestEnv() {
		if(Boolean.getBoolean(P2_ONLY_PROP)) {
			System.out.println("System prop. p2.only: " + Boolean.getBoolean(P2_ONLY_PROP));
			WorkbenchPreferenceDialog dialog = new WorkbenchPreferenceDialog();
			dialog.open();
			AvailableSoftwareSitesPreferencePage page = new AvailableSoftwareSitesPreferencePage(dialog);
			dialog.select(page);
			for (int i = 0; i < page.getItems().size(); i++) {
				page.getItems().get(0).select();
				page.clickRemove();
				DefaultShell removeSites = new DefaultShell("Remove Sites");
				new PushButton("Yes").click();
				new WaitWhile(new ShellIsAvailable(removeSites), TimePeriod.SHORT);
			}
			dialog.ok();
		}
	}

	@Test
	public void testInstallFromUpdateSite() {
		try {
			new URL(UPDATE_SITE);
		} catch (MalformedURLException ex) {
			fail("Invalid update site URL: '" + UPDATE_SITE + "'");
		}
		
		InstallNewSoftwareDialog installDialog = new InstallNewSoftwareDialog();
		installDialog.open();
		
		AvailableSoftwarePage installPage = new AvailableSoftwarePage(installDialog);
		installPage.addUpdateSite(UPDATE_SITE);

		installPage.selectSoftware(".*RedDeer.*");
		
		installDialog.next();

		new InstallationOperator(installDialog, collector).completeInstallation();
	}
}
