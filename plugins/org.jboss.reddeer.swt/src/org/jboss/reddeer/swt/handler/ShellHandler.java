package org.jboss.reddeer.swt.handler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.widgets.Shell;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.impl.shell.internal.BasicShell;
import org.jboss.reddeer.swt.lookup.ShellLookup;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;
import org.jboss.reddeer.swt.wait.TimePeriod;

/**
 * Contains methods that handle UI operations on {@link Shell} widgets. 
 * @author Jiri Peterka
 *
 */
public class ShellHandler {

	private static ShellHandler instance;
	private static Shell workbenchShell = ShellLookup.getInstance().getWorkbenchShell();
	
	private final Logger log = Logger.getLogger(ShellHandler.class);

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
	/**
	 * Closes all opened shells except workbench shell
	 * @param beforeShellIsClosed - callback method beforeShellIsClosed.runBeforeShellIsClosed
	 * is called before shell is closed
	 */
	public void closeAllNonWorbenchShells(IBeforeShellIsClosed beforeShellIsClosed){
		log.info("Closing all shells...");
		List<Shell> shells = getNonWorbenchShellsToClose();
		long timeOut = System.currentTimeMillis() + (TimePeriod.VERY_LONG.getSeconds() * 1000);
		do {
			// first try to close active shell and reload shells list
			Shell s = getFilteredActiveShell(shells);
			// if no active shell present close first one
			if (s == null && shells.size() > 0){
				s = shells.get(0);
			}
			if (s != null && !s.isDisposed()) {
				if (beforeShellIsClosed != null){
					beforeShellIsClosed.runBeforeShellIsClosed(s);
				}
				new BasicShell(s).close();
			}
			// reload current shells list
			shells = getNonWorbenchShellsToClose();
		} while ((shells.size() > 0) && (System.currentTimeMillis() < timeOut));
	}
	
	/**
	 * Closes all opened shells except workbench shell
	 */
	public void closeAllNonWorbenchShells(){
		this.closeAllNonWorbenchShells(null);
	}
	
	/**
	 * Return all shells which should be closed
	 * 
	 * @return
	 */
	private List<Shell> getNonWorbenchShellsToClose() {
		List<Shell> shellsToClose = new ArrayList<Shell>();
		Shell[] currentShells = ShellLookup.getInstance().getShells();
		for (int i = 0; i < currentShells.length; i++) {
			if (currentShells[i] != ShellHandler.workbenchShell && !currentShells[i].isDisposed()) {
				shellsToClose.add(currentShells[i]);
			}
		}
		return shellsToClose;
	}
	/**
	 * Returns active shell if contained within shells list
	 * @param shells - list of shells checked for active shell
	 * @return
	 */
	private Shell getFilteredActiveShell(List<Shell> shells){
		Shell result = null;
		
		if (shells != null){
			Shell activeShell = ShellLookup.getInstance().getActiveShell();
			if (activeShell != null){
				Iterator<Shell> itShell = shells.iterator();
				while (itShell.hasNext() && result == null){
					Shell shell = itShell.next();
					if (WidgetHandler.getInstance().getText(shell).equals(
							WidgetHandler.getInstance().getText(activeShell))){
						result = shell;
					}
				}
			}
		}
		
		return result;
		
	}

}
