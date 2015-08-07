package org.jboss.reddeer.snippet.preference;

import static org.junit.Assert.assertTrue;

import org.jboss.reddeer.eclipse.m2e.core.ui.preferences.MavenPreferencePage;
import org.jboss.reddeer.jface.preference.PreferenceDialog;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.workbench.ui.dialogs.WorkbenchPreferenceDialog;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class MavenPreferencePageTest {

	@Test
	public void test() {
		// create instances of dialog and page
		PreferenceDialog dialog = new WorkbenchPreferenceDialog();
		MavenPreferencePage page = new MavenPreferencePage();
		
		// open the dialog using Eclipse top menu
		dialog.open();
		// select page in the left menu
		dialog.select(page);
		
		// work with the page
		page.setDebugOutput(true);
		assertTrue(page.isDebugOutputChecked());
		
		// press OK button to store the values and close the dialog
		dialog.ok();
	}
}