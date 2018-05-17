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

import org.eclipse.reddeer.swt.impl.ctab.DefaultCTabItem;

/**
 * Represents one tab in Launch configuration. One tab can be used in 
 * different configuratins. 
 * 
 * @author Lucia Jelinkova
 *
 */
public abstract class LaunchConfigurationTab {
	
	private String name;
	
	/**
	 * Construct launch configuration tab with the given name.
	 *
	 * @param name Tab name
	 */
	public LaunchConfigurationTab(String name) {
		this.name = name;
	}
	
	/**
	 * Activates the tab.
	 */
	public void activate(){
		new DefaultCTabItem(name).activate();
	}
}
