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
