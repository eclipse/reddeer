package org.jboss.reddeer.eclipse.test.debug.ui.launchConfigurations;

import org.jboss.reddeer.eclipse.debug.ui.launchConfigurations.RunConfigurationDialog;
import org.junit.Before;

public class RunConfigurationDialogTest extends AbstractLaunchConfigurationDialogTest {

	@Before
	public void setup(){
		dialog = new RunConfigurationDialog();
	}
	
	@Override
	protected String getConfigurationName() {
		return this.getClass() + "_test_configuration";
	}
}
