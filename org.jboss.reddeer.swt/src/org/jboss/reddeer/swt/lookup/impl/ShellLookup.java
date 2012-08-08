package org.jboss.reddeer.swt.lookup.impl;

import org.eclipse.swt.widgets.Shell;
import org.jboss.reddeer.swt.util.Display;

/**
 * 
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
}
