package org.jboss.reddeer.swt.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.hamcrest.core.Is;
import org.hamcrest.core.IsNot;
import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.swt.api.ToolBar;
import org.jboss.reddeer.swt.api.ToolItem;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.clabel.DefaultCLabel;
import org.jboss.reddeer.swt.impl.label.DefaultLabel;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.shell.WorkbenchShell;
import org.jboss.reddeer.swt.impl.toolbar.DefaultToolItem;
import org.jboss.reddeer.swt.impl.toolbar.ShellToolBar;
import org.jboss.reddeer.swt.impl.toolbar.ShellToolItem;
import org.jboss.reddeer.swt.impl.toolbar.ViewToolBar;
import org.jboss.reddeer.swt.impl.toolbar.WorkbenchToolItem;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
import org.jboss.reddeer.swt.matcher.WithRegexMatcher;
import org.jboss.reddeer.swt.matcher.WithRegexMatchers;
import org.jboss.reddeer.workbench.impl.view.WorkbenchView;
import org.jboss.tools.reddeer.swt.test.model.TestModel;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for various toolbar implementations
 * @author Jiri Peterka
 *
 */
public class ToolBarTest extends RedDeerTest {

	@Before
	public void prepare() {
		new WorkbenchView("RedDeer SWT").open();
	}
	
	@Test 
	public void workbenchToolBarTest() {
		
		ToolItem i = new WorkbenchToolItem("RedDeer SWT WorkbenchToolItem");
		i.click();
		assertTrue("ToolItem should be clicked", TestModel.getClickedAndReset());
	}
	
	@Test 
	public void workbenchToolBarRegexTest() {
		
		WithRegexMatcher rm = new WithRegexMatcher("RedDeer SWT Workbench.*");
		ToolItem i = new WorkbenchToolItem(rm);
		i.click();
		assertTrue("ToolItem should be clicked", TestModel.getClickedAndReset());
	}

	@Test
	public void testToolBar() {
		new WorkbenchShell();
		ToolBar t = new ViewToolBar();
		assertNotNull(t);
	}

	@Test
	public void testToolItemInViewToolBarFound() {
		ToolItem i = new DefaultToolItem("RedDeer SWT ViewToolItem");
		assertEquals("RedDeer SWT ViewToolItem", i.getToolTipText());
	}

	@Test
	public void testToolItemInViewToolBarClicked() {
		ToolItem i = new DefaultToolItem("RedDeer SWT ViewToolItem");
		i.click();		
		assertTrue("ToolItem should be clicked", TestModel.getClickedAndReset());		
	}

	@Test
	public void testToolItemInViewToolBarRegexClicked() {
		WithRegexMatcher rm = new WithRegexMatcher("RedDeer SWT View.*");
		ToolItem i = new DefaultToolItem(rm);
		i.click();
		assertTrue("ToolItem should be clicked", TestModel.getClickedAndReset());		
	}
	
	@Test
	public void testShellToolBar() {
		openPreferences();
		ToolBar t = new ShellToolBar();
		assertNotNull(t);
		closePreferences();	
	}
	
	@Test
	public void testToolItemInShellToolBarFound() {
		openPreferences();
		ToolItem ti = new ShellToolItem();
		assertNotNull(ti);
		closePreferences();	
	}
	
	@Test
	public void testToolItemInShellToolBarClicked() {
		openPreferences();
		assertThat(new DefaultCLabel().getText(), Is.is("General"));
		new DefaultTreeItem(1).select();
		assertThat(new DefaultLabel().getText(), IsNot.not("General"));
		ToolItem ti = new ShellToolItem();
		ti.click();
		assertThat(new DefaultCLabel().getText(), Is.is("General"));
		closePreferences();		
	}

	@Test
	public void testToolItemInShellToolBarRegexClicked() {
		openPreferences();
		new DefaultTreeItem(1).select();
		ToolItem ti = new ShellToolItem(new WithRegexMatcher(".*ack.*"));
		assertNotNull(ti);
		closePreferences();			
	}
	
	private void openPreferences() {
		WithRegexMatchers m = new WithRegexMatchers("Window.*", "Preferences.*");
		Menu menu = new ShellMenu(m.getMatchers());
		menu.select();
		new DefaultShell("Preferences");
		TreeItem item = new DefaultTreeItem("General");
		item.select();
	}
	
	private void closePreferences() {
		new DefaultTreeItem(0).select();
		new PushButton("Cancel").click();
	}
}
