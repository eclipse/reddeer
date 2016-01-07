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
package org.jboss.reddeer.swt.test;

import org.eclipse.swt.widgets.Shell;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.test.utils.ShellTestUtils;
import org.jboss.reddeer.core.util.Display;
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
