package org.jboss.reddeer.swt.impl.shell;

import org.jboss.reddeer.junit.logging.Logger;
import org.eclipse.ui.PlatformUI;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;

/**
 * WorkbenchShell is Shell implementation for WorkbenchShell
 * 
 * @author Jiri Peterka
 * 
 */
public class WorkbenchShell extends AbstractShell {

	private Logger log = Logger.getLogger(WorkbenchShell.class);

	/**
	 * Default Constructor for a WorkbenchShell
	 */
	public WorkbenchShell() {

		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				swtShell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
				swtShell.setFocus();
			}
		});

		log.info("Workbench shell has title '" + getText() + "'");
	}

	/**
	 * Maximize window
	 */
	public void maximize() {
		Display.syncExec(new Runnable() {

			public void run() {
				swtShell.setMaximized(true);
			}
		});
	}

	/**
	 * Restore window
	 */
	public void restore() {
		Display.syncExec(new Runnable() {

			public void run() {
				swtShell.setMaximized(false);
			}
		});
	}

	/**
	 * Return true if window is maximized, false otherwise
	 * 
	 * @return true if window is maximized
	 */
	public boolean isMaximized() {
		return Display.syncExec(new ResultRunnable<Boolean>() {

			public Boolean run() {
				return swtShell.getMaximized();
			}
		});
	}
}
