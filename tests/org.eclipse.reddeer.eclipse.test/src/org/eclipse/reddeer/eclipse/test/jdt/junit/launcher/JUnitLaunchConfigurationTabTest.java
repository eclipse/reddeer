/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.eclipse.test.jdt.junit.launcher;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.reddeer.common.exception.RedDeerException;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.eclipse.debug.ui.launchConfigurations.JUnitLaunchConfiguration;
import org.eclipse.reddeer.eclipse.debug.ui.launchConfigurations.JUnitLaunchConfigurationTab;
import org.eclipse.reddeer.eclipse.debug.ui.launchConfigurations.LaunchConfigurationsDialog;
import org.eclipse.reddeer.eclipse.debug.ui.launchConfigurations.RunConfigurationsDialog;
import org.eclipse.reddeer.eclipse.ui.perspectives.JavaPerspective;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.requirements.openperspective.OpenPerspectiveRequirement.OpenPerspective;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
@OpenPerspective(JavaPerspective.class)
public class JUnitLaunchConfigurationTabTest {
	
	private static final String CONFIGURATION_NAME = JUnitLaunchConfigurationTabTest.class + "_test_config";

	protected LaunchConfigurationsDialog dialog;

	@Before
	public void openDialog(){
		dialog = new RunConfigurationsDialog();
		dialog.open();
	}
	
	@After
	public void closeDialog(){
		try {
			new WaitUntil(new ShellIsAvailable(dialog.getTitle()), TimePeriod.NONE);
			new DefaultShell(dialog.getTitle()).close();
		} catch (RedDeerException e){
			// already closed
		}
	}
	
	@Test
	public void testJUnitTab() {
		dialog.create(new JUnitLaunchConfiguration(), CONFIGURATION_NAME);
		
		JUnitLaunchConfigurationTab tab = new JUnitLaunchConfigurationTab();
		tab.activate();
		
		tab.setProject("abc");
		assertThat(tab.getProject(), is("abc"));
		
		tab.setTestClass("cde");
		assertThat(tab.getTestClass(), is("cde"));
		
		tab.setTestMethod("efg");
		assertThat(tab.getTestMethod(), is("efg"));
	}
}
