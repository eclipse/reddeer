package org.jboss.reddeer.swt.lookup.impl;

import org.eclipse.swt.widgets.Shell;
import org.jboss.reddeer.swt.condition.ShellIsActive;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;
import org.jboss.reddeer.swt.wait.TimePeriod;
import org.jboss.reddeer.swt.wait.WaitUntil;

/**
 * Shell Lookup, this contains routines for ToolBar implementation that have are widely used 
 * and also requires to be executed in UI Thread
 * @author Jiri Peterka
 * 
 */
public class ShellLookup {
	/**
	 * Returns active shell
	 * Waits for shell to become active in case there is no active shell at the moment
	 * If there is no active shell even after waiting has finished then shell with focus is returned 
	 * @return
	 */
	public Shell getActiveShell() {
		new WaitUntil(new ShellIsActive(), TimePeriod.NORMAL, false);
		Shell activeShell = getCurrentActiveShell();
		// try to find shell with focus
		if (activeShell == null) {
			activeShell = Display.syncExec(new ResultRunnable<Shell>() {
				@Override
				public Shell run() {
					Shell[] ss = Display.getDisplay().getShells();
					for (Shell shell : ss) {
						if (shell.isFocusControl()) {
							return shell;
						}
					}
					return null;
				}
			});
		}

		return activeShell;
	}
	/**
	 * Returns current Active Shell without waiting for shell to become active
	 * Can return null
	 * @return
	 */
	public Shell getCurrentActiveShell () {
		return Display.syncExec(new ResultRunnable<Shell>() {
			@Override
			public Shell run() {
				return Display.getDisplay().getActiveShell();
			}
		});
	}
	
	public Shell[] getShells() {
		
		return Display.syncExec(new ResultRunnable<Shell[]>() {
			
			@Override
			public Shell[] run() {
				return Display.getDisplay().getShells();
			}
			
		});
	}
	
}


