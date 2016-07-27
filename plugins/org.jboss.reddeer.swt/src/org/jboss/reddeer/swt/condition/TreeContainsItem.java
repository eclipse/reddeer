package org.jboss.reddeer.swt.condition;

import org.jboss.reddeer.common.condition.AbstractWaitCondition;
import org.jboss.reddeer.swt.api.Tree;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;

public class TreeContainsItem extends AbstractWaitCondition {

	private Tree tree;
	private String[] itemPath;

	/**
	 * Constructs TreeContainsItem wait condition. Condition is met when the
	 * specified tree contains the tree item with specified text.
	 * 
	 * @param tree tree where to look for an item
	 * @param item item to find in the specified tree
	 */
	public TreeContainsItem(Tree tree, String... itemPath) {
		this.tree = tree;
		this.itemPath = itemPath;
	}

	@Override
	public boolean test() {
		try{
			new DefaultTreeItem(tree, itemPath);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public String description() {
		return "tree contains item '" + itemPath;
	}

}
