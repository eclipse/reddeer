package org.jboss.reddeer.swt.handler;

import org.eclipse.swt.widgets.Shell;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;

/**
 * Contains methods that handle UI operations on {@link Shell} widgets. 
 * @author Jiri Peterka
 *
 */
public class ShellHandler {

	private static ShellHandler instance;

	private ShellHandler() {

	}

	/**
	 * Creates and returns instance of ShellHandler class
	 * 
	 * @return
	 */
	public static ShellHandler getInstance() {
		if (instance == null) {
			instance = new ShellHandler();
		}
		return instance;
	}


	/**
	 * Closes given shell
	 * @param swtShell given SWT shell instance
	 */
	public void closeShell(final Shell swtShell) {

		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				if (swtShell != null && !swtShell.isDisposed()) {
					swtShell.close();
				}
			}
		});
	}

	/**
	 * Focuses specified shell
	 * @param shell given SWT shell instance
	 */
	public void setFocus(final Shell shell) {
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				shell.forceActive();
				shell.forceFocus();
			}
		});
	}
	
	/**
	 * Checks if shell is visible
	 * @param shell given SWT shell instance
	 * @return true if shell is visible, false otherwise
	 */
	public boolean isVisible(final Shell shell) {
		return Display.syncExec(new ResultRunnable<Boolean>() {

			@Override
			public Boolean run() {
				return shell.isVisible();
			}
		});
	}
	
	/**
	 * Checks if shell is disposed
	 * @param shell given SWT shell instance
	 * @return true if shell is disposed, false otherwise
	 */
	public boolean isDisposed(final Shell shell) {
		return Display.syncExec(new ResultRunnable<Boolean>() {

			@Override
			public Boolean run() {
				return shell.isDisposed();
			}
		});
	}
}
