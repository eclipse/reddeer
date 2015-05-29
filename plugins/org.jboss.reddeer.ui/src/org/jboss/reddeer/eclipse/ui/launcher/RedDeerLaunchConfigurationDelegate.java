package org.jboss.reddeer.eclipse.ui.launcher;

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.variables.IStringVariableManager;
import org.eclipse.core.variables.VariablesPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.pde.launching.JUnitLaunchConfigurationDelegate;
import org.jboss.reddeer.common.userprofile.UserProfile;
import org.jboss.reddeer.eclipse.jdt.debug.ui.launchConfigurations.RedDeerJavaArgumentsTab;
import org.jboss.reddeer.ui.Activator;

/**
 * A launch delegate for launching JUnit Plug-in tests.
 * 
 * @author sbunciak
 * @since 0.2
 */
public class RedDeerLaunchConfigurationDelegate extends
		JUnitLaunchConfigurationDelegate {
	
	public static final String LAUNCH_CONFIG_ID = "org.jboss.reddeer.eclipse.ui.launcher.JunitLaunchConfig"; //$NON-NLS-1$

	@Override
	protected String getApplication(ILaunchConfiguration configuration) {
		return Activator.APPLICATION_ID;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	/**
	 * Reads all attributes stored in configuration and those with specific red deer prefix stores as VM argument. 
	 */
	protected void collectExecutionArguments(
			ILaunchConfiguration configuration, List vmArguments,
			List programArgs) throws CoreException {
		super.collectExecutionArguments(configuration, vmArguments, programArgs);

		List<RedDeerLauncherProperties> properties = RedDeerLauncherProperties.loadAll(configuration);
		IStringVariableManager mgr = VariablesPlugin.getDefault().getStringVariableManager();
		
		if (properties != null && properties.size() > 0){
			// RedDeer properties were added by Run As dialog
			for (RedDeerLauncherProperties property : properties){
				if (property.getCurrentValue() == null || "".equals(property.getCurrentValue())){
					continue;
				}
				vmArguments.add(getVMArgument(property, mgr));
			}
		}
		else{
			// Launching was called by Run As context menu therefore initialize all RedDeer properties
			RedDeerLauncherProperties[] redDeerLauncherPropsInitValues = RedDeerLauncherProperties.getInitialRedDeerLauncherProperties();
			for (RedDeerLauncherProperties property : redDeerLauncherPropsInitValues){
				String currValue = property.getCurrentValue(); 
				if ( currValue != null && currValue.length() > 0){
					vmArguments.add(getVMArgument(property, mgr));
				}
			}
			// Save default RedDeer Properties values to Launch configuration
			ILaunchConfigurationWorkingCopy launchConfigurationWorkingCopy = configuration.getWorkingCopy();
			RedDeerJUnitTab.savePropertiesToLaunchConfiguration(launchConfigurationWorkingCopy, redDeerLauncherPropsInitValues); 
			// Add vmArguments from user profile
			String userProfileVMargs = UserProfile.getInstance().getProperty(UserProfile.VM_ARGS_KEY);
			if (userProfileVMargs != null){
				String currentVMargs = configuration.getAttribute(RedDeerJavaArgumentsTab.VM_ARGS_ATTR_NAME, "");
				launchConfigurationWorkingCopy.setAttribute(RedDeerJavaArgumentsTab.VM_ARGS_ATTR_NAME, currentVMargs 
					+ (currentVMargs.length() > 0 ? " " : "") 
					+ userProfileVMargs);
				launchConfigurationWorkingCopy.doSave();
				vmArguments.add(userProfileVMargs);
			}
		}
	}
	
	private String getVMArgument (RedDeerLauncherProperties property, IStringVariableManager mgr) throws CoreException{
		String substituedVariables = mgr.performStringSubstitution(property.getCurrentValue());
		return "-D" + property.getProperty().getName() + "=" + substituedVariables;
	}
}
