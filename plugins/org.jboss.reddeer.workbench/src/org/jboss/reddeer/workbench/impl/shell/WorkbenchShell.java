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
}
