package org.jboss.reddeer.swt.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.label.DefaultLabel;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.shell.WorkbenchShell;
import org.jboss.reddeer.swt.util.Display;
import org.junit.Test;

public class ShellTest extends RedDeerTest {
	private Shell shell1,shell2;
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
	
	@Test
	public void maximizeWorkbenshShellTest() {
		WorkbenchShell workbenchShell = new WorkbenchShell();
		assertFalse(workbenchShell.isMaximized());
		workbenchShell.maximize();
		assertTrue(workbenchShell.isMaximized());
		workbenchShell.restore();
		assertFalse(workbenchShell.isMaximized());
	}

	@Test
	public void activateShellTest() {
		createSimpleShell("Shell 1");
		createSimpleShell("Shell 2");

		shell2 = new DefaultShell("Shell 2");
		new DefaultLabel("Shell 2");

		shell1 = new DefaultShell("Shell 1");
		new DefaultLabel("Shell 1");

		new DefaultShell("Shell 2");
		new DefaultLabel("Shell 2");

		try {
			new DefaultLabel("Shell 1");
			fail("Label 'Shell 1' should be in inactive shell!");
		} catch (SWTLayerException swtle) {
			// ok, we expect an exception
		}

	}

	@Test
	public void closeAllShellsTest() {
		createSimpleShell("Shell 1");
		createSimpleShell("Shell 2");
		
		new WorkbenchShell().closeAllShells();
		
		try {
			new DefaultShell("Shell 1");
			fail("'Shell 1' should be closed");
		} catch (SWTLayerException e) {
			// ok
		}

		try {
			new DefaultShell("Shell 2");
			fail("'Shell 2' should be closed");
		} catch (SWTLayerException e) {
			// ok
		}
	}
	
	private static void createSimpleShell(final String title) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				org.eclipse.swt.widgets.Display display = Display.getDisplay();
				org.eclipse.swt.widgets.Shell shell = new org.eclipse.swt.widgets.Shell(display);
				shell.setText(title);
				Label swtLabel = new Label(shell, SWT.BORDER);
				swtLabel.setText(title);
				swtLabel.setSize(100, 30);
				swtLabel.setLocation(100, 50);
				shell.open();
			}
		});
	}
	@Override
	protected void tearDown(){
		if (shell1 != null){
			shell1.close();
		}
		if (shell2 != null){
			shell2.close();
		}
	}

}
