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
