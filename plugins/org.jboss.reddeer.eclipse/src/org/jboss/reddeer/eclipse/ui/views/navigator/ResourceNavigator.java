package org.jboss.reddeer.eclipse.ui.views.navigator;

import org.jboss.reddeer.eclipse.jdt.ui.AbstractExplorer;

/**
 * Navigator RedDeer implementation
 * "The Navigator view provides a hierarchical view of the resources
 * in the Workbench including hidden files."
 * 
 * @author Radoslav Rabara
 *
 */
public class ResourceNavigator extends AbstractExplorer {
	
	/**
	 * Construct the explorer with "Navigator". 
	 */
	public ResourceNavigator() {
		super("Navigator");
	}
}
