package org.jboss.reddeer.swt.impl.shell;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.lookup.ShellLookup;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;

/**
 * WorkbenchShell is Shell implementation for WorkbenchShell
 * 
 * @author Jiri Peterka
 * 
 */
public class WorkbenchShell extends AbstractShell {

	private static final Logger log = Logger.getLogger(WorkbenchShell.class);

	/**
	 * Default Constructor for a WorkbenchShell
	 */
	public WorkbenchShell() {
		super(ShellLookup.getInstance().getWorkbenchShell());
		setFocus();
		log.info("Workbench shell has title '" + getText() + "'");
	}



	/**
	 * Maximize window
	 */
	public void maximize() {
		log.info("Maximize workbench shell");
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
		log.info("Restore workbench shell");
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
