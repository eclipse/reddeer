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
package org.eclipse.reddeer.swt.condition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.reddeer.common.condition.AbstractWaitCondition;
import org.eclipse.reddeer.core.lookup.ShellLookup;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.swt.widgets.Shell;

/**
 * Condition pass when new shell is opened or the specified one is no longer available
 * @author rawagner
 *
 */
public class NewShellIsOpenedOrIsNotAvailable extends AbstractWaitCondition {

	private List<Shell> previousShells;
	private Shell newShell;
	private Shell oldShell;

	/**
	 * 
	 * @param oldShell existing shell which can become unavailable
	 * @param previousShells all open shells
	 */
	public NewShellIsOpenedOrIsNotAvailable(Shell oldShell, final Shell[] previousShells) {
		this.previousShells = new ArrayList<Shell>(Arrays.asList(previousShells));
		this.oldShell = oldShell;
	}

	@Override
	public boolean test() {
		List<Shell> currentShells = new ArrayList<Shell>(Arrays.asList(ShellLookup.getInstance().getShells()));
		if (!currentShells.contains(oldShell)) {
			return true;
		}
		currentShells.removeAll(previousShells);
		if (currentShells.size() == 1) {
			newShell = currentShells.get(0);
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public org.eclipse.reddeer.swt.api.Shell getResult() {
		if (newShell != null) {
			return new DefaultShell(newShell);
		}
		return null;
	}

}
