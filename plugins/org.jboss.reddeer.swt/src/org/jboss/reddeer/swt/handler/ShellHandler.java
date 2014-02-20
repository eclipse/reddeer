package org.jboss.reddeer.swt.handler;

import org.eclipse.swt.widgets.Shell;
import org.jboss.reddeer.swt.util.Display;

/**
 * Shell handler handles operations for SWT Shell instances
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

}
