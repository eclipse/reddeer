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

import org.eclipse.pde.ui.launcher.JUnitWorkbenchLaunchShortcut;

/**
 * Enhances the {@link JUnitWorkbenchLaunchShortcut} to launch RedDeer's launch configuration. 
 * 
 * @author sbunciak
 *
 */
public class RedDeerLaunchShortcut extends JUnitWorkbenchLaunchShortcut {
	
	protected String getLaunchConfigurationTypeId() {
		return RedDeerLaunchConfigurationDelegate.LAUNCH_CONFIG_ID;
	}
	
}
