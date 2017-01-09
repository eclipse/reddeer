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
package org.jboss.reddeer.eclipse.core.resources;

import org.jboss.reddeer.swt.api.TreeItem;

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
