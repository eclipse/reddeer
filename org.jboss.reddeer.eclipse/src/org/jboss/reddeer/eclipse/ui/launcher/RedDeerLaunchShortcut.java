package org.jboss.reddeer.eclipse.ui.launcher;

import org.eclipse.pde.ui.launcher.JUnitWorkbenchLaunchShortcut;

/**
 * 
 * @author sbunciak
 *
 */
public class RedDeerLaunchShortcut extends JUnitWorkbenchLaunchShortcut {
	
	protected String getLaunchConfigurationTypeId() {
		return RedDeerLaunchConfigurationDelegate.LAUNCH_CONFIG_ID;
	}
	
}
