package org.jboss.reddeer.eclipse.jdt.debug.ui.launchConfigurations;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.jdt.debug.ui.launchConfigurations.JavaArgumentsTab;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.userprofile.UserProfile;
/**
 * Overrides JavaArgumentsTab to be able to initialize VM arguments
 * with values defined in user profile
 * @author vlado pakan
 *
 */
public class RedDeerJavaArgumentsTab extends JavaArgumentsTab{
	
	private static final Logger log = Logger.getLogger(JavaArgumentsTab.class);
	
	public static final String VM_ARGS_ATTR_NAME = "org.eclipse.jdt.launching.VM_ARGUMENTS";
	
	public static final String PROGRAM_ARGS_ATTR_NAME = "org.eclipse.jdt.launching.PROGRAM_ARGUMENTS";
	
	private static final String REDDEER_VM_ARGS_TO_ADD = "org.jboss.reddeer.VM_ARGUMENTS_TO_ADD";
	
	private static final String REDDEER_PROGRAM_ARGS_TO_ADD = "org.jboss.reddeer.PROGRAM_ARGUMENTS_TO_ADD";
	
	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy config) {
		super.setDefaults(config);
		String userProfileVMargs = UserProfile.getInstance().getProperty(UserProfile.VM_ARGS_KEY);
		if (userProfileVMargs != null && userProfileVMargs.length() > 0) {
			config.setAttribute(RedDeerJavaArgumentsTab.REDDEER_VM_ARGS_TO_ADD, userProfileVMargs);
		}

		String userProfileProgramArgs = UserProfile.getInstance().getProperty(UserProfile.PROGRAM_ARGS_KEY);
		if (userProfileProgramArgs != null && userProfileProgramArgs.length() > 0) {
			config.setAttribute(RedDeerJavaArgumentsTab.REDDEER_PROGRAM_ARGS_TO_ADD, userProfileProgramArgs);
		}
	}
	
	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		try {
			String reddeerVMArgsToAdd = configuration.getAttribute(RedDeerJavaArgumentsTab.REDDEER_VM_ARGS_TO_ADD, "");
			if (reddeerVMArgsToAdd.length() > 0){
				((ILaunchConfigurationWorkingCopy)configuration).setAttribute(RedDeerJavaArgumentsTab.REDDEER_VM_ARGS_TO_ADD, "");
				String currentVMArgs = configuration.getAttribute(RedDeerJavaArgumentsTab.VM_ARGS_ATTR_NAME, "");
				((ILaunchConfigurationWorkingCopy)configuration).setAttribute(RedDeerJavaArgumentsTab.VM_ARGS_ATTR_NAME,
						currentVMArgs + (currentVMArgs.length() > 0 ? " " : "") + reddeerVMArgsToAdd);
			}
		} catch (CoreException ce) {
			log.error("Unable to set launch configuration property : " + RedDeerJavaArgumentsTab.REDDEER_VM_ARGS_TO_ADD, ce);
		}
		
		try {
			String reddeerProgramArgsToAdd = configuration.getAttribute(RedDeerJavaArgumentsTab.REDDEER_PROGRAM_ARGS_TO_ADD, "");
			if (reddeerProgramArgsToAdd.length() > 0){
				((ILaunchConfigurationWorkingCopy)configuration).setAttribute(RedDeerJavaArgumentsTab.REDDEER_PROGRAM_ARGS_TO_ADD, "");
				String currentProgramArgs = configuration.getAttribute(RedDeerJavaArgumentsTab.PROGRAM_ARGS_ATTR_NAME, "");
				((ILaunchConfigurationWorkingCopy)configuration).setAttribute(RedDeerJavaArgumentsTab.PROGRAM_ARGS_ATTR_NAME,
						currentProgramArgs + (currentProgramArgs.length() > 0 ? " " : "") + reddeerProgramArgsToAdd);
			}
		} catch (CoreException ce) {
			log.error("Unable to set launch configuration property : " + RedDeerJavaArgumentsTab.REDDEER_PROGRAM_ARGS_TO_ADD, ce);
		}
		super.initializeFrom(configuration);

	}
}
