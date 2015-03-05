package org.jboss.reddeer.eclipse.core.resources;

import org.jboss.reddeer.swt.api.TreeItem;

/**
 * Represents Java project inside Project explorer, Package explorer or Resource Navigator.
 * 
 * @author mlabuda@redhat.com
 *
 */
public class JavaProject extends Project {

	/**
	 * Creates object representing java project
	 * @param item
	 */
	public JavaProject(TreeItem item) {
		super(item);
	}
	
	@Override
	public String[] getNatureIds() {
		return new String[] {"org.eclipse.jdt.core.javanature"};
	}
}
