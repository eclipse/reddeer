package org.jboss.reddeer.swt.impl.tree;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.jboss.reddeer.swt.api.TreeItem;

public abstract class AbstractTreeItem implements TreeItem {
	protected final Logger logger = Logger.getLogger(this.getClass());
	protected SWTBotTree tree;
	protected SWTBotTreeItem item;
	
	@Override
	public void select() {
		item.select();
	}
	
	@Override
	public String getText() {
		String text = item.getText();
		return text;
	}
	
	@Override
	public String getToolTipText() {
		String toolTipText = item.getToolTipText();
		return toolTipText;
	}
	
	public void expand(){
		logger.debug("Expanding Tree Item");
		item.expand();
	}
	
	/**
	 * Returns all (direct) subnodes of this TreeItem
	 * 
	 * @return
	 */
	
	public List<AbstractTreeItem> getAllChildren(){
		expand();
		List<AbstractTreeItem> list = new LinkedList<AbstractTreeItem>();
		for (SWTBotTreeItem treeItem : item.getItems()) {
			list.add(new TreeItemForTree(treeItem, tree));
		}
		return list;
	}
}
