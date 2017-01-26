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

import org.jboss.reddeer.swt.api.TreeItem;

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
