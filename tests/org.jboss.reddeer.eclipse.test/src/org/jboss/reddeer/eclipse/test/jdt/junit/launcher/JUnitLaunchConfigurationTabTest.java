package org.jboss.reddeer.eclipse.test.jdt.junit.launcher;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.jboss.reddeer.core.exception.CoreLayerException;
import org.jboss.reddeer.eclipse.debug.ui.launchConfigurations.LaunchConfigurationDialog;
import org.jboss.reddeer.eclipse.debug.ui.launchConfigurations.RunConfigurationDialog;
import org.jboss.reddeer.eclipse.jdt.junit.launcher.JUnitLaunchConfiguration;
import org.jboss.reddeer.eclipse.jdt.junit.launcher.JUnitLaunchConfigurationTab;
import org.jboss.reddeer.eclipse.ui.perspectives.JavaPerspective;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.requirements.openperspective.OpenPerspectiveRequirement.OpenPerspective;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
@OpenPerspective(JavaPerspective.class)
public class JUnitLaunchConfigurationTabTest {
	
	private static final String CONFIGURATION_NAME = JUnitLaunchConfigurationTabTest.class + "_test_config";

	protected LaunchConfigurationDialog dialog;

	@Before
	public void openDialog(){
		dialog = new RunConfigurationDialog();
		dialog.open();
	}
	
	@After
	public void closeDialog(){
		try {
			new DefaultShell(dialog.getTitle()).close();
		} catch (CoreLayerException e){
			// already closed
		}
	}
	
	@Test
	public void testJUnitTab() {
		dialog.create(new JUnitLaunchConfiguration(), CONFIGURATION_NAME);
		
		JUnitLaunchConfigurationTab tab = new JUnitLaunchConfigurationTab();
		tab.activate();
		
		tab.setProject("abc");
		assertThat(tab.getProject(), is("abc"));
		
		tab.setTestClass("cde");
		assertThat(tab.getTestClass(), is("cde"));
		
		tab.setTestMethod("efg");
		assertThat(tab.getTestMethod(), is("efg"));
	}
}
