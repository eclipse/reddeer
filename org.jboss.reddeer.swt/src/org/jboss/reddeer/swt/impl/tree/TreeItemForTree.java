package org.jboss.reddeer.swt.impl.tree;

import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.jboss.reddeer.swt.api.TreeItem;

/**
 * 
 * @author rhopp
 *
 */

public class TreeItemForTree extends AbstractTreeItem implements TreeItem{

	/**
	 * Returns first Tree item of given AbstractTree
	 * 
	 * @param tree
	 */
	
	public TreeItemForTree(AbstractTree tree){
		this.tree = tree.getSWTBotTree();
	}
	
	/**
	 * Protected constructor
	 * 
	 * @param item
	 * @param tree
	 */
	
	protected TreeItemForTree(SWTBotTreeItem item, SWTBotTree tree){
		this.tree = tree;
		this.item = item;
	}
}
