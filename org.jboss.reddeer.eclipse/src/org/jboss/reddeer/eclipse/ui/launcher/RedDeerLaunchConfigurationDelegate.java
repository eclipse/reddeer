package org.jboss.reddeer.eclipse.ui.launcher;

import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.pde.ui.launcher.JUnitLaunchConfigurationDelegate;
import org.jboss.reddeer.eclipse.Activator;

/**
 * 
 * @author sbunciak
 *
 */
@SuppressWarnings("deprecation")
public class RedDeerLaunchConfigurationDelegate extends
		JUnitLaunchConfigurationDelegate {
	public static final String LAUNCH_CONFIG_ID = "org.jboss.reddeer.eclipse.ui.launcher.JunitLaunchConfig"; //$NON-NLS-1$

	@Override
	protected String getApplication(ILaunchConfiguration configuration) {
		return Activator.APPLICATION_ID;
	}
}
