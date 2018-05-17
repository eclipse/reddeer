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
package org.eclipse.reddeer.swt.test.toolbar;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.hamcrest.core.Is;
import org.hamcrest.core.IsNot;
import org.eclipse.reddeer.common.matcher.RegexMatcher;
import org.eclipse.reddeer.core.matcher.WithTextMatchers;
import org.eclipse.reddeer.core.matcher.WithTooltipTextMatcher;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.swt.api.MenuItem;
import org.eclipse.reddeer.swt.api.ToolBar;
import org.eclipse.reddeer.swt.api.ToolItem;
import org.eclipse.reddeer.swt.api.TreeItem;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.clabel.DefaultCLabel;
import org.eclipse.reddeer.swt.impl.label.DefaultLabel;
import org.eclipse.reddeer.swt.impl.menu.ShellMenuItem;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.swt.impl.toolbar.DefaultToolBar;
import org.eclipse.reddeer.swt.impl.toolbar.DefaultToolItem;
import org.eclipse.reddeer.swt.impl.tree.DefaultTree;
import org.eclipse.reddeer.swt.impl.tree.DefaultTreeItem;
import org.eclipse.reddeer.workbench.impl.shell.WorkbenchShell;
import org.eclipse.reddeer.workbench.ui.dialogs.WorkbenchPreferenceDialog;
import org.eclipse.tools.reddeer.swt.test.model.TestModel;
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
		WorkbenchPreferenceDialog wd = new WorkbenchPreferenceDialog();
		wd.open();
		TreeItem item = new DefaultTreeItem("General");
		item.select();
	}
	
	private void closePreferences() {
		new DefaultTree().getItems().get(0).select();
		new PushButton("Cancel").click();
	}
}
