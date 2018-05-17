/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.core.lookup;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.hamcrest.Matcher;
import org.eclipse.reddeer.common.condition.AbstractWaitCondition;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.core.exception.CoreLayerException;
import org.eclipse.reddeer.core.handler.TreeHandler;
import org.eclipse.reddeer.core.handler.TreeItemHandler;

/**
 * Tree item lookup provides methods for looking up tree items located within trees.
 * 
 * @author Lucia Jelinkova
 *
 */
@SuppressWarnings("unchecked")
public class TreeItemLookup {

	private static TreeItemLookup instance = null;
	
	private TreeItemLookup() {
	}

	/**
	 * Gets instance of TreeItemLookup.
	 * 
	 * @return TreeItemLookup instance
	 */
	public static TreeItemLookup getInstance() {
		if (instance == null) instance = new TreeItemLookup();
		return instance;
	}
	
	/**
	 * Walks through specified tree and finds tree item with specified index matching specified matchers.
	 * <br>
	 * Example:
	 * Tree item with path "A", "AA", "AAB" can be matched by the following
	 * path matchers - new TreeItemRegexMatcher("A"), new TreeItemRegexMatcher("A+"), new TreeItemRegexMatcher("A+B").
	 * 
	 * @param tree tree to walk through
	 * @param index index of tree item
	 * @param matchers matchers to match path to tree item, each of them matches one tree item on the path
	 * @return tree item on specified index matching specified matchers in specified tree 
	 */
	public TreeItem getTreeItem(Tree tree, int index, Matcher<TreeItem>... matchers){
		List<TreeItem> result = getTreeItems(tree, matchers);
		if (result.size() < index + 1) {
			throw new CoreLayerException("Specified index (" + index + ") is bigger or equal as the number of found items (" + result.size() + ")");
		}
		return result.get(index);
	}
	
	/**
	 * Walks through specified tree and finds tree items matching specified matchers.
	 * <br>
	 * Example:
	 * Tree item with path "A", "AA", "AAB" can be matched by the following
	 * path matchers - new TreeItemRegexMatcher("A"), new TreeItemRegexMatcher("A+"), new TreeItemRegexMatcher("A+B").
	 *
	 * @param tree tree to walk through
	 * @param pathItemMatchers the path item matchers
	 * @return tree items matching specified matchers in specified tree
	 */
	public List<TreeItem> getTreeItems(Tree tree, Matcher<TreeItem>... pathItemMatchers){
		new WaitUntil(new TreeHasChildren(tree));
		List<TreeItem> items = TreeHandler.getInstance().getSWTItems(tree);
		return getTreeItems(items, pathItemMatchers);
	}

	/**
	 * Walks through specified tree item and finds tree item with specified index matching specified matchers.
	 * <br>
	 * Example:
	 * Tree item with path "A", "AA", "AAB" can be matched by the following
	 * path matchers - new TreeItemRegexMatcher("A"), new TreeItemRegexMatcher("A+"), new TreeItemRegexMatcher("A+B").
	 * 
	 * @param treeItem tree item to walk through
	 * @param index index of tree item
	 * @param matchers matchers to match path to tree item, each of them matches one tree item on the path
	 * @return tree item on specified index matching specified matchers in specified tree item
	 */
	public TreeItem getTreeItem(TreeItem treeItem, int index, Matcher<TreeItem>... matchers){
		List<TreeItem> result = getTreeItems(treeItem, matchers);
		if (result.size() < index + 1) {
			throw new CoreLayerException("Specified index (" + index + ") is bigger or equal as the number of found items (" + result.size() + ")");
		}
		return result.get(index);
	}
	
	/**
	 * Walks through specified tree item and finds tree items matching specified matchers.
	 * <br>
	 * Example:
	 * Tree item with path "A", "AA", "AAB" can be matched by the following
	 * path matchers - new TreeItemRegexMatcher("A"), new TreeItemRegexMatcher("A+"), new TreeItemRegexMatcher("A+B").
	 *
	 * @param treeItem tree item to walk through
	 * @param pathItemMatchers the path item matchers
	 * @return tree items matching specified matchers in specified tree item
	 */
	public List<TreeItem> getTreeItems(TreeItem treeItem, Matcher<TreeItem>... pathItemMatchers){
		List<TreeItem> items = TreeItemHandler.getInstance().getChildrenItems(treeItem);
		return getTreeItems(items, pathItemMatchers);
	}
	
	private List<TreeItem> getTreeItems(List<TreeItem> items, Matcher<TreeItem>... pathItemMatchers) {
		if (pathItemMatchers.length == 0){
			return items;
		}

		for(int index = 0; index < pathItemMatchers.length; index++) {
			List<TreeItem> matchingItems = getMatchingTreeItems(items, pathItemMatchers[index]);
			
			if (matchingItems.isEmpty()){
				throw new CoreLayerException("There are no items matching matcher " + pathItemMatchers[index]);
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
			new WaitUntil(new TreeItemHasChildren(parentItem), TimePeriod.DEFAULT, false);	
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
	 * Condition is met when tree has at least one child. 
	 * 
	 * @author jjankovi
	 *
	 */
	class TreeHasChildren extends AbstractWaitCondition {
		private Tree tree;
		
		/**
		 * Instantiates a new tree has children.
		 *
		 * @param tree the tree
		 */
		public TreeHasChildren(Tree tree) {
			super();
			this.tree = tree;
		}
		
		/* (non-Javadoc)
		 * @see org.eclipse.reddeer.common.condition.WaitCondition#test()
		 */
		@Override
		public boolean test() {
			return TreeHandler.getInstance().getSWTItems(tree).size() > 0;		
		}
		
		/* (non-Javadoc)
		 * @see org.eclipse.reddeer.common.condition.AbstractWaitCondition#description()
		 */
		@Override
		public String description() {
			return "tree has children";
		}
	}
	
	/**
	 * Condition is met when tree item has at least one child. 
	 * 
	 * @author jjankovi
	 *
	 */
	public class TreeItemHasChildren extends AbstractWaitCondition {
		
		private final TreeItem treeItem;
		
		/**
		 * Instantiates a new tree item has children.
		 *
		 * @param treeItem the tree item
		 */
		public TreeItemHasChildren(TreeItem treeItem) {
			this.treeItem = treeItem;
		}

		/* (non-Javadoc)
		 * @see org.eclipse.reddeer.common.condition.WaitCondition#test()
		 */
		@Override
		public boolean test() {
			return TreeItemHandler.getInstance().getChildrenItems(treeItem).size() > 0;
		}

		/* (non-Javadoc)
		 * @see org.eclipse.reddeer.common.condition.AbstractWaitCondition#description()
		 */
		@Override
		public String description() {
			return "treeItem " + TreeItemHandler.getInstance().getText(treeItem, 0) + " has children";
		}
	}
}
