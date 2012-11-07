package org.jboss.reddeer.swt.impl.tree;

import java.util.List;

import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.lookup.impl.ShellLookup;

public class ShellTreeItem extends AbstractTreeItem implements TreeItem {
	
	public ShellTreeItem() {
		this(0);
	}
	
	public ShellTreeItem(String... treeItemPath) {
		this(0, treeItemPath);
	}
	
	public ShellTreeItem(int treeIndex, String... treeItemPath) {
		this(treeIndex, 0, treeItemPath);
	}
	
	public ShellTreeItem(int treeItemIndex) {
		this(0, treeItemIndex);
	}
	
	public ShellTreeItem(int treeIndex, int treeItemIndex) {
		this(treeIndex, treeItemIndex, (String[]) null);
	}
	
	public ShellTreeItem(int treeIndex, int treeItemIndex, String... treeItemPath) {
		super(new ShellLookup().getActiveShell(), treeIndex, treeItemIndex, treeItemPath);
	}
	
	public List<TreeItem> getItems() {
		return getItems(false);
	}

	@Override
	public TreeItem getItem(String text) {
		return getItem(text, true);
	}

}
