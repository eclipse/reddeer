package org.jboss.reddeer.swt.handler;

import org.eclipse.swt.widgets.Shell;

/**
 * Interface used as a hook for method {@link ShellHandler#closeAllNonWorbenchShells()}.
 * 
 * @author Vlado Pakan
 *
 */
public interface IBeforeShellIsClosed {
	
	/**
	 * Method is called right before closing shell within method
	 * {@link ShellHandler#closeAllNonWorbenchShells(IBeforeShellIsClosed)}.
	 * 
	 * @param shell shell to close
	 */
	void runBeforeShellIsClosed(Shell shell);
}
