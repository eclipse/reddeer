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
package org.jboss.reddeer.swt.test.toolbar;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.hamcrest.core.Is;
import org.hamcrest.core.IsNot;
import org.jboss.reddeer.common.matcher.RegexMatcher;
import org.jboss.reddeer.core.matcher.WithTextMatchers;
import org.jboss.reddeer.core.matcher.WithTooltipTextMatcher;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.swt.api.ToolBar;
import org.jboss.reddeer.swt.api.ToolItem;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.clabel.DefaultCLabel;
import org.jboss.reddeer.swt.impl.label.DefaultLabel;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.toolbar.DefaultToolBar;
import org.jboss.reddeer.swt.impl.toolbar.DefaultToolItem;
import org.jboss.reddeer.swt.impl.tree.DefaultTree;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
import org.jboss.reddeer.workbench.impl.shell.WorkbenchShell;
import org.jboss.tools.reddeer.swt.test.model.TestModel;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class DefaultToolBarTest {

	@Test
	public void testDefaultToolBar() {
		openPreferences();
		final ToolBar t = new DefaultToolBar();
		assertNotNull(t);
		closePreferences();	
	}
	
	@Test 
	public void workbenchToolBarTest() {		
		ToolItem i = new DefaultToolItem(new WorkbenchShell(), "RedDeer SWT WorkbenchToolItem");
		i.click();
		assertTrue("ToolItem should be clicked", TestModel.getClickedAndReset());
	}
	
	@Test 
	public void workbenchToolBarRegexTest() {
		WithTooltipTextMatcher matcher = new WithTooltipTextMatcher(
				new RegexMatcher("RedDeer SWT Workbench.*"));
		ToolItem i = new DefaultToolItem(new WorkbenchShell(), matcher);
		i.click();
		assertTrue("ToolItem should be clicked", TestModel.getClickedAndReset());
	}
	
	
	@Test
	public void testToolItemInShellToolBarFound() {
		openPreferences();
		ToolItem ti = new DefaultToolItem();
		assertNotNull(ti);
		closePreferences();	
	}
	
	@Test
	public void testToolItemInShellToolBarClicked() {
		openPreferences();
		assertThat(new DefaultCLabel().getText(), Is.is("General"));
		new DefaultTree().getItems().get(1).select();
		assertThat(new DefaultLabel().getText(), IsNot.not("General"));
		ToolItem ti = new DefaultToolItem();
		ti.click();
		assertThat(new DefaultCLabel().getText(), Is.is("General"));
		closePreferences();		
	}

	@Test
	public void testToolItemInShellToolBarRegexClicked() {
		openPreferences();
		new DefaultTree().getItems().get(1).select();
		ToolItem ti = new DefaultToolItem(new WithTooltipTextMatcher(
				new RegexMatcher(".*ack.*")));
		assertNotNull(ti);
		closePreferences();			
	}
	
	private void openPreferences() {
		RegexMatcher[] array = { new RegexMatcher("Window.*"),
				new RegexMatcher("Preferences.*") };
		WithTextMatchers m = new WithTextMatchers(array);
		Menu menu = new ShellMenu(m.getMatchers());
		menu.select();
		new DefaultShell("Preferences");
		TreeItem item = new DefaultTreeItem("General");
		item.select();
	}
	
	private void closePreferences() {
		new DefaultTree().getItems().get(0).select();
		new PushButton("Cancel").click();
	}
}
