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
package org.eclipse.reddeer.swt.test.condition;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.eclipse.reddeer.common.exception.WaitTimeoutExpiredException;
import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.core.matcher.WithLabelMatcher;
import org.eclipse.reddeer.core.matcher.WithTextMatcher;
import org.eclipse.reddeer.swt.api.Shell;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.swt.test.utils.ShellTestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for ShellIsAvailable condition, shell from RedDeer SWT layer
 * @author odockal
 *
 */
public class ShellIsAvailableTest {

	private Shell shell;
	
	@Before
	public void setUp() {
		String shellTitle = "shell";
		Display.syncExec(() -> {
			ShellTestUtils.createShell(shellTitle);
		});
		this.shell = new DefaultShell(shellTitle);
	}

	@After
	public void tearDown() {
		closeShell();
	}
	
	private void closeShell() {
		if (this.shell != null) {
			if (!this.shell.isDisposed() ) {
				this.shell.close();
			}	
			this.shell = null;
		}
	}
	
	@Test
	public void testClosedShell() {
		ShellIsAvailable isAvailable = new ShellIsAvailable(this.shell.getText());
		assertTrue(isAvailable.test());
		this.shell.close();
		assertFalse(isAvailable.test());
	}

	@Test
	public void testMinimizeMaximize() {
		ShellIsAvailable isAvailable = new ShellIsAvailable(this.shell.getText());
		assertTrue(isAvailable.test());
		this.shell.minimize();
		assertTrue(isAvailable.test());
		this.shell.maximize();
		assertTrue(isAvailable.test());
	}
	
	@Test
	public void testShellWithText() {
		ShellIsAvailable shellAvailable = new ShellIsAvailable(this.shell.getText());
		assertTrue(shellAvailable.test());
	}
	
	@Test
	public void testShellWithTextUnavailable() {
		closeShell();
		ShellIsAvailable shellAvailable = new ShellIsAvailable("shell");
		assertFalse(shellAvailable.test());
	}
	
	@Test
	public void testShellWithMatcher() {
		ShellIsAvailable shellAvailable = new ShellIsAvailable(new WithTextMatcher(this.shell.getText()));
		assertTrue(shellAvailable.test());
	}
	
	@Test
	public void testShellWithMatcherUnavailable() {
		closeShell();
		ShellIsAvailable shellAvailable = new ShellIsAvailable(new WithTextMatcher("shell"));
		assertFalse(shellAvailable.test());
	}
	
	@Test
	public void testShellWithLabelMatcherUnavailable() {
		ShellIsAvailable shellAvailable = new ShellIsAvailable(new WithLabelMatcher(this.shell.getText()));
		assertFalse(shellAvailable.test());
	}

	@Test
	public void testGetResultShell() {
		ShellIsAvailable shellAvailable = new ShellIsAvailable(this.shell.getText());
		assertTrue(shellAvailable.test());
		assertTrue(shellAvailable.getResult().equals(this.shell.getSWTWidget()));
	}

	@Test
	public void testGetResultNull() {
		ShellIsAvailable shellAvailable = new ShellIsAvailable("Another Shell");
		assertFalse(shellAvailable.test());
		assertNull(shellAvailable.getResult());
	}
	
	@Test
	public void testWaitWhile() {
		ShellIsAvailable isAvailable = new ShellIsAvailable(this.shell.getText());
		try {
			new WaitWhile(isAvailable, TimePeriod.SHORT, true);
			fail("Shell should have been available");
		} catch (WaitTimeoutExpiredException e) {
			assertNotNull(isAvailable.getResult());
		}
	}
	
	@Test
	public void testWaitUntil() {
		ShellIsAvailable isAvailable = new ShellIsAvailable(this.shell.getText());
		try {
			new WaitUntil(isAvailable, TimePeriod.SHORT, true);
			assertNotNull(isAvailable.getResult());
		} catch (WaitTimeoutExpiredException e) {
			fail("Shell should have been available");
		}
	}
	
}
