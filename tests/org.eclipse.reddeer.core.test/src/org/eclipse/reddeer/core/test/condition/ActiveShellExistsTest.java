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
package org.eclipse.reddeer.core.test.condition;

import static org.junit.Assert.*;

import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.core.condition.ActiveShellExists;
import org.eclipse.reddeer.swt.api.Shell;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.swt.test.utils.ShellTestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ActiveShellExistsTest {

	private Shell shell;
	
	@Before
	public void setUp() {
		Display.syncExec(() -> {
			ShellTestUtils.createShell("Test shell");
		});
		shell = new DefaultShell("Test shell");
	}
	
	@Test
	public void testActiveShellExistsGetResult() {
		shell.setFocus();
		ActiveShellExists exists = new ActiveShellExists();
		assertTrue(exists.test());
		assertEquals(shell.getSWTWidget(), exists.getResult());
	}

	@Test
	public void testNoActiveShellExists() {
		tearDown();
		ActiveShellExists exists = new ActiveShellExists();
		assertFalse(exists.test());
		assertNull(exists.getResult());
	}
	
	@After
	public void tearDown() {
		if (shell != null) {
			shell.setFocus();
			shell.close();
			shell = null;
		}
	}
}
