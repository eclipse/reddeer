package org.jboss.reddeer.swt.impl.tree;

import java.util.List;

import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.lookup.impl.WidgetLookup;

/**
 * ViewTreeItem simulates tree item found in view environment
 * @author jjankovi
 *
 */
public class ViewTreeItem extends AbstractTreeItem implements TreeItem {

	public ViewTreeItem() {
		this(0);
	}
	
	public ViewTreeItem(String... treeItemPath) {
		this(0, treeItemPath);
	}
	
	public ViewTreeItem(int treeIndex, String... treeItemPath) {
		this(treeIndex, 0, treeItemPath);
	}
	
	public ViewTreeItem(int treeItemIndex) {
		this(0, treeItemIndex);
	}
	
	public ViewTreeItem(int treeIndex, int treeItemIndex) {
		this(treeIndex, treeItemIndex, (String[]) null);
	}
	
	public ViewTreeItem(int treeIndex, int treeItemIndex, String... treeItemPath) {
		super(WidgetLookup.getInstance().getFocusControl(), treeIndex, treeItemIndex, treeItemPath);
	}
	
	@Override
	public List<TreeItem> getItems() {
		return getItems(false);
	}
	
	@Override
	public TreeItem getItem(String text) {
		return getItem(text, false);
	}
}
