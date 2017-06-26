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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.eclipse.reddeer.direct.preferences.PreferencesUtil;
import org.eclipse.reddeer.eclipse.debug.ui.preferences.ConsolePreferencePage;
import org.eclipse.reddeer.eclipse.ui.ide.dialogs.IDEPerspectivesPreferencePage;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.swt.api.Menu;
import org.eclipse.reddeer.swt.impl.menu.ShellMenu;
import org.eclipse.reddeer.workbench.ui.dialogs.WorkbenchPreferenceDialog;
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

		WorkbenchPreferenceDialog preferenceDialog = new WorkbenchPreferenceDialog();
		ConsolePreferencePage consolePreferencePage = new ConsolePreferencePage(preferenceDialog);
		preferenceDialog.open();
		preferenceDialog.select(consolePreferencePage);
		consolePreferencePage.restoreDefaults();
		consolePreferencePage.apply();
		preferenceDialog.ok();
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
		IDEPerspectivesPreferencePage perspectivesPreferencePage = new IDEPerspectivesPreferencePage(preferencesDialog);
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
		IDEPerspectivesPreferencePage perspectivesPreferencePage = new IDEPerspectivesPreferencePage(preferencesDialog);
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

	@Test
	public void testLimitingConsoleOutput() {
		WorkbenchPreferenceDialog preferenceDialog = new WorkbenchPreferenceDialog();
		preferenceDialog.open();
		ConsolePreferencePage consolePreferencePage = new ConsolePreferencePage(preferenceDialog);
		preferenceDialog.select(consolePreferencePage);
		assertEquals(consolePreferencePage.isConsoleOutputLimited(), PreferencesUtil.isConsoleOutputLimited());
		assertEquals(consolePreferencePage.getConsoleOutputSize(), PreferencesUtil.getConsoleOutputSize());
		preferenceDialog.cancel();

		PreferencesUtil.setConsoleOutputLimited(false);

		preferenceDialog.open();
		preferenceDialog.select(consolePreferencePage);
		assertEquals(false, consolePreferencePage.isConsoleOutputLimited());
		preferenceDialog.cancel();

		PreferencesUtil.setConsoleOutputSize(100000);

		preferenceDialog.open();
		preferenceDialog.select(consolePreferencePage);
		assertEquals(true, consolePreferencePage.isConsoleOutputLimited());
		assertEquals(100000, consolePreferencePage.getConsoleOutputSize());
		preferenceDialog.cancel();
	}

	@Test
	public void testSettingConsoleShow() {
		WorkbenchPreferenceDialog preferenceDialog = new WorkbenchPreferenceDialog();
		preferenceDialog.open();
		ConsolePreferencePage consolePreferencePage = new ConsolePreferencePage(preferenceDialog);
		preferenceDialog.select(consolePreferencePage);
		assertEquals(consolePreferencePage.isConsoleOpenedOnError(), PreferencesUtil.isConsoleOpenedOnError());
		assertEquals(consolePreferencePage.isConsoleOpenedOnOutput(), PreferencesUtil.isConsoleOpenedOnOutput());
		preferenceDialog.cancel();

		PreferencesUtil.setConsoleOpenedOnError(true);
		PreferencesUtil.setConsoleOpenedOnOutput(false);

		preferenceDialog.open();
		preferenceDialog.select(consolePreferencePage);
		assertTrue(consolePreferencePage.isConsoleOpenedOnError());
		assertFalse(consolePreferencePage.isConsoleOpenedOnOutput());
		preferenceDialog.cancel();

		PreferencesUtil.setConsoleOpenedOnError(false);
		PreferencesUtil.setConsoleOpenedOnOutput(true);

		preferenceDialog.open();
		preferenceDialog.select(consolePreferencePage);
		assertFalse(consolePreferencePage.isConsoleOpenedOnError());
		assertTrue(consolePreferencePage.isConsoleOpenedOnOutput());
		preferenceDialog.cancel();
	}

}
