package org.jboss.reddeer.eclipse.test.m2e.core.ui.preferences;

import static org.junit.Assert.assertEquals;

import org.jboss.reddeer.eclipse.m2e.core.ui.preferences.MavenSettingsPreferencePage;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class MavenSettingsPreferencePageTest{
	
	private MavenSettingsPreferencePage page = new MavenSettingsPreferencePage();
	
	@After
	public void tearDown(){
		page.cancel();
	}
	
	@Test
	public void setSettingsXMLTest(){
		page.open();
		page.setUserSettingsLocation("test");
		assertEquals("test", page.getUserSettingsLocation());
	}

}
