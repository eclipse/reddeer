package org.jboss.reddeer.eclipse.ui.launcher;

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.variables.IStringVariableManager;
import org.eclipse.core.variables.VariablesPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.pde.ui.launcher.JUnitLaunchConfigurationDelegate;
import org.jboss.reddeer.ui.Activator;

/**
 * A launch delegate for launching JUnit Plug-in tests.
 * 
 * @author sbunciak
 * @since 0.2
 */
@SuppressWarnings("deprecation")
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
		
		for (RedDeerLauncherProperties property : properties){
			if (property.getCurrentValue() == null || "".equals(property.getCurrentValue())){
				continue;
			}
			String substituedVariables = mgr.performStringSubstitution(property.getCurrentValue());
			vmArguments.add("-D" + property.getProperty().getName() + "=" + substituedVariables);
		}
	}
}
