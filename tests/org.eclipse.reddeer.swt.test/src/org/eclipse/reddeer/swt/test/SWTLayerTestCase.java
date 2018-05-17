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
package org.eclipse.reddeer.swt.test;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.swt.test.utils.ShellTestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public abstract class SWTLayerTestCase {
	
	protected static final String SHELL_TITLE = "Testing shell";

	protected abstract void createControls(Shell shell);
	
	@Before
	public void setUp() {
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				Shell shell = ShellTestUtils.createShell(SHELL_TITLE);
				createControls(shell);
				shell.layout();
			}
		});
	}
	
	@After
	public void cleanup() {
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				ShellTestUtils.closeShell(SHELL_TITLE);
			}
		});
	}
}
