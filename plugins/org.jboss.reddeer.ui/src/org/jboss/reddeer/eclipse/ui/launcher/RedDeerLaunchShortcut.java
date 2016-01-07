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
package org.jboss.reddeer.eclipse.ui.launcher;

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
