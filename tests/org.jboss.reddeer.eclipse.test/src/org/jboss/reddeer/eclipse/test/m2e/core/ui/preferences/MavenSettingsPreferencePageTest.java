package org.jboss.reddeer.eclipse.test.m2e.core.ui.preferences;

import static org.junit.Assert.assertEquals;

import org.jboss.reddeer.eclipse.jdt.ui.WorkbenchPreferenceDialog;
import org.jboss.reddeer.eclipse.m2e.core.ui.preferences.MavenSettingsPreferencePage;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class MavenSettingsPreferencePageTest{
	
	private WorkbenchPreferenceDialog preferencesDialog = new WorkbenchPreferenceDialog();
	
	private MavenSettingsPreferencePage page = new MavenSettingsPreferencePage();
	
	@After
	public void tearDown(){
		preferencesDialog.cancel();
	}
	
	@Test
	public void setSettingsXMLTest(){
		preferencesDialog.open();
		preferencesDialog.select(page);
		page.setUserSettingsLocation("test");
		assertEquals("test", page.getUserSettingsLocation());
	}

}
