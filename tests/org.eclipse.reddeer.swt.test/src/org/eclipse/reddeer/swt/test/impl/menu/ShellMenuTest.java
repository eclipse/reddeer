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
package org.eclipse.reddeer.swt.test.impl.menu;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.swt.api.Menu;
import org.eclipse.reddeer.swt.api.Shell;
import org.eclipse.reddeer.swt.impl.menu.ContextMenu;
import org.eclipse.reddeer.swt.impl.menu.ShellMenu;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class ShellMenuTest extends AbstractMenuTest{
	
	@Test
	public void shellMenuTest() {
		new DefaultShell(SHELL_TEXT);
		ShellMenu menu = new ShellMenu();
		assertTrue(menu.getItems().size() == 1);
		assertEquals("ShellMenuBarItem",menu.getItems().get(0).getText());
		menu.getItem("ShellMenuBarItem","ShellMenuBarItem1");
	}
	
	@Test
	public void shellMenuTest1() {
		ShellMenu menu = new ShellMenu(new DefaultShell(SHELL_TEXT));
		assertTrue(menu.getItems().size() == 1);
		assertEquals("ShellMenuBarItem",menu.getItems().get(0).getText());
		menu.getItem("ShellMenuBarItem","ShellMenuBarItem1");
	}

	@Test
	public void hasExistingMenuItem() {
		Shell shell = new DefaultShell(SHELL_TEXT);
		Menu menu = new ShellMenu(shell);
		assertTrue(menu.hasItem("ShellMenuBarItem"));
	}

	@Test
	public void hasExistingMenuItemWithPath() {
		Shell shell = new DefaultShell(SHELL_TEXT);
		Menu menu = new ShellMenu(shell);
		assertTrue(menu.hasItem("ShellMenuBarItem", "ShellMenuBarItem1"));
	}

	@Test
	public void hasNonExistingMenuItem() {
		Shell shell = new DefaultShell(SHELL_TEXT);
		Menu menu = new ContextMenu(shell);
		assertFalse(menu.hasItem("ShellMenuBarItemX"));
	}

}
