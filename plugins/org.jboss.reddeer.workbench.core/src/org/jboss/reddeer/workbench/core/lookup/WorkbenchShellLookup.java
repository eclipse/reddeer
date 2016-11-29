package org.jboss.reddeer.workbench.core.lookup;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.jboss.reddeer.common.util.Display;
import org.jboss.reddeer.common.util.ResultRunnable;

public class WorkbenchShellLookup {
	
	private static WorkbenchShellLookup instance;
	
	private WorkbenchShellLookup(){
		
	}
	
	/**
	 * Gets instance of ShellLookup.
	 * 
	 * @return ShellLookup instance
	 */
	public static WorkbenchShellLookup getInstance() {
		if (instance == null)
			instance = new WorkbenchShellLookup();
		return instance;
	}
	
	/**
	 * Gets active workbench shell.
	 * 
	 * @return active workbench shell
	 */
	public Shell getWorkbenchShell() {
		return Display.syncExec(new ResultRunnable<Shell>() {
			@Override
			public Shell run() {
				return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
			}
		});
	}

}
