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
package org.jboss.reddeer.eclipse.debug.ui.launchConfigurations;

/**
 * Represents Debug configurations dialog 
 * 
 * @author Lucia Jelinkova
 *
 */
public class DebugConfigurationDialog extends LaunchConfigurationDialog {

	public static final String DIALOG_TITLE = "Debug Configurations";
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.eclipse.debug.ui.launchConfigurations.LaunchConfigurationDialog#getTitle()
	 */
	@Override
	public String getTitle() {
		return DIALOG_TITLE;
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.eclipse.debug.ui.launchConfigurations.LaunchConfigurationDialog#getMenuItemName()
	 */
	@Override
	protected String getMenuItemName() {
		return "Debug Configurations...";
	}
}
