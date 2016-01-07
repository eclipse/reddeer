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
