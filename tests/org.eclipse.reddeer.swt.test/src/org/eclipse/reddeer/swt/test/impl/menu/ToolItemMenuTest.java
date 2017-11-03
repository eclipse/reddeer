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

import org.eclipse.reddeer.core.exception.CoreLayerException;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.swt.api.Menu;
import org.eclipse.reddeer.swt.api.ToolItem;
import org.eclipse.reddeer.swt.impl.menu.ToolItemMenu;
import org.eclipse.reddeer.swt.impl.menu.ToolItemMenuItem;
import org.eclipse.reddeer.swt.impl.toolbar.DefaultToolItem;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class ToolItemMenuTest extends AbstractMenuTest {

	@Test
	public void toolItemMenuItem() {
		new ToolItemMenuItem(new DefaultToolItem(shell, "toolItemMenu"), "ToolItemMenuItem1");
	}

	@Test
	public void toolItemMenu() {
		Menu toolItemMenu = new ToolItemMenu(new DefaultToolItem(shell, "toolItemMenu"));
		assertEquals(3, toolItemMenu.getItems().size());
	}

	@Test(expected = CoreLayerException.class)
	public void toolItemMenuWrongStyle() {
		new ToolItemMenu(new DefaultToolItem(shell, "genericToolItem"));
	}

	@Test
	public void hasExistingMenuItem() {
		ToolItem toolItem = new DefaultToolItem(shell, "toolItemMenu");
		Menu menu = new ToolItemMenu(toolItem);
		assertTrue(menu.hasItem("ToolItemMenuItem1"));
	}

	@Test
	public void hasNonExistingMenuItem() {
		ToolItem toolItem = new DefaultToolItem(shell, "toolItemMenu");
		Menu menu = new ToolItemMenu(toolItem);
		assertFalse(menu.hasItem("ToolItemMenuItemX"));
	}

}