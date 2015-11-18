package org.jboss.reddeer.eclipse.core.resources;

import org.jboss.reddeer.swt.api.TreeItem;

/**
 * Represents a general project inside Project explorer, Package explorer or Resource
 * Navigator.
 * 
 * @author Vlado Pakan, mlabuda@redhat.com
 * 
 */
public class Project extends AbstractProject {

	/**
	 * Creates a project represented by specified {@link TreeItem}.
	 * @param treeItem
	 *            encapsulated tree item
	 */
	public Project(TreeItem treeItem) {
		super(treeItem);
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.eclipse.core.resources.AbstractProject#getNatureIds()
	 */
	@Override
	public String[] getNatureIds() {
		return null;
	}
}
