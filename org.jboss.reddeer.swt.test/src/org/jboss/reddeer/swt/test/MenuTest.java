package org.jboss.reddeer.swt.test;

import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.impl.menu.WorkbenchMenu;
import org.jboss.reddeer.swt.impl.shell.ActiveShell;
import org.junit.Test;

public class MenuTest {

	@Test
	public void menuTest() {
		new ActiveShell();
		Menu m = new WorkbenchMenu();
		m.select("Window","Preferences");
		Shell s = new ActiveShell("Preferences");
		s.close();
	}

}
