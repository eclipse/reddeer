/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.workbench.impl.shell;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.util.Display;
import org.jboss.reddeer.common.util.ResultRunnable;
import org.jboss.reddeer.swt.impl.shell.AbstractShell;
import org.jboss.reddeer.workbench.core.lookup.WorkbenchShellLookup;

/**
 * WorkbenchShell is Shell implementation for WorkbenchShell
 * 
 * @author Jiri Peterka
 * 
 */
public class WorkbenchShell extends AbstractShell {

	private static final Logger log = Logger.getLogger(WorkbenchShell.class);

	/**
	 * Default Constructor for a WorkbenchShell.
	 */
	public WorkbenchShell() {
		super(WorkbenchShellLookup.getInstance().getWorkbenchShell());
		setFocus();
		log.debug("Workbench shell has title '" + getText() + "'");
	}

	/**
	 * Maximize window.
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
	 * Restore window.
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
	 * Return true if window is maximized, false otherwise.
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
