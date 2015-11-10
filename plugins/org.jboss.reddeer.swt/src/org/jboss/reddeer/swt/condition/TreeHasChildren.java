package org.jboss.reddeer.swt.condition;


import org.jboss.reddeer.common.condition.AbstractWaitCondition;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.api.Tree;

/**
 * Condition is fulfilled when tree has children.
 *
 * @author Jaroslav Jankovic
 * @author Jiri Peterka
 *
 */
public class TreeHasChildren extends AbstractWaitCondition {
	private Logger log = Logger.getLogger(TreeHasChildren.class);
	private Tree tree;
	
	/**
	 * Construct tree has children condition. 
	 * @param tree given tree
	 */
	public TreeHasChildren(Tree tree) {
		super();
		this.tree = tree;
	}

	@Override
	public boolean test() {
		int count = tree.getItems().size();
		log.trace("Count of found tree items:" + count);
		if (count > 0) {
			return true;
		}
		return false;
	}

	@Override
	public String description() {
		return "tree has children";
	}
}
