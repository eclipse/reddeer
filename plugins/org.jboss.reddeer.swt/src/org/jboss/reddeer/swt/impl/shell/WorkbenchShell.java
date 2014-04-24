package org.jboss.reddeer.swt.impl.shell;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.jboss.reddeer.junit.logging.Logger;
import org.jboss.reddeer.swt.condition.JobIsRunning;
import org.jboss.reddeer.swt.condition.WaitCondition;
import org.jboss.reddeer.swt.handler.ShellHandler;
import org.jboss.reddeer.swt.handler.WidgetHandler;
import org.jboss.reddeer.swt.lookup.ShellLookup;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;
import org.jboss.reddeer.swt.wait.TimePeriod;
import org.jboss.reddeer.swt.wait.WaitUntil;
import org.jboss.reddeer.swt.wait.WaitWhile;

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
		swtShell = getWorkbenchShell();		
		WidgetHandler.getInstance().setFocus(swtShell);
		log.debug("Workbench shell has title '" + getText() + "'");
	}

	private Shell getWorkbenchShell() {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				swtShell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
			}
		});
		return swtShell;
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

	/**
	 * Close all open shells except current workbench shell
	 */
	public void closeAllShells() {
		log.info("Closing all shells...");
		Shell[] shells = ShellLookup.getInstance().getShells();
		for (Shell s : shells) {
			Shell workbenchShell = getWorkbenchShell();
			if (s != workbenchShell) ShellHandler.getInstance().closeShell(s);
		}
		new WaitUntil(new NoShellToClose(), TimePeriod.VERY_LONG);
	}

	/**
	 * Return all shells which should be closed
	 * 
	 * @return
	 */
	protected List<Shell> getShellsToClose() {
		List<Shell> shells = new ArrayList<Shell>();
		Shell[] shell = ShellLookup.getInstance().getShells();
		for (int i = 0; i < shell.length; i++) {
			if (shell[i] != swtShell) {
				shells.add(shell[i]);
			}
		}
		return shells;
	}

	/**
	 * Override this method if you want to add some functionality before closing
	 * a given shell from method closeAllShells()
	 * 
	 * @param shells
	 */
	protected void beforeShellIsClosed(Shell shells) {

	}
	
	private class NoShellToClose implements WaitCondition {
		
		private List<Shell> shells;
		
		public NoShellToClose() {
			shells = new ArrayList<Shell>();
		}

		@Override
		public boolean test() {
			new WaitWhile(new JobIsRunning(), TimePeriod.LONG);
			shells = getShellsToClose();
			if (shells.isEmpty() || shellsAreDisposed(shells)) {
				return true;
			}
			Shell shell = shells.get(shells.size() - 1);
			beforeShellIsClosed(shell);
			if (!shell.isDisposed()){
				new DefaultShell(WidgetHandler.getInstance().getText(shell)).close();
			}
			return false;
		}

		@Override
		public String description() {
			List<String> shellTitles = new ArrayList<String>();
			for (Shell shell: shells) {
				if (!shell.isDisposed()){
					shellTitles.add(WidgetHandler.getInstance().getText(shell));
				}
			}
			return "The following shells are still open " + shellTitles;
		}
		
		private boolean shellsAreDisposed(List<Shell> shells){
			boolean nonDisposedShellExists = false;
			
			if (shells != null){
				Iterator<Shell> itShells = shells.iterator();
				while (itShells.hasNext() && (!nonDisposedShellExists)){
					nonDisposedShellExists = !itShells.next().isDisposed();
				}
			}
			
			return !nonDisposedShellExists;
		}
		
	}
}
