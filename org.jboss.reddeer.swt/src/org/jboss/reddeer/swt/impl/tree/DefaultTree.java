package org.jboss.reddeer.swt.impl.tree;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.jboss.reddeer.swt.api.Tree;
import org.jboss.reddeer.swt.exception.WidgetNotAvailableException;
import org.jboss.reddeer.swt.util.Bot;

/**
 * First available Tree implementation
 * @author Jiri Peterka
 *
 */
public class DefaultTree extends AbstractTree implements Tree {

	public DefaultTree() {
		this(Bot.get());
	}
	
	public DefaultTree(SWTBot bot) {
		try {
			tree = bot.tree();
			tree.setFocus();
		} catch (WidgetNotFoundException e) {
			throw new WidgetNotAvailableException("No tree is available");
		}
	}
	
	public List<AbstractTreeItem> getAllItemsRecursive(){
		List<AbstractTreeItem> list = new LinkedList<AbstractTreeItem>();
		for (SWTBotTreeItem treeItem : tree.getAllItems()) {
			AbstractTreeItem item = new TreeItemForTree(treeItem, tree);
			list.addAll(getAllItemsRecursive(item));
		}
		return list;	
	}
	
	private List<AbstractTreeItem> getAllItemsRecursive(AbstractTreeItem parentItem){
		List<AbstractTreeItem> list = new LinkedList<AbstractTreeItem>();
		list.add(parentItem);
		for (AbstractTreeItem abstractTreeItem : parentItem.getAllChildren()) {
			list.addAll(getAllItemsRecursive(abstractTreeItem));
		}
		return list;
	}
}
