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

import org.eclipse.reddeer.swt.api.TreeItem;

/**
 * Default implementation of a server in Servers view.
 * 
 * @author mlabuda@redhat.com
 *
 */
public class DefaultServer extends AbstractServer {

	/**
	 * Creates a instance of DefaultServer.
	 * @param treeItem treeItem of the server
	 */
	public DefaultServer(TreeItem treeItem) {
		super(treeItem);
	}
}
