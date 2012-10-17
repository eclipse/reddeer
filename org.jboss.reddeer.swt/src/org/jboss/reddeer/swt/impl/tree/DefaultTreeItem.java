package org.jboss.reddeer.swt.impl.tree;

import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.jboss.reddeer.swt.api.Tree;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.util.Bot;

public class DefaultTreeItem extends AbstractTreeItem implements TreeItem {

	public DefaultTreeItem(String... items) {
		SWTBotTree tree = Bot.get().tree();

		if (items.length == 0) return;
		
		tree.expandNode(items);

		item = tree.getTreeItem(items[0]);
		for (int i = 1; i < items.length; i++){
			item = item.getNode(items[i]);
		}
		if (logger.isDebugEnabled()){
	          StringBuffer sbPath = new StringBuffer("");
	          for (String itemText : items){
	            if (sbPath.length() > 0 ){
	              sbPath.append(" > ");
	            }
	            sbPath.append(itemText);
	          }
	      logger.debug("Select treeitem: " + sbPath.toString());
	    }
		item.select();
	}
	
	public DefaultTreeItem(Tree tree, String... path) {
		this(path);
	}
	
	public DefaultTreeItem(int treeIndex, String... items) {
		SWTBotTree tree = Bot.get().tree(treeIndex);

		if (items.length == 0) return;
		
		tree.expandNode(items);

		item = tree.getTreeItem(items[0]);
		for (int i = 1; i < items.length; i++){
			item = item.getNode(items[i]);
		}
		if (logger.isDebugEnabled()){
	          StringBuffer sbPath = new StringBuffer("");
	          for (String itemText : items){
	            if (sbPath.length() > 0 ){
	              sbPath.append(" > ");
	            }
	            sbPath.append(itemText);
	          }
	      logger.debug("Select treeitem: " + sbPath.toString());
	    }
		item.select();
	}
	
	protected DefaultTreeItem(org.eclipse.swt.widgets.TreeItem treeItem){
		this.item = new SWTBotTreeItem(treeItem);
	}
}
