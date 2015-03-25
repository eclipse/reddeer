package org.jboss.reddeer.swt.handler;

import org.eclipse.swt.widgets.Shell;

/**
 * Interface used as a hook for method {@link ShellHandler#closeAllNonWorbenchShells()}.
 * 
 * @author Vlado Pakan
 * @deprecated since 0.8, use {@link #org.jboss.reddeer.core.handler.IBeforeShellIsClosed}
 */
@Deprecated
public interface IBeforeShellIsClosed {
	
	/**
	 * Method is called right before closing shell within method
	 * {@link ShellHandler#closeAllNonWorbenchShells(IBeforeShellIsClosed)}.
	 * 
	 * @param shell shell to close
	 */
	void runBeforeShellIsClosed(Shell shell);
}
