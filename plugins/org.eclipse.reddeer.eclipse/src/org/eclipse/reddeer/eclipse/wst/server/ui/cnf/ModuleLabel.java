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
package org.eclipse.reddeer.eclipse.wst.server.ui.cnf;

import org.eclipse.reddeer.eclipse.wst.server.ui.cnf.ServersViewEnums.ServerPublishState;
import org.eclipse.reddeer.eclipse.wst.server.ui.cnf.ServersViewEnums.ServerState;
import org.eclipse.reddeer.swt.api.TreeItem;


/**
 * Parses and holds information displayed in the module's label on 
 * {@link ServersView2}
 * 
 * @author Lucia Jelinkova
 *
 */
public class ModuleLabel extends AbstractLabel{

	/**
	 * Instantiates a new module label.
	 *
	 * @param item the item
	 */
	public ModuleLabel(TreeItem item) {
		parse(item);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.eclipse.wst.server.ui.view.AbstractLabel#parseSingleStateDecoration(java.lang.String)
	 */
	@Override
	protected void parseSingleStateDecoration(String stateString) {
		for (ServerPublishState publishStatus : ServerPublishState.values()) {
			if (publishStatus.getText().equals(stateString)) {
				status = publishStatus;
				return;
			}
		}
		for (ServerState serverState : ServerState.values()) {
			if (serverState.getText().equals(stateString)) {
				state = serverState;
				return;
			}
		}
		throw new IllegalArgumentException("There is no enumeration with text " 
				+ stateString + " in ServerPublishState or ServerState");
	}
}
