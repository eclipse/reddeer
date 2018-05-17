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
package org.eclipse.reddeer.eclipse.test.debug.ui.launchConfigurations;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.eclipse.reddeer.common.exception.RedDeerException;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.eclipse.debug.ui.launchConfigurations.LaunchConfiguration;
import org.eclipse.reddeer.eclipse.debug.ui.launchConfigurations.LaunchConfigurationsDialog;
import org.eclipse.reddeer.eclipse.ui.perspectives.JavaPerspective;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.requirements.openperspective.OpenPerspectiveRequirement.OpenPerspective;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.swt.impl.tree.DefaultTreeItem;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
@OpenPerspective(JavaPerspective.class)
public abstract class AbstractLaunchConfigurationDialogTest {

	protected LaunchConfigurationsDialog dialog;
	
	protected abstract String getConfigurationName();
	
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
	public void createSelectDelete(){
		JUnitConfiguration configuration = new JUnitConfiguration();
		
		dialog.open();
		dialog.create(configuration);
		dialog.create(configuration, getConfigurationName());
		
		dialog.select(configuration);
		
		assertTrue(new DefaultTreeItem(configuration.getType()).isSelected());
		
		dialog.select(configuration, getConfigurationName());
		assertTrue(new DefaultTreeItem(configuration.getType(), getConfigurationName()).isSelected());
	
		dialog.delete(configuration, getConfigurationName());
		try {
			new DefaultTreeItem(configuration.getType(), getConfigurationName());
			fail("The configuration shoud have been deleted");
		} catch (RedDeerException e){
			// ok, this is expected
		}
	}
	
	private class JUnitConfiguration extends LaunchConfiguration {

		public JUnitConfiguration() {
			super("JUnit");
		}
	}
}
