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
package org.jboss.reddeer.eclipse.test.jdt.junit.launcher;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.jboss.reddeer.common.exception.RedDeerException;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.eclipse.debug.ui.launchConfigurations.JUnitLaunchConfiguration;
import org.jboss.reddeer.eclipse.debug.ui.launchConfigurations.JUnitLaunchConfigurationTab;
import org.jboss.reddeer.eclipse.debug.ui.launchConfigurations.LaunchConfigurationsDialog;
import org.jboss.reddeer.eclipse.debug.ui.launchConfigurations.RunConfigurationsDialog;
import org.jboss.reddeer.eclipse.ui.perspectives.JavaPerspective;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.requirements.openperspective.OpenPerspectiveRequirement.OpenPerspective;
import org.jboss.reddeer.swt.condition.ShellIsAvailable;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
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
