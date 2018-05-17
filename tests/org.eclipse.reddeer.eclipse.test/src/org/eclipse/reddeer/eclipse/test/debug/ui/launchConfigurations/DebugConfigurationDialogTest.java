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

import org.eclipse.reddeer.eclipse.debug.ui.launchConfigurations.DebugConfigurationsDialog;
import org.junit.Before;

public class DebugConfigurationDialogTest extends AbstractLaunchConfigurationDialogTest {

	@Before
	public void setup(){
		dialog = new DebugConfigurationsDialog();
	}
	
	@Override
	protected String getConfigurationName() {
		return this.getClass() + "_test_configuration";
	}
}
