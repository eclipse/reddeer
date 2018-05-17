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
package org.eclipse.reddeer.eclipse.test.datatools.ui;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.eclipse.reddeer.eclipse.datatools.connectivity.ui.dse.views.DataSourceExplorerView;
import org.eclipse.reddeer.eclipse.datatools.connectivity.ui.wizards.NewCPWizard;
import org.eclipse.reddeer.eclipse.datatools.ui.FlatFileProfile;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class ConnectionProfileTest {

	@Test
	public void flatFileConnectionProfileTest() {
		String profile = "Test Flat File Profile";

		FlatFileProfile flatProfile = new FlatFileProfile();
		flatProfile.setName(profile);
		flatProfile.setFolder(new File("target").getAbsolutePath());
		flatProfile.setCharset("UTF-8");
		flatProfile.setStyle("CSV");

		NewCPWizard connWizard = new NewCPWizard();
		connWizard.open();
		connWizard.createFlatFileProfile(flatProfile);

		DataSourceExplorerView dataSourceExplorer = new DataSourceExplorerView();
		dataSourceExplorer.open();
		List<String> flatFileSources = dataSourceExplorer.getFlatFileDataSources();
		assertTrue("Profile '" + profile + "' isn't available", flatFileSources.contains(profile));
	}

}