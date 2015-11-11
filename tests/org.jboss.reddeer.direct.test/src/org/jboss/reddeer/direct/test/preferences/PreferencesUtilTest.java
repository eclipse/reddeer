package org.jboss.reddeer.direct.test.preferences;

import static org.junit.Assert.assertEquals;

import org.jboss.reddeer.direct.preferences.PreferencesUtil;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test for core manipulation with common preferences.
 * 
 * @author Andrej Podhradsky
 * 
 */
@RunWith(RedDeerSuite.class)
public class PreferencesUtilTest {

	@Test
	public void autoBuildingTest() {
		Menu menu = new ShellMenu("Project", "Build Automatically");
		assertEquals(menu.isSelected(), PreferencesUtil.isAutoBuildingOn());
		PreferencesUtil.setAutoBuildingOff();
		assertEquals(menu.isSelected(), false);
		assertEquals(menu.isSelected(), PreferencesUtil.isAutoBuildingOn());
		PreferencesUtil.setAutoBuildingOn();
		assertEquals(menu.isSelected(), true);
		assertEquals(menu.isSelected(), PreferencesUtil.isAutoBuildingOn());
	}
}
