package org.jboss.reddeer.swt.test;

import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
import org.junit.Test;

public class TreeTest extends RedDeerTest {


	@Test
	public void foundRegexShellTest() {
		new DefaultShell();
		Menu m = new ShellMenu("Window","Preferences");
		m.select();
		Shell s = new DefaultShell("Preferences");
		TreeItem item = new DefaultTreeItem("General","Appearance");
		item.select();
		s.close();
	}
	
	
}
