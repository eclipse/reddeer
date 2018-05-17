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
package org.eclipse.reddeer.core.condition;

import org.eclipse.reddeer.common.condition.AbstractWaitCondition;
import org.eclipse.reddeer.core.lookup.ShellLookup;
import org.eclipse.swt.widgets.Shell;

/**
 * Condition is met when active shell exists.
 *
 * @author mlabuda@redhat.com
 * @since 0.8.0
 */

public class ActiveShellExists extends AbstractWaitCondition {

	private Shell shell;
	
	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.common.condition.WaitCondition#test()
	 */
	@Override
	public boolean test() {
		shell = ShellLookup.getInstance().getCurrentActiveShell();
		return shell != null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.common.condition.AbstractWaitCondition#description()
	 */
	@Override
	public String description() {
		return "active shell exists";
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Shell getResult() {
		return this.shell;
	}
}