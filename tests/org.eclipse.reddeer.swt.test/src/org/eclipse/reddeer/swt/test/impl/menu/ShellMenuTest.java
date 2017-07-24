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

import static org.junit.Assert.*;

import org.eclipse.reddeer.junit.runner.RedDeerSuite;
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
	
}
