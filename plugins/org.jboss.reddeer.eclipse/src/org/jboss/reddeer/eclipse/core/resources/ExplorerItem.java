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
	 * Creates {@link ExplorerItem}
	 * 
	 * @param treeItem
	 */
	public ExplorerItem(TreeItem treeItem) {
		super(treeItem);
	}
	
	@Override
	public void select() {
		activateWrappingView();
		treeItem.select();
	}
}
