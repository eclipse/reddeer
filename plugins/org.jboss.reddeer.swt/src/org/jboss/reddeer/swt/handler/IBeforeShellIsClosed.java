package org.jboss.reddeer.swt.handler;

import org.eclipse.swt.widgets.Shell;
/**
 * Interface used as hook for method closeAllNonWorbenchShells() in {@link ShellHandler}
 * @author Vlado Pakan
 *
 */
public interface IBeforeShellIsClosed {
	/**
	 * Method is called right before closing shell within
	 * method closeAllNonWorbenchShells() in {@link ShellHandler}
	 * @param shell
	 */
	public void runBeforeShellIsClosed(Shell shell);
}
