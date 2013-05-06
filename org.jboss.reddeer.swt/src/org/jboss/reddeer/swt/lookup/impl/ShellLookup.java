package org.jboss.reddeer.swt.lookup.impl;

import org.eclipse.swt.widgets.Shell;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;

/**
 * Shell Lookup, this contains routines for ToolBar implementation that have are widely used 
 * and also requires to be executed in UI Thread
 * @author Jiri Peterka
 * 
 */
public class ShellLookup {

	public Shell getActiveShell() {

		final Shell[] s = new Shell[1];
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				// find active shell
				s[0] = Display.getDisplay().getActiveShell();
				if (s[0] != null)
					return;
				// at least try to find shell with focus 
				Shell[] ss = Display.getDisplay().getShells();
				for (Shell shell : ss) {
					if (shell.isFocusControl()) {
						s[0] = shell;
						return;
					}
				}
				s[0] = null;
				return;
			}
		});
		return s[0];
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


