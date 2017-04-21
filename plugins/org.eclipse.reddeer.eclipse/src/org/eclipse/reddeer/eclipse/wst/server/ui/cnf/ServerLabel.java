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
package org.eclipse.reddeer.eclipse.wst.server.ui.cnf;

import org.eclipse.reddeer.eclipse.wst.server.ui.cnf.ServersViewEnums.ServerState;
import org.eclipse.reddeer.swt.api.TreeItem;

/**
 * Parses and holds information displayed in the server's label on 
 * {@link ServersView2}
 * 
 * @author Lucia Jelinkova
 *
 */
public class ServerLabel extends AbstractLabel {

	/**
	 * Instantiates a new server label.
	 *
	 * @param item the item
	 */
	public ServerLabel(TreeItem item) {
		parse(item);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.eclipse.wst.server.ui.view.AbstractLabel#parseSingleStateDecoration(java.lang.String)
	 */
	@Override
	protected void parseSingleStateDecoration(String stateString) {
			state = ServerState.getByText(stateString);
	}
}
