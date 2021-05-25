/******************************************************************************* 
 * Copyright (c) 2021 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/
package org.eclipse.reddeer.eclipse.test.jdt.environment.launcher;

import static org.junit.Assert.assertTrue;

import org.eclipse.reddeer.common.exception.RedDeerException;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.eclipse.debug.ui.launchConfigurations.EnvironmentTab;
import org.eclipse.reddeer.eclipse.debug.ui.launchConfigurations.JUnitLaunchConfiguration;
import org.eclipse.reddeer.eclipse.debug.ui.launchConfigurations.LaunchConfigurationsDialog;
import org.eclipse.reddeer.eclipse.debug.ui.launchConfigurations.RunConfigurationsDialog;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.impl.button.RadioButton;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import org.junit.Test;

/**
 * Tests for Environment Tab in Launch Configuration
 * 
 * @author Oleksii Korniienko olkornii@redhat.com
 *
 */
public class EnvironmentTabTest {

	private static final String CONFIGURATION_NAME = EnvironmentTabTest.class + "_test_config";

	protected static LaunchConfigurationsDialog dialog;
	protected static EnvironmentTab envTab;

	@BeforeClass
	public static void beforeClass() throws Exception {
		dialog = new RunConfigurationsDialog();
		dialog.open();
		dialog.create(new JUnitLaunchConfiguration(), CONFIGURATION_NAME);
		envTab = new EnvironmentTab();
		envTab.activate();
	}

	@AfterClass
	public static void closeDialog() {
		try {
			new WaitUntil(new ShellIsAvailable(dialog.getTitle()), TimePeriod.NONE);
			new DefaultShell(dialog.getTitle()).close();
		} catch (RedDeerException e) {
			// already closed
		}
	}

	@Test
	public void addTest() {
		int prev_env_count = envTab.getAllVariables().size();
		envTab.add("test_name", "test_value");
		int add_env_count = envTab.getAllVariables().size();
		assertTrue((prev_env_count + 1) == add_env_count);
	}

	@Test
	public void selectTest() {
		int prev_env_count = envTab.getAllVariables().size();
		envTab.selectEnvironmentVariable(0);
		int select_env_count = envTab.getAllVariables().size();
		assertTrue((prev_env_count + 1) == select_env_count);

		int prev_env_name_count = envTab.getAllVariables().size();
		envTab.selectEnvironmentVariable("USER");
		int select_env_name_count = envTab.getAllVariables().size();
		assertTrue((prev_env_name_count + 1) == select_env_name_count);

		prev_env_count = envTab.getAllVariables().size();
		envTab.selectEnvironmentVariable(true);
		int select_all_env_count = envTab.getAllVariables().size();
		assertTrue(prev_env_count < select_all_env_count);
	}

	@Test
	public void removeTest() {
		int prev_env_count = envTab.getAllVariables().size();
		String first_var_name = envTab.getVariable(0).getText();
		envTab.remove(first_var_name);
		int remove_env_count = envTab.getAllVariables().size();
		assertTrue((prev_env_count - 1) == remove_env_count);
	}

	@Test
	public void editTest() {
		envTab.edit("test_name", "test_new", "test_new");
		String item_text = envTab.getVariable("test_new").getText();
		assertTrue(item_text.contains("test_new"));
	}

	@Test
	public void copyPasteTest() {
		envTab.add("test_copy", "test_copy");
		int prev_env_count = envTab.getAllVariables().size();

		envTab.copy("test_copy");
		envTab.remove("test_copy");
		int copy_env_count = envTab.getAllVariables().size();
		assertTrue((prev_env_count - 1) == copy_env_count);

		envTab.paste();
		int paste_env_count = envTab.getAllVariables().size();
		assertTrue(prev_env_count == paste_env_count);
	}

	@Test
	public void appendReplaceTest() {
		envTab.getReplaceRadioButton().click();
		assertTrue(new RadioButton("Replace native environment with specified environment").isSelected());
		envTab.getAppendRadioButton().click();
		assertTrue(new RadioButton("Append environment to native environment").isSelected());
	}
}
