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
package org.eclipse.reddeer.eclipse.core.resources;

import org.eclipse.reddeer.swt.api.TreeItem;

/**
 * Represents a project item of a {@link Project}.
 * 
 * @author Vlado Pakan, mlabuda@redhat.com
 * 
 */
public class DefaultProjectItem extends AbstractProjectItem {

	/**
	 * Constructs project item with a specified tree item.
	 * 
	 * @param treeItem
	 *            item representing project item
	 */
	public DefaultProjectItem(TreeItem treeItem) {
		super(treeItem);
	}

	@Override
	public ProjectItem getProjectItem(String... path) {
		return new DefaultProjectItem(getResource(path).getTreeItem());
	}

	@Override
	public Project getProject() {
		return new DefaultProject(getResource(treeItem.getPath()[0]).getTreeItem());
	}
}
