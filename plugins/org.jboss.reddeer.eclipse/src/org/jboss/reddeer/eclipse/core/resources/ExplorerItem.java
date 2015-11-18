package org.jboss.reddeer.eclipse.core.resources;

import org.jboss.reddeer.swt.api.TreeItem;

/**
 * Represents an item in the explorer
 * 
 * @author Jan Richter
 *
 */
public class ExplorerItem extends AbstractExplorerItem{

	/**
	 * Creates {@link ExplorerItem}.
	 *
	 * @param treeItem the tree item
	 */
	public ExplorerItem(TreeItem treeItem) {
		super(treeItem);
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.eclipse.core.resources.AbstractExplorerItem#select()
	 */
	@Override
	public void select() {
		activateWrappingView();
		treeItem.select();
	}
}
