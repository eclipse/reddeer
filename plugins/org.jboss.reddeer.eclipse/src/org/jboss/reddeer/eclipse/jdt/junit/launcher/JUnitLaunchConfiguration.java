/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
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
	 * Constructs JUnit launch configuration.
	 */
	public JUnitLaunchConfiguration() {
		super("JUnit");
	}
}
