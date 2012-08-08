package org.jboss.reddeer.swt.test;

import org.apache.log4j.Logger;
import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.impl.menu.WorkbenchMenu;
import org.jboss.reddeer.swt.impl.shell.ActiveShell;
import org.junit.Test;

public class MenuTest {
	
	protected final Logger log = Logger.getLogger(this.getClass());

	@Test
	public void menuTest() {
		log.info("menu test");
		new ActiveShell();
		Menu m = new WorkbenchMenu();
		m.select("Window","Preferences");
		Shell s = new ActiveShell("Preferences");
		s.close();
	}
	
}
