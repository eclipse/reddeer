package org.jboss.reddeer.swt.impl.tree;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.jboss.reddeer.swt.api.Tree;
import org.jboss.reddeer.swt.api.TreeItem;

public abstract class AbstractTree implements Tree {

	protected final Logger logger = Logger.getLogger(this.getClass());
	
	protected SWTBotTree tree;

	public List<TreeItem> getItems(){
		List<TreeItem> list = new LinkedList<TreeItem>();
		for (SWTBotTreeItem treeItem : tree.getAllItems()) {
			TreeItem item = new DefaultTreeItem(treeItem.widget);
			list.add(item);
		}
		return list;	
	}
	
	public List<TreeItem> getAllItems(){
		List<TreeItem> list = new LinkedList<TreeItem>();
		list.addAll(getAllItemsRecursive(getItems()));
		return list;
	}

	private List<TreeItem> getAllItemsRecursive(List<TreeItem> parentItems){
		List<TreeItem> list = new LinkedList<TreeItem>();
		
		for (TreeItem item : parentItems) {
			list.add(item);
			list.addAll(getAllItemsRecursive(item.getItems()));
		}
		return list;
	}
}
