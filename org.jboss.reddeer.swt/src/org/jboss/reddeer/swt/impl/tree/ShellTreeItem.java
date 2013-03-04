package org.jboss.reddeer.swt.impl.tree;

import java.util.List;

import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.lookup.impl.ShellLookup;

/**
 * ShellTreeItem simulates tree item found in shell environment
 * @author jjankovi
 *
 */
public class ShellTreeItem extends AbstractTreeItem implements TreeItem {
	
	/**
	 * @deprecated As of release 0.4, replaced by 
	 * {@link org.jboss.reddeer.swt.impl.tree.DefaultTreeItem}
	 */
	@Deprecated
	public ShellTreeItem() {
		this(0);
	}
	
	/**
	 * @deprecated As of release 0.4, replaced by 
	 * {@link org.jboss.reddeer.swt.impl.tree.DefaultTreeItem}
	 */
	@Deprecated
	public ShellTreeItem(String... treeItemPath) {
		this(0, treeItemPath);
	}
	
	/**
	 * @deprecated As of release 0.4, replaced by 
	 * {@link org.jboss.reddeer.swt.impl.tree.DefaultTreeItem}
	 */
	@Deprecated
	public ShellTreeItem(int treeIndex, String... treeItemPath) {
		this(treeIndex, 0, treeItemPath);
	}
	
	/**
	 * @deprecated As of release 0.4, replaced by 
	 * {@link org.jboss.reddeer.swt.impl.tree.DefaultTreeItem}
	 */
	@Deprecated
	public ShellTreeItem(int treeItemIndex) {
		this(0, treeItemIndex);
	}
	
	/**
	 * @deprecated As of release 0.4, replaced by 
	 * {@link org.jboss.reddeer.swt.impl.tree.DefaultTreeItem}
	 */
	@Deprecated
	public ShellTreeItem(int treeIndex, int treeItemIndex) {
		this(treeIndex, treeItemIndex, (String[]) null);
	}
	
	/**
	 * @deprecated As of release 0.4, replaced by 
	 * {@link org.jboss.reddeer.swt.impl.tree.DefaultTreeItem}
	 */
	@Deprecated
	public ShellTreeItem(int treeIndex, int treeItemIndex, String... treeItemPath) {
		super(new ShellLookup().getActiveShell(), treeIndex, treeItemIndex, treeItemPath);
	}
	
	@Override
	public List<TreeItem> getItems() {
		return getItems(true);
	}

	@Override
	public TreeItem getItem(String text) {
		return getItem(text, true);
	}

}
