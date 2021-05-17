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
import org.eclipse.reddeer.eclipse.debug.ui.launchConfigurations.EnvironmentLaunchConfigurationTab;
import org.eclipse.reddeer.eclipse.debug.ui.launchConfigurations.JUnitLaunchConfiguration;
import org.eclipse.reddeer.eclipse.debug.ui.launchConfigurations.LaunchConfigurationsDialog;
import org.eclipse.reddeer.eclipse.debug.ui.launchConfigurations.RunConfigurationsDialog;
import org.eclipse.reddeer.eclipse.ui.perspectives.JavaPerspective;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.requirements.openperspective.OpenPerspectiveRequirement.OpenPerspective;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.impl.button.RadioButton;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.swt.impl.table.DefaultTable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
@OpenPerspective(JavaPerspective.class)
public class EnvironmentLaunchConfigurationTabTest {

	private static final String CONFIGURATION_NAME = EnvironmentLaunchConfigurationTabTest.class + "_test_config";

	protected LaunchConfigurationsDialog dialog;

	@Before
	public void openDialog() {
		dialog = new RunConfigurationsDialog();
		dialog.open();
	}

	@After
	public void closeDialog() {
		try {
			new WaitUntil(new ShellIsAvailable(dialog.getTitle()), TimePeriod.NONE);
			new DefaultShell(dialog.getTitle()).close();
		} catch (RedDeerException e) {
			// already closed
		}
	}

	@Test
	public void testEnvironmentTab() {
		dialog.create(new JUnitLaunchConfiguration(), CONFIGURATION_NAME);

		EnvironmentLaunchConfigurationTab envTab = new EnvironmentLaunchConfigurationTab();
		envTab.activate();
		int should_be_var_count = envTab.get_variables().size();

		envTab.add("test_name", "test_value");
		should_be_var_count++;
		int add_env_count = envTab.get_variables().size();
		assertTrue(should_be_var_count == add_env_count);

		envTab.select(0);
		should_be_var_count++;
		int select_env_count = envTab.get_variables().size();
		assertTrue(should_be_var_count == select_env_count);

		new DefaultTable().select(0);
		envTab.remove();
		should_be_var_count--;
		int remove_env_count = envTab.get_variables().size();
		assertTrue(should_be_var_count == remove_env_count);

		new DefaultTable().select(0);
		envTab.edit("test_new", "test_new");
		String item_text = new DefaultTable().getItem(0).getText();
		assertTrue(item_text.contains("test_new"));

		new DefaultTable().select(0);
		envTab.copy();
		envTab.remove();
		should_be_var_count--;
		int copy_env_count = envTab.get_variables().size();
		assertTrue(should_be_var_count == copy_env_count);
		envTab.paste();
		should_be_var_count++;
		int paste_env_count = envTab.get_variables().size();
		assertTrue(should_be_var_count == paste_env_count);

		envTab.replace();
		assertTrue(new RadioButton("Replace native environment with specified environment").isSelected());
		envTab.append();
		assertTrue(new RadioButton("Append environment to native environment").isSelected());
	}
}
