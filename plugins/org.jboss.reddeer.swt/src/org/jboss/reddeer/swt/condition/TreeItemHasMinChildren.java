package org.jboss.reddeer.swt.condition;

import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.condition.WaitCondition;


/**
 * Condition is fulfilled when tree item has at least minItemsCount children items
 * @author Vlado Pakan
 *
 */
public class TreeItemHasMinChildren implements WaitCondition {
	private final TreeItem treeItem;
	private final int minItemsCount;

	public TreeItemHasMinChildren(TreeItem treeItem , int minItemsCount) {
		this.treeItem = treeItem;
		this.minItemsCount = minItemsCount;
	}

	public boolean test() {
		return treeItem.getItems().size() >= this.minItemsCount;
	}

	@Override
	public String description() {
		return "TreeItem " + treeItem.getText() + " has " 
			+ this.minItemsCount + " children or more";
	}
}