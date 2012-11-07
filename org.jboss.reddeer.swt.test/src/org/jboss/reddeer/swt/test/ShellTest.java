package org.jboss.reddeer.swt.test;

import static org.junit.Assert.assertFalse;

import org.jboss.reddeer.swt.test.RedDeerTest;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.shell.WorkbenchShell;
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
}
