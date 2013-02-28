package org.jboss.reddeer.swt.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jboss.reddeer.swt.test.RedDeerTest;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.shell.WorkbenchShell;
import org.jboss.reddeer.swt.impl.tree.ShellTreeItem;
import org.junit.Test;

public class ShellTest extends RedDeerTest {

	@Test
	public void workbenchShellTest() {
		Shell shell = new WorkbenchShell();
		assertFalse(shell.getText().equals(""));
	}

	@Test
	public void DefaultShellTest() {
		Shell shell = new DefaultShell();
		assertFalse(shell.getText().equals(""));
	}
	
	@Test
	public void activateShellTest(){
		Shell shell = new WorkbenchShell();
		assertTrue(shell.isActive());
		new ShellMenu("File", "New", "Other...").select();
		Shell shellNew = new DefaultShell("New");
		assertFalse(shell.isActive());
		assertTrue(shellNew.isActive());
		//activate first shell
		shell.activate();
		assertTrue(shell.isActive());
		assertFalse(shellNew.isActive());
		shellNew.close();
	}
}
