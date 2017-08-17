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

import java.util.List;

import org.eclipse.reddeer.common.matcher.RegexMatcher;
import org.eclipse.reddeer.core.exception.CoreLayerException;
import org.eclipse.reddeer.core.matcher.WithMnemonicTextMatcher;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.swt.api.Menu;
import org.eclipse.reddeer.swt.api.MenuItem;
import org.eclipse.reddeer.swt.impl.menu.ContextMenu;
import org.eclipse.reddeer.swt.impl.menu.ContextMenuItem;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.swt.impl.text.DefaultText;
import org.eclipse.reddeer.swt.impl.tree.DefaultTree;
import org.eclipse.reddeer.swt.impl.tree.DefaultTreeItem;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class ContextMenuTest extends AbstractMenuTest{
	
	@Test
	public void getAllMenuItems() {
		new DefaultTreeItem(new DefaultTree(new DefaultShell(SHELL_TEXT)),"TreeItem1").select();
		org.eclipse.reddeer.swt.api.Menu menu = new ContextMenu();
		List<org.eclipse.reddeer.swt.api.MenuItem> items = menu.getItems();
		assertNotNull(items);
		assertTrue(items.size() == 6);
	}
	
	@Test
	public void getAllMenuItems_viaConstructor() {
		org.eclipse.reddeer.swt.api.Menu menu = 
				new ContextMenu(new DefaultTreeItem(new DefaultTree(new DefaultShell(SHELL_TEXT)),"TreeItem1"));
		List<org.eclipse.reddeer.swt.api.MenuItem> items = menu.getItems();
		assertNotNull(items);
		assertTrue(items.size() == 6);
	}
	
	@Test
	public void getMenuItemByPath() {
		new DefaultTreeItem(new DefaultTree(new DefaultShell(SHELL_TEXT)),"TreeItem1").select();
		org.eclipse.reddeer.swt.api.Menu menu = new ContextMenu();
		menu.getItem("TreeItem1MenuItem1");
	}
	
	@Test(expected=CoreLayerException.class)
	public void getMenuItemByPathNonexisting() {
		new DefaultTreeItem(new DefaultTree(new DefaultShell(SHELL_TEXT)),"TreeItem1").select();
		org.eclipse.reddeer.swt.api.Menu menu = new ContextMenu();
		menu.getItem("New1");
	}
	
	@Test
	public void getMenuItemByMatcher() {
		new DefaultTreeItem(new DefaultTree(new DefaultShell(SHELL_TEXT)),"TreeItem1").select();
		org.eclipse.reddeer.swt.api.Menu menu = new ContextMenu();
		menu.getItem(new WithMnemonicTextMatcher(new RegexMatcher("TreeItem1Menu.*")));
	}
	
	@Test(expected=CoreLayerException.class)
	public void getMenuItemByMatcherNonExisting() {
		new DefaultTreeItem(new DefaultTree(new DefaultShell(SHELL_TEXT)),"TreeItem1").select();
		org.eclipse.reddeer.swt.api.Menu menu = new ContextMenu();
		menu.getItem(new WithMnemonicTextMatcher(new RegexMatcher("TreeItem2Menu.*")));
	}
	
	@Test
	public void menuItemGetText() {
		new DefaultTreeItem(new DefaultTree(new DefaultShell(SHELL_TEXT)),"TreeItem1").select();
		ContextMenuItem item = new ContextMenuItem("TreeItem1MenuItem1");
		assertEquals("TreeItem1MenuItem1", item.getText());
	}
	
	@Test
	public void menuItemGetMenu() {
		new DefaultTreeItem(new DefaultTree(new DefaultShell(SHELL_TEXT)),"TreeItem1").select();
		ContextMenuItem item = new ContextMenuItem("TreeItem1MenuItem1");
		assertNotNull(item.getParent());
		Menu menu = item.getParent();
		assertEquals("TreeItem1MenuItem1", menu.getItems().get(0).getText());
		assertEquals("TreeItem1MenuItem2",menu.getItems().get(1).getText());
	}
	
	@Test
	public void menuItemGetAvailableChildItems() {
		new DefaultTreeItem(new DefaultTree(new DefaultShell(SHELL_TEXT)),"TreeItem1").select();
		ContextMenuItem item = new ContextMenuItem("TreeItem1MenuItemWithMenu");
		List<MenuItem> avChildItems = item.getAvailableChildItems();
		assertTrue(avChildItems.size()==1);
		assertEquals("TreeItem1MenuItemWithMenuEnabledChild", avChildItems.get(0).getText());
		assertTrue(avChildItems.get(0).isEnabled());
	}
	
	@Test
	public void menuItemGetAllChildItems() {
		new DefaultTreeItem(new DefaultTree(new DefaultShell(SHELL_TEXT)),"TreeItem1").select();
		ContextMenuItem item = new ContextMenuItem("TreeItem1MenuItemWithMenu");
		List<MenuItem> childItems = item.getChildItems();
		assertTrue(childItems.size()==2);
		assertEquals("TreeItem1MenuItemWithMenuEnabledChild", childItems.get(0).getText());
		assertEquals("TreeItem1MenuItemWithMenuDisabledChild", childItems.get(1).getText());
		assertTrue(childItems.get(0).isEnabled());
		assertFalse(childItems.get(1).isEnabled());
	}
	
	@Test
	public void menuItemConstructor() {
		ContextMenuItem item = new ContextMenuItem(
				new DefaultTreeItem(new DefaultTree(new DefaultShell(SHELL_TEXT)),"TreeItem1"), "TreeItem1MenuItemWithMenu");
		assertEquals("TreeItem1MenuItemWithMenu", item.getText());
	}
	
	@Test
	public void menuItemConstructor1() {
		ContextMenuItem item = new ContextMenuItem(
				new DefaultTreeItem(new DefaultTree(new DefaultShell(SHELL_TEXT)),"TreeItem1"), 
				"TreeItem1MenuItemWithMenu", "TreeItem1MenuItemWithMenuEnabledChild");
		assertEquals("TreeItem1MenuItemWithMenuEnabledChild", item.getText());
	}
	
	@Test
	public void disabledMenu() {
		Menu menu = new ContextMenuItem(
				new DefaultTreeItem(new DefaultTree(new DefaultShell(SHELL_TEXT)),"TreeItem1"), 
				"DisabledMenu").getMenu();
		assertFalse(menu.isEnabled());
		assertTrue(menu.getParentMenu().isEnabled());
	}
	
	@Test
	public void selectMenuItem() {
		new DefaultTreeItem(new DefaultTree(new DefaultShell(SHELL_TEXT)),"TreeItem1").select();
		ContextMenuItem item = new ContextMenuItem("TreeItem1MenuItem1");
		item.select();
		assertEquals("selected "+item.getText(), new DefaultText().getText());
	}
	
	@Test
	public void checkMenuItem() {
		new DefaultTreeItem(new DefaultTree(new DefaultShell(SHELL_TEXT)),"TreeItem1").select();
		ContextMenuItem item = new ContextMenuItem("TreeItem1MenuItemCheck");
		assertFalse(item.isSelected());
		item.select();
		assertTrue(item.isSelected());
	}
	
	@Test
	public void radioMenuItem() {
		new DefaultTreeItem(new DefaultTree(new DefaultShell(SHELL_TEXT)),"TreeItem1").select();
		ContextMenuItem item = new ContextMenuItem("TreeItem1MenuItemRadio");
		assertFalse(item.isSelected());
		item.select();
		assertTrue(item.isSelected());
	}
	
	@Test
	public void shellContextMenu() {
		new ContextMenuItem(new DefaultShell(SHELL_TEXT),"ShellContextMenuItem");
	}



}
