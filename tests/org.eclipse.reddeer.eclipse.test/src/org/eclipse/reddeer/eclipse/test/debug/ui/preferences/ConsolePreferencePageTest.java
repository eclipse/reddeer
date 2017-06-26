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
package org.eclipse.reddeer.eclipse.test.debug.ui.preferences;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.reddeer.eclipse.debug.ui.preferences.ConsolePreferencePage;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.workbench.ui.dialogs.WorkbenchPreferenceDialog;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * 
 * @author Andrej Podhradsky (apodhrad@redhat.com)
 *
 */
@RunWith(RedDeerSuite.class)
public class ConsolePreferencePageTest {

	private ConsolePreferencePage consolePreferencePage;

	@Before
	public void openConsolePreferencePage() {

		WorkbenchPreferenceDialog dialog = new WorkbenchPreferenceDialog();
		dialog.open();
		consolePreferencePage = new ConsolePreferencePage(dialog);
		dialog.select(consolePreferencePage);

		consolePreferencePage.restoreDefaults();
		consolePreferencePage.apply();
	}

	@After
	public void closeConsolePreferencePage() {
		new WorkbenchPreferenceDialog().cancel();
	}

	@Test
	public void testLimitConsoleOutput() {
		assertTrue(consolePreferencePage.isConsoleOutputLimited());
		consolePreferencePage.toggleConsoleOutputLimited(false);
		assertFalse(consolePreferencePage.isConsoleOutputLimited());
		consolePreferencePage.toggleConsoleOutputLimited(true);
		assertTrue(consolePreferencePage.isConsoleOutputLimited());
	}

	@Test
	public void testSettingConsoleOutputSizeWithLimit() {
		consolePreferencePage.toggleConsoleOutputLimited(true);
		consolePreferencePage.setConsoleOutputSize(12345);
		assertTrue(consolePreferencePage.isConsoleOutputLimited());
		assertEquals(12345, consolePreferencePage.getConsoleOutputSize());
	}

	@Test
	public void testSettingConsoleOutputSizeWithoutLimit() {
		consolePreferencePage.toggleConsoleOutputLimited(false);
		consolePreferencePage.setConsoleOutputSize(54321);
		assertTrue(consolePreferencePage.isConsoleOutputLimited());
		assertEquals(54321, consolePreferencePage.getConsoleOutputSize());
	}

	@Test
	public void testShowConsoleOnOutput() {
		assertTrue(consolePreferencePage.isConsoleOpenedOnOutput());
		consolePreferencePage.toggleShowConsoleOnOutput(false);
		assertFalse(consolePreferencePage.isConsoleOpenedOnOutput());
		consolePreferencePage.toggleShowConsoleOnOutput(true);
		assertTrue(consolePreferencePage.isConsoleOpenedOnOutput());
	}

	@Test
	public void testShowConsoleOnError() {
		assertTrue(consolePreferencePage.isConsoleOpenedOnError());
		consolePreferencePage.toggleShowConsoleErrorWrite(false);
		assertFalse(consolePreferencePage.isConsoleOpenedOnError());
		consolePreferencePage.toggleShowConsoleErrorWrite(true);
		assertTrue(consolePreferencePage.isConsoleOpenedOnError());
	}

}
