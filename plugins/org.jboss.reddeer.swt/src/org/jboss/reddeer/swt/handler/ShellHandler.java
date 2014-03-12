package org.jboss.reddeer.swt.handler;

import org.eclipse.swt.widgets.Shell;
import org.jboss.reddeer.swt.util.Display;

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

	public void setFocus(final Shell shell) {
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				shell.forceActive();
				shell.forceFocus();
			}
		});
	}
}
