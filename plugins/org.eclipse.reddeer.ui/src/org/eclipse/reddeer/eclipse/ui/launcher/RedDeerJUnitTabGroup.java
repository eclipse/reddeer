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
package org.eclipse.reddeer.eclipse.ui.launcher;

import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.jdt.debug.ui.launchConfigurations.JavaArgumentsTab;
import org.eclipse.jdt.internal.junit.launcher.JUnitLaunchConfigurationConstants;
import org.eclipse.jdt.internal.junit.launcher.TestKindRegistry;
import org.eclipse.jdt.junit.launcher.JUnitLaunchConfigurationTab;
import org.eclipse.pde.ui.launcher.JUnitTabGroup;
import org.eclipse.reddeer.eclipse.jdt.debug.ui.launchConfigurations.RedDeerJavaArgumentsTab;

/**
 * Class which creates RedDeer specific tab in run configurations. use the pde
 * defaults, just change the first tab to use the junit config instead of the
 * pde one. RedDeer uses its own IApplication, which takes care of threading.
 * 
 * Replaces JavaArgumentsTab with RedDeerJavaArgumentsTab to allow to handle
 * initial value for vm arguments
 * 
 * @author sbunciak
 * @author vlado pakan
 * 
 */
public class RedDeerJUnitTabGroup extends JUnitTabGroup {

	/**
	 * Creates tabs for RedDeer JUnit tab group.
	 * @param dialog dialog
	 * @param mode mode
	 */
	public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
		super.createTabs(dialog, mode);
		ILaunchConfigurationTab[] tabs = getTabs();
		tabs[0] = new JUnitLaunchConfigurationTab();
		
		int index = 0;
		while (!(tabs[index] instanceof JavaArgumentsTab) && index < tabs.length){
			index++;
		}
		if (index < tabs.length){
			tabs[index] = new RedDeerJavaArgumentsTab();
		}
	}
	
	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		super.setDefaults(configuration);
		configuration.setAttribute(JUnitLaunchConfigurationConstants.ATTR_TEST_RUNNER_KIND,TestKindRegistry.JUNIT4_TEST_KIND_ID);
	}

}