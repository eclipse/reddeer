package org.jboss.reddeer.swt.test;

import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.impl.menu.WorkbenchMenu;
import org.jboss.reddeer.swt.impl.shell.ActiveShell;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
import org.junit.Test;

public class TreeTest {


	@Test
	public void foundRegexShellTest() {
		new ActiveShell();
		Menu m = new WorkbenchMenu("Window","Preferences");
		m.select();
		Shell s = new ActiveShell("Preferences");
		TreeItem item = new DefaultTreeItem("General","Appearance");
		item.select();
		s.close();
	}
	
	
}
