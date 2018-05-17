/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.workbench.core.lookup;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.common.util.ResultRunnable;

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
