/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.workbench.impl.shell;

import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.swt.impl.shell.AbstractShell;
import org.eclipse.reddeer.workbench.core.lookup.WorkbenchShellLookup;

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
		log.debug("Workbench shell has title '" + getText() + "'");
	}
}
