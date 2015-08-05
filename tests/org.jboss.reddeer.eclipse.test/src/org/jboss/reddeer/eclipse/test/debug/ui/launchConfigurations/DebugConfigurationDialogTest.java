package org.jboss.reddeer.eclipse.test.debug.ui.launchConfigurations;

import org.jboss.reddeer.eclipse.debug.ui.launchConfigurations.DebugConfigurationDialog;
import org.junit.Before;

public class DebugConfigurationDialogTest extends AbstractLaunchConfigurationDialogTest {

	@Before
	public void setup(){
		dialog = new DebugConfigurationDialog();
	}
	
	@Override
	protected String getConfigurationName() {
		return this.getClass() + "_test_configuration";
	}
}
