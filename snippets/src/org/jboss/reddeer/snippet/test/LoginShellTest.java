package org.jboss.reddeer.snippet.test;

import org.jboss.reddeer.snippet.test.example.LoginShell;
import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.workbench.impl.shell.WorkbenchShell;
import org.junit.Test;

public class LoginShellTest {

	@Test
	public void testMyLoginShell() {
	  new WorkbenchShell();
	  Menu shellMenu = new ShellMenu("MyEclipseProduct","Login to My Eclipse product");
	  shellMenu.select();

	  // Your own RedDeer API usage
	  LoginShell shell = new LoginShell();
	  shell.setUsername("tester");
	  shell.setPassword("secret");
	  shell.login();
	}
	
}
