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
package org.jboss.reddeer.direct.test.preferences;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.jboss.reddeer.direct.preferences.PreferencesUtil;
import org.jboss.reddeer.eclipse.ui.dialogs.PerspectivesPreferencePage;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.workbench.ui.dialogs.WorkbenchPreferenceDialog;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test for core manipulation with common preferences.
 * 
 * @author Andrej Podhradsky (apodhrad@redhat.com)
 * 
 */
@RunWith(RedDeerSuite.class)
public class PreferencesUtilTest {

	private String openAssosiatedPerspective;

	public PreferencesUtilTest() {
		openAssosiatedPerspective = PreferencesUtil.getOpenAssociatedPerspective();
	}

	@After
	public void resetPreferences() {
		PreferencesUtil.setOpenAssociatedPerspective(openAssosiatedPerspective);
	}

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

	@Test
	public void getOpenAssociatedPerspectiveTest() {
		WorkbenchPreferenceDialog preferencesDialog = new WorkbenchPreferenceDialog();
		PerspectivesPreferencePage perspectivesPreferencePage = new PerspectivesPreferencePage();
		preferencesDialog.open();
		preferencesDialog.select(perspectivesPreferencePage);

		perspectivesPreferencePage.checkAlwaysOpenAssociatedPerspective();
		perspectivesPreferencePage.apply();
		assertEquals("always", PreferencesUtil.getOpenAssociatedPerspective());

		perspectivesPreferencePage.checkPromptOpenAssociatedPerspective();
		perspectivesPreferencePage.apply();
		assertEquals("prompt", PreferencesUtil.getOpenAssociatedPerspective());

		perspectivesPreferencePage.checkNeverOpenAssociatedPerspective();
		perspectivesPreferencePage.apply();
		assertEquals("never", PreferencesUtil.getOpenAssociatedPerspective());

		preferencesDialog.cancel();
	}

	@Test
	public void setOpenAssociatedPerspectiveTest() {
		PreferencesUtil.setOpenAssociatedPerspective("always");

		WorkbenchPreferenceDialog preferencesDialog = new WorkbenchPreferenceDialog();
		PerspectivesPreferencePage perspectivesPreferencePage = new PerspectivesPreferencePage();
		preferencesDialog.open();
		preferencesDialog.select(perspectivesPreferencePage);
		assertTrue(perspectivesPreferencePage.isAlwaysOpenAssociatedPerspective());
		preferencesDialog.cancel();

		PreferencesUtil.setOpenAssociatedPerspective("prompt");
		preferencesDialog.open();
		preferencesDialog.select(perspectivesPreferencePage);
		assertTrue(perspectivesPreferencePage.isPromptOpenAssociatedPerspective());
		preferencesDialog.cancel();

		PreferencesUtil.setOpenAssociatedPerspective("never");
		preferencesDialog.open();
		preferencesDialog.select(perspectivesPreferencePage);
		assertTrue(perspectivesPreferencePage.isNeverOpenAssociatedPerspective());
		preferencesDialog.cancel();
	}

	@Test
	public void setOpenAssociatedPerspectiveWithIncorrectOptionTest() {
		String incorrectOption = "sometimes";
		try {
			PreferencesUtil.setOpenAssociatedPerspective(incorrectOption);
			fail("IllegalArgumentException was expected for the option '" + incorrectOption + "'");
		} catch (IllegalArgumentException e) {
			assertEquals("Expected one of [always, never, prompt] but was '" + incorrectOption + "'", e.getMessage());
		}
	}

}
