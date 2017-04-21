/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.direct.test.preferences;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.eclipse.reddeer.direct.preferences.Preferences;
import org.eclipse.reddeer.workbench.ui.dialogs.WorkbenchPreferenceDialog;
import org.eclipse.reddeer.eclipse.m2e.core.ui.preferences.MavenSettingsPreferencePage;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.requirements.cleanworkspace.CleanWorkspaceRequirement.CleanWorkspace;
import org.eclipse.reddeer.workbench.core.condition.JobIsRunning;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitWhile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test for core manipulation with Eclipse preferences.
 * 
 * @author Andrej Podhradsky
 * 
 */
@CleanWorkspace
@RunWith(RedDeerSuite.class)
public class PreferencesTest {

	public static final String M2E_PLUGIN = "org.eclipse.m2e.core";
	public static final String M2E_USER_SETTINGS = "eclipse.m2.userSettingsFile";

	@Before
	@After
	public void restoreToDefault() {
		Preferences.setDefault(M2E_PLUGIN, M2E_USER_SETTINGS);
	}

	@Test
	public void getPreferenceTest() {
		WorkbenchPreferenceDialog dialog = new WorkbenchPreferenceDialog();
		MavenSettingsPreferencePage page = new MavenSettingsPreferencePage();
		dialog.open();
		dialog.select(page);
		String location = page.getUserSettingsLocation();
		dialog.ok();
		assertEquals(location, Preferences.get(M2E_PLUGIN, M2E_USER_SETTINGS));
		dialog.open();
		dialog.select(page);
		String newLocation = location.replaceFirst(".xml", "_new.xml");
		page.setUserSettingsLocation(newLocation);
		dialog.ok();
		new WaitWhile(new JobIsRunning(), TimePeriod.DEFAULT);
		assertEquals(newLocation, Preferences.get(M2E_PLUGIN, M2E_USER_SETTINGS));
	}

	@Test
	public void getNonExistingPreferenceTest() {
		assertNull(Preferences.get(M2E_PLUGIN, "nonExistingKey"));
	}

	@Test
	public void setPreferenceTest() {
		WorkbenchPreferenceDialog dialog = new WorkbenchPreferenceDialog();
		MavenSettingsPreferencePage page = new MavenSettingsPreferencePage();
		dialog.open();
		dialog.select(page);
		String location = page.getUserSettingsLocation();
		dialog.ok();
		String newLocation = location.replaceFirst(".xml", "_new.xml");
		Preferences.set(M2E_PLUGIN, M2E_USER_SETTINGS, newLocation);
		dialog.open();
		dialog.select(page);
		location = page.getUserSettingsLocation();
		dialog.ok();
		assertEquals(newLocation, location);
	}

	@Test
	public void setDefaultPreferenceTest() {
		WorkbenchPreferenceDialog dialog = new WorkbenchPreferenceDialog();
		MavenSettingsPreferencePage page = new MavenSettingsPreferencePage();
		dialog.open();
		dialog.select(page);
		String location = page.getUserSettingsLocation();
		page.setUserSettingsLocation(location.replaceFirst(".xml", "_new.xml"));
		dialog.ok();
		new WaitWhile(new JobIsRunning(), TimePeriod.DEFAULT);
		Preferences.setDefault(M2E_PLUGIN, M2E_USER_SETTINGS);
		dialog.open();
		dialog.select(page);
		String newLocation = page.getUserSettingsLocation();
		dialog.ok();
		assertEquals(newLocation, location);
	}

	@Test
	public void getDefaultPreferenceTest() {
		String defaultValue = Preferences.getDefault(M2E_PLUGIN, M2E_USER_SETTINGS);
		// This test works only from command line!
		// The default value is set using resources/plugin_customization.ini
		// See appArgLine in pom.xml 
		assertEquals("/tmp/settings.xml", defaultValue);
	}

}
