package org.jboss.reddeer.swt.lookup;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.condition.WaitCondition;
import org.jboss.reddeer.swt.handler.TreeHandler;
import org.jboss.reddeer.swt.handler.TreeItemHandler;
import org.jboss.reddeer.swt.impl.tree.TreeItemNotFoundException;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;

/**
 * TreeItem lookup
 * 
 * @author Lucia Jelinkova
 *
 */
public class TreeItemLookup {

	private static TreeItemLookup instance = null;
	
	private TreeItemLookup() {
	}
	
	/**
	 * Creates and returns an instance
	 * @return TextLookup instance
	 */
	public static TreeItemLookup getInstance() {
		if (instance == null) instance = new TreeItemLookup();
		return instance;
	}
	
	/**
	 * 
	 * Walks down the tree and finds tree items with given index that has path matching specified matchers. 
	 * <br>
	 * For example, if there is tree item with path "A", "AA", "AAB" it can be matched by the following
	 * path matchers: new TreeItemRegexMatcher("A"), new TreeItemRegexMatcher("A+"), new TreeItemRegexMatcher("A+B");
	 * 
	 * @param tree
	 * @param index Index of the returned tree item
	 * @param matchers Path matchers. Every matcher matches one tree item on the path
	 * @return
	 */
	public TreeItem getTreeItem(Tree tree, int index, Matcher<TreeItem>... matchers){
		List<TreeItem> result = getTreeItems(tree, matchers);
		if (result.size() < index + 1){
			throw new TreeItemNotFoundException(result, index, matchers);
		}
		return result.get(index);
	}
	
	/**
	 * Walks down the tree and finds all tree items that have path matching specified matchers. 
	 * <br>
	 * For example, if there is tree item with path "A", "AA", "AAB" it can be matched by the following
	 * path matchers: new TreeItemRegexMatcher("A"), new TreeItemRegexMatcher("A+"), new TreeItemRegexMatcher("A+B");
	 * @param tree
	 * @param pathItemMatchers Path matchers. Every matcher matches one tree item on the path
	 * @return
	 */
	public List<TreeItem> getTreeItems(Tree tree, Matcher<TreeItem>... pathItemMatchers){
		if (pathItemMatchers.length == 0){
			throw new IllegalArgumentException("No matchers for tree item path specified");
		}
		new WaitUntil(new TreeHasChildren(tree));
		List<TreeItem> items = TreeHandler.getInstance().getSWTItems(tree);

		for(int index = 0; index < pathItemMatchers.length; index++) {
			List<TreeItem> matchingItems = getMatchingTreeItems(items, pathItemMatchers[index]);
			
			if (matchingItems.isEmpty()){
				throw new TreeItemNotFoundException(items, pathItemMatchers[index], pathItemMatchers);
			}

			if (index == pathItemMatchers.length - 1) {
				return matchingItems;
			}

			items = getChildItems(matchingItems);
		}
		
		throw new IllegalStateException("It should never get here. This is probably flaw in lookup algoritnus");
	}
		
	private List<TreeItem> getChildItems(List<TreeItem> parentItems) {
		List<TreeItem> children = new ArrayList<TreeItem>();
		for (TreeItem parentItem : parentItems){
			new WaitUntil(new TreeItemHasChildren(parentItem), TimePeriod.NORMAL, false);	
			children.addAll(TreeItemHandler.getInstance().getChildrenItems(parentItem));
		}
		return children;
	}

	private List<TreeItem> getMatchingTreeItems(List<TreeItem> items, Matcher<TreeItem> matcher){
		List<TreeItem> matchingitems = new ArrayList<TreeItem>();
		for (TreeItem item : items){
			if (matcher.matches(item)){
				matchingitems.add(item);
			}
		}
		return matchingitems;
	}
	
	/**
	 * Condition is fulfilled when tree has children 
	 * 
	 * @author jjankovi
	 *
	 */
	class TreeHasChildren implements WaitCondition {
		private Tree tree;
		public TreeHasChildren(Tree tree) {
			super();
			this.tree = tree;
		}
		
		@Override
		public boolean test() {
			return TreeHandler.getInstance().getSWTItems(tree).size() > 0;		
		}
		
		@Override
		public String description() {
			return "tree has children";
		}
	}
	
	public class TreeItemHasChildren implements WaitCondition {
		
		private final TreeItem treeItem;
		
		public TreeItemHasChildren(TreeItem treeItem) {
			this.treeItem = treeItem;
		}

		@Override
		public boolean test() {
			return TreeItemHandler.getInstance().getChildrenItems(treeItem).size() > 0;
		}

		@Override
		public String description() {
			return "treeItem " + TreeItemHandler.getInstance().getText(treeItem, 0) + " has children";
		}
	}
}
