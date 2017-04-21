/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.eclipse.test.debug.ui.launchConfigurations;

import org.eclipse.reddeer.eclipse.debug.ui.launchConfigurations.RunConfigurationsDialog;
import org.junit.Before;

public class RunConfigurationDialogTest extends AbstractLaunchConfigurationDialogTest {

	@Before
	public void setup(){
		dialog = new RunConfigurationsDialog();
	}
	
	@Override
	protected String getConfigurationName() {
		return this.getClass() + "_test_configuration";
	}
}
