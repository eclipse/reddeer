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
package org.jboss.reddeer.eclipse.wst.server.ui.view;

import org.jboss.reddeer.eclipse.wst.server.ui.view.ServersViewEnums.ServerState;
import org.jboss.reddeer.swt.api.TreeItem;

/**
 * Parses and holds information displayed in the server's label on 
 * {@link ServersView}
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
	 * @see org.jboss.reddeer.eclipse.wst.server.ui.view.AbstractLabel#parseSingleStateDecoration(java.lang.String)
	 */
	@Override
	protected void parseSingleStateDecoration(String stateString) {
			state = ServerState.getByText(stateString);
	}
}
