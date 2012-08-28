package org.jboss.reddeer.swt.impl.tree;

import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.jboss.reddeer.swt.api.TreeItem;

public class TreeItemForTree extends AbstractTreeItem implements TreeItem{

	
	
	protected TreeItemForTree(SWTBotTreeItem item, SWTBotTree tree){
		this.tree = tree;
		this.item = item;
	}
}
