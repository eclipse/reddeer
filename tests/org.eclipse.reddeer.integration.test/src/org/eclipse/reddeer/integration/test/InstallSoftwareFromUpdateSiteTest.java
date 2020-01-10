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

import org.eclipse.reddeer.integration.test.installation.common.dialog.InstallNewSoftwareDialog;
import org.eclipse.reddeer.integration.test.installation.common.page.AvailableSoftwarePage;
import org.eclipse.reddeer.integration.test.installation.common.util.InstallationOperator;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class InstallSoftwareFromUpdateSiteTest {
	
	private static final String UPDATE_SITE = System.getProperty("update.site");
	
	@Rule
	public ErrorCollector collector = new ErrorCollector();

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
