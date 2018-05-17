/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.eclipse.debug.ui.launchConfigurations;

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
