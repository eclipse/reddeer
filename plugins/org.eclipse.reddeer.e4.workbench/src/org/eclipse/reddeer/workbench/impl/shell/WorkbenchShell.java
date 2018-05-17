/*******************************************************************************
 * Copyright (c) 2017, 2018 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.workbench.impl.shell;

import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.swt.impl.shell.AbstractShell;
import org.eclipse.reddeer.workbench.core.lookup.WorkbenchShellLookup;

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
