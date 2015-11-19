package org.jboss.reddeer.eclipse.test.debug.ui.launchConfigurations;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.jboss.reddeer.common.exception.RedDeerException;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.core.condition.ShellWithTextIsAvailable;
import org.jboss.reddeer.core.exception.CoreLayerException;
import org.jboss.reddeer.eclipse.debug.ui.launchConfigurations.LaunchConfiguration;
import org.jboss.reddeer.eclipse.debug.ui.launchConfigurations.LaunchConfigurationDialog;
import org.jboss.reddeer.eclipse.ui.perspectives.JavaPerspective;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.requirements.openperspective.OpenPerspectiveRequirement.OpenPerspective;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
@OpenPerspective(JavaPerspective.class)
public abstract class AbstractLaunchConfigurationDialogTest {

	protected LaunchConfigurationDialog dialog;
	
	protected abstract String getConfigurationName();
	
	@After
	public void closeDialog(){
		try {
			new WaitUntil(new ShellWithTextIsAvailable(dialog.getTitle()), TimePeriod.NONE);
			new DefaultShell(dialog.getTitle()).close();
		} catch (RedDeerException e){
			// already closed
		}
	}
	
	@Test
	public void createSelectDelete(){
		JUnitConfiguration configuration = new JUnitConfiguration();
		
		dialog.open();
		dialog.create(configuration);
		dialog.create(configuration, getConfigurationName());
		
		dialog.select(configuration);
		
		assertTrue(new DefaultTreeItem(configuration.getType()).isSelected());
		
		dialog.select(configuration, getConfigurationName());
		assertTrue(new DefaultTreeItem(configuration.getType(), getConfigurationName()).isSelected());
	
		dialog.delete(configuration, getConfigurationName());
		try {
			new DefaultTreeItem(configuration.getType(), getConfigurationName());
			fail("The configuration shoud have been deleted");
		} catch (RedDeerException e){
			// ok, this is expected
		}
	}
	
	private class JUnitConfiguration extends LaunchConfiguration {

		public JUnitConfiguration() {
			super("JUnit");
		}
	}
}
