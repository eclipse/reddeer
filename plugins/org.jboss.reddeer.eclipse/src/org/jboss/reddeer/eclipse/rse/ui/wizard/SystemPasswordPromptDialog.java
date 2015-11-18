package org.jboss.reddeer.eclipse.rse.ui.wizard;

import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;

/**
 * This class represents Remote System password prompt dialog
 * @author Pavol Srna
 *
 */
public class SystemPasswordPromptDialog {

	public static final String TITLE = "Enter Password";
	
	private Shell shell;
	
	/**
	 * Constructs a dialog with {@value #TITLE}.
	 */
	public SystemPasswordPromptDialog() {
		shell = new DefaultShell(TITLE);
	}
	
	/**
	 * Get Shell of the Remote System password prompt dialog.
	 *
	 * @return Shell
	 */
	public Shell getShell(){
		return this.shell;
	}
	
}
