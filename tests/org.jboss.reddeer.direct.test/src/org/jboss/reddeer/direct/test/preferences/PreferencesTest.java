package org.jboss.reddeer.direct.test.preferences;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.jboss.reddeer.direct.preferences.Preferences;
import org.jboss.reddeer.eclipse.m2e.core.ui.preferences.MavenSettingsPreferencePage;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.requirements.cleanworkspace.CleanWorkspaceRequirement.CleanWorkspace;
import org.jboss.reddeer.swt.condition.JobIsRunning;
import org.jboss.reddeer.swt.wait.TimePeriod;
import org.jboss.reddeer.swt.wait.WaitWhile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test for core manipulation with Eclipse preferences.
 * 
 * @author apodhrad
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
		MavenSettingsPreferencePage page = new MavenSettingsPreferencePage();
		page.open();
		String location = page.getUserSettingsLocation();
		page.ok();
		assertEquals(location, Preferences.get(M2E_PLUGIN, M2E_USER_SETTINGS));
		page.open();
		String newLocation = location.replaceFirst(".xml", "_new.xml");
		page.setUserSettingsLocation(newLocation);
		page.ok();
		new WaitWhile(new JobIsRunning(), TimePeriod.NORMAL);
		assertEquals(newLocation, Preferences.get(M2E_PLUGIN, M2E_USER_SETTINGS));
	}

	@Test
	public void getNonExistingPreferenceTest() {
		assertNull(Preferences.get(M2E_PLUGIN, "nonExistingKey"));
	}

	@Test
	public void setPreferenceTest() {
		MavenSettingsPreferencePage page = new MavenSettingsPreferencePage();
		page.open();
		String location = page.getUserSettingsLocation();
		page.ok();
		String newLocation = location.replaceFirst(".xml", "_new.xml");
		Preferences.set(M2E_PLUGIN, M2E_USER_SETTINGS, newLocation);
		page.open();
		location = page.getUserSettingsLocation();
		page.ok();
		assertEquals(newLocation, location);
	}

	@Test
	public void setDefaultPreferenceTest() {
		MavenSettingsPreferencePage page = new MavenSettingsPreferencePage();
		page.open();
		String location = page.getUserSettingsLocation();
		page.setUserSettingsLocation(location.replaceFirst(".xml", "_new.xml"));
		page.ok();
		new WaitWhile(new JobIsRunning(), TimePeriod.NORMAL);
		Preferences.setDefault(M2E_PLUGIN, M2E_USER_SETTINGS);
		page.open();
		String newLocation = page.getUserSettingsLocation();
		page.ok();
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
