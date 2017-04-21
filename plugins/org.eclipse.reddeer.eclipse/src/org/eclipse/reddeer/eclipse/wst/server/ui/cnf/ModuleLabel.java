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

import org.eclipse.reddeer.eclipse.wst.server.ui.cnf.ServersViewEnums.ServerPublishState;
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
			status = ServerPublishState.getByText(stateString);
	}
}
