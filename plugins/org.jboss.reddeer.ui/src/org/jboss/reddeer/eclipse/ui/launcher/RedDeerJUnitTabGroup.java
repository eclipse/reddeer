package org.jboss.reddeer.eclipse.ui.launcher;

import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.jdt.debug.ui.launchConfigurations.JavaArgumentsTab;
import org.eclipse.jdt.junit.launcher.JUnitLaunchConfigurationTab;
import org.eclipse.pde.ui.launcher.JUnitTabGroup;
import org.jboss.reddeer.eclipse.jdt.debug.ui.launchConfigurations.RedDeerJavaArgumentsTab;

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
	 * @see {@link org.eclipse.pde.ui.launcher.JUnitTabGroup#createTabs(ILaunchConfigurationDialog, String)}
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

}