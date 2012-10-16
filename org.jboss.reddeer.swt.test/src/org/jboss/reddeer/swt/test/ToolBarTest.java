package org.jboss.reddeer.swt.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.swt.api.ToolBar;
import org.jboss.reddeer.swt.api.ToolItem;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.shell.WorkbenchShell;
import org.jboss.reddeer.swt.impl.toolbar.ViewToolBar;
import org.jboss.reddeer.swt.impl.toolbar.ViewToolItem;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
import org.jboss.reddeer.swt.matcher.RegexMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

public class ToolBarTest {

	@BeforeClass
	public static void prepare() {
		
		WorkbenchShell ws = new WorkbenchShell();
		String title = ws.getText();
		
		RegexMatchers m = new RegexMatchers("Window.*", "Show View.*",
				"Other...*");
		Menu menu = new ShellMenu(m.getMatchers());
		menu.select();
				
		DefaultTreeItem item = new DefaultTreeItem("RedDeer SWT","RedDeer SWT");
		item.select();
		new PushButton("OK").click();
		
		new DefaultShell(title);
	}
	
	@Test
	public void testToolBar() {
		new WorkbenchShell();
		ToolBar t = new ViewToolBar();
		assertNotNull(t);
	}

	@Test
	public void testToolItemInViewToolBarFound() {
		ToolItem i = new ViewToolItem("RedDeer SWT ToolItem");
		assertEquals("RedDeer SWT ToolItem", i.getToolTipText()); 
	}

	@Test
	public void testToolItemInViewToolBarClicked() {
		ToolItem i = new ViewToolItem("RedDeer SWT ToolItem");
		i.click();
	}

}
