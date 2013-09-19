package org.jboss.reddeer.swt.test;

import static org.junit.Assert.assertFalse;

import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.shell.WorkbenchShell;
import org.jboss.reddeer.swt.util.Display;
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
	public void closeShellTest() {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				org.eclipse.swt.widgets.Display display = Display.getDisplay();
				org.eclipse.swt.widgets.Shell shell = new org.eclipse.swt.widgets.Shell(display);
				shell.setText("Dummy shell");
				shell.open();
				shell.setFocus();
			}
		});
		
		Shell dummyShell = new DefaultShell("Dummy shell");
		dummyShell.close();
	}

}
