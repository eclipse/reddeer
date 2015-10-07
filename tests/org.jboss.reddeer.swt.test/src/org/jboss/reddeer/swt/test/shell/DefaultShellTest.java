package org.jboss.reddeer.swt.test.shell;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.core.condition.ShellWithTextIsActive;
import org.jboss.reddeer.core.exception.CoreLayerException;
import org.jboss.reddeer.core.handler.ShellHandler;
import org.jboss.reddeer.core.util.Display;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.label.DefaultLabel;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class DefaultShellTest {
	

	private Shell shell1,shell2;

	@Test
	public void defaultShellTest() {
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
		} catch (CoreLayerException cle) {
			// ok, we expect an exception
		}

	}
	
	@Test
	public void testEmptyTitleShell() {
		createSimpleShell("");
		shell1 = new DefaultShell("");
		assertTrue(shell1.getText().equals(""));
	}

	@Test
	public void testEmptyTitleShellWithCondition() {
		createSimpleShell("");
		new WaitUntil(new ShellWithTextIsActive(""));
		shell1 = new DefaultShell("");
		assertTrue(shell1.getText().equals(""));
	}
	
	@Test
	public void closeAllShellsTest() {
		createSimpleShell("Shell 1");
		createSimpleShell("Shell 2");
		
		ShellHandler.getInstance().closeAllNonWorbenchShells();
		
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
	
	@Test
	public void closeAllShellsTest2() {
			
		// Show View window
		new ShellMenu("Window", "Show View", "Other...").select();
		ShellHandler.getInstance().closeAllNonWorbenchShells();
		if (!checkShell("Show View")) fail("'Show View' should be closed");
		
		// New Server Runtime window
		new ShellMenu("Window", "Preferences").select();
		new DefaultTreeItem("Server", "Runtime Environments").select();
		new PushButton("Add...").click();
		ShellHandler.getInstance().closeAllNonWorbenchShells();
		
		if (!checkShell("New Server Runtime Environment")) fail("'New Server Runtime Environment' should be closed");
		if (!checkShell("Preferences")) fail("'Preferences' should be closed");
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
	
	@After
	public void tearDown(){
		if (shell1 != null){
			shell1.close();
		}
		if (shell2 != null){
			shell2.close();
		}
	}
	
	/**
	 * Checks whether the shell with specified name is open.
	 * Closes shell and return false, if is open. 
	 * 
	 * @param name Title of a shell
	 * @return <b>true</b> - the shell is closed
	 *         <b>false</b> - the shell was opened
	 */
	private boolean checkShell(String name) {
		
		try {
			new DefaultShell(name);
		} catch (SWTLayerException ex) {
			return true;
		}
		
		new DefaultShell().close();
		return false;
	}
}
