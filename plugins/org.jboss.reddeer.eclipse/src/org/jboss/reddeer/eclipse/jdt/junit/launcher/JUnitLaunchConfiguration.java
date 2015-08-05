package org.jboss.reddeer.eclipse.jdt.junit.launcher;

import org.jboss.reddeer.eclipse.debug.ui.launchConfigurations.LaunchConfiguration;

/**
 * Represents configuration for running JUnit tests.  
 * 
 * @author Lucia Jelinkova
 *
 */
public class JUnitLaunchConfiguration extends LaunchConfiguration {

	/**
	 * Constructs JUnit launch configuration
	 */
	public JUnitLaunchConfiguration() {
		super("JUnit");
	}
}
