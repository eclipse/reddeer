package org.jboss.reddeer.swt.impl.tree;

import org.apache.log4j.Logger;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.jboss.reddeer.swt.api.Tree;

public abstract class AbstractTree implements Tree {
	protected final Logger logger = Logger.getLogger(this.getClass());
	SWTBotTree tree;
	
	@Override
	public void select(String... items) {
		if (items.length == 0) return;
		
		tree.expandNode(items);

		SWTBotTreeItem item = tree.getTreeItem(items[0]);
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
}
