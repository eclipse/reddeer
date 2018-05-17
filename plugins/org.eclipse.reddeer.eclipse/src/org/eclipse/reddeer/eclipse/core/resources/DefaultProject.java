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
package org.eclipse.reddeer.eclipse.core.resources;

import org.eclipse.reddeer.swt.api.TreeItem;

/**
 * Represents a default project inside Project explorer, Package explorer or
 * Resource Navigator.
 * 
 * @author Vlado Pakan, mlabuda@redhat.com
 * 
 */
public class DefaultProject extends AbstractProject {

	/**
	 * Creates a project represented by specified {@link TreeItem}.
	 * 
	 * @param treeItem
	 *            encapsulated tree item
	 */
	public DefaultProject(TreeItem treeItem) {
		super(treeItem);
	}
	
	@Override
	public ProjectItem getProjectItem(String... path) {
		return new DefaultProjectItem(getResource(path).getTreeItem());
	}
	
	@Override
	public String[] getNatureIds() {
		return null;
	}
}
