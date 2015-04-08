package org.jboss.reddeer.swt.impl.tree;


import java.util.Iterator;
import java.util.List;

import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.api.Tree;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.condition.TreeHasChildren;
import org.jboss.reddeer.swt.condition.TreeItemHasMinChildren;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.core.lookup.TreeItemLookup;
import org.jboss.reddeer.core.matcher.TreeItemTextMatcher;
import org.jboss.reddeer.core.matcher.WithTextMatcher;
import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;

/**
 * Default tree item implementation
 * 
 * @author jjankovi
 *
 */
@SuppressWarnings("unchecked")
public class DefaultTreeItem extends AbstractTreeItem {

	private static final Logger logger = Logger.getLogger(DefaultTreeItem.class);
	
	/**
	 * Tree item with specified path will be constructed 
	 * 
	 * @param treeItemPath
	 */
	public DefaultTreeItem(String... treeItemPath) {
		this(new DefaultTree(), treeItemPath);
	}
	
	/**
	 * Tree item in specified tree with specified path will be constructed 
	 * 
	 * @param treeItemPath
	 */
	public DefaultTreeItem(Tree tree, String... treeItemPath) {
		super(TreeItemLookup.getInstance().getTreeItem(tree.getSWTWidget(), 0, createMatchers(treeItemPath)));
	}
	
	/**
	 * Tree item with specified path will be constructed
	 *
	 * @param treeItemPath
	 */
	public DefaultTreeItem(Matcher<org.eclipse.swt.widgets.TreeItem>... treeItemPath) {
		this(new DefaultTree(), treeItemPath);
	}

	/**
	 * Tree item in specified tree with specified path will be constructed
	 *
	 * @param treeItemPath
	 */
	public DefaultTreeItem(Tree tree, Matcher<org.eclipse.swt.widgets.TreeItem>... treeItemPath) {
		super(TreeItemLookup.getInstance().getTreeItem(tree.getSWTWidget(), 0, treeItemPath));
	}
	
	/**
	 * Tree item with specified index and path will be constructed. 
	 *
	 * @param treeItemPath
	 */
	public DefaultTreeItem(int index, Matcher<org.eclipse.swt.widgets.TreeItem>... treeItemPath) {
		this(new DefaultTree(), index, treeItemPath);
	}

	/**
	 * Tree item in specified tree with specified index and path will be constructed
	 *
	 * @param treeItemPath
	 */
	public DefaultTreeItem(Tree tree, int index, Matcher<org.eclipse.swt.widgets.TreeItem>... treeItemPath) {
		super(TreeItemLookup.getInstance().getTreeItem(tree.getSWTWidget(), index, treeItemPath));
	}
	
	/**
	 * @deprecated Will be removed in 1.0.0. Use {@link DefaultTree}{@link #getItems()} instead
	 * Default parameter-less constructor
	 */
	public DefaultTreeItem() {
		this(new DefaultTree());
	}
	
	/**
	 * @deprecated Will be removed in 1.0.0. Use {@link DefaultTree}{@link #getItems()} instead
	 * TreeItem inside given tree
	 */
	public DefaultTreeItem(Tree tree) {
		super(findTreeItem(tree, 0));
	}
	
	/**
	 * TreeItem inside given composite
	 * @deprecated {@link ReferencedComposite} used to be used to locate the tree. 
	 * Please use {@link #DefaultTreeItem(Tree)} constructor directly. Will be removed in 1.0.0
	 * @param
	 */
	public DefaultTreeItem(ReferencedComposite referencedComposite) {
		this(referencedComposite, 0);
	}
	
	/**
	 * Tree item with specified path inside given composite will be constructed
	 * @param referencedComposite
	 * @param treeItemPathIndex
	 * @deprecated {@link ReferencedComposite} used to be used to locate the tree. 
	 * Please use {@link #DefaultTreeItem(Tree)} constructor directly. Will be removed in 1.0.0
	 */
	public DefaultTreeItem(ReferencedComposite referencedComposite, String... treeItemPath) {
		this(referencedComposite, 0, treeItemPath);
	}

	/**
	 * Tree item with specified path inside given composite will be constructed
	 * @param referencedComposite
	 * @deprecated {@link ReferencedComposite} used to be used to locate the tree. 
	 * Please use {@link #DefaultTreeItem(Tree)} constructor directly. Will be removed in 1.0.0
	 * @param treeItemPath
	 */
	public DefaultTreeItem(ReferencedComposite referencedComposite, Matcher<String>... treeItemPath) {
		this(referencedComposite, 0, treeItemPath);
	}
	
	/**
	 * Tree item with specified tree index and path will be constructed
	 * @deprecated Index used to be used to locate the tree. 
	 * Please use {@link #DefaultTreeItem(Tree)} constructor directly. Will be removed in 1.0.0
	 * @param treeIndex
	 * @param treeItemPath
	 */
	public DefaultTreeItem(int treeIndex, String... treeItemPath) {
		super(findTreeItem((ReferencedComposite)null, treeIndex, treeItemPath));
	}

	/**
	 * Tree item with specified tree index and path inside given composite will be constructed
	 * @deprecated {@link ReferencedComposite} and index used to be used to locate the tree. 
	 * Please use {@link #DefaultTreeItem(Tree)} constructor directly. Will be removed in 1.0.0
	 *  
	 * @param referencedComposite
	 * @param treeIndex
	 * @param treeItemPath
	 */
	public DefaultTreeItem(ReferencedComposite referencedComposite, int treeIndex, String... treeItemPath) {
		super(findTreeItem(referencedComposite, treeIndex, treeItemPath));
	}

	/**
	 * Tree item with specified tree index and path inside given composite will be constructed
	 * @deprecated {@link ReferencedComposite} and index used to be used to locate the tree. 
	 * Please use {@link #DefaultTreeItem(Tree)} constructor directly. Will be removed in 1.0.0
	 * 
	 * @param referencedComposite
	 * @param treeIndex
	 * @param treeItemPath
	 */
	public DefaultTreeItem(ReferencedComposite referencedComposite, int treeIndex, Matcher<String>... treeItemPath) {
		super(findTreeItem(referencedComposite, treeIndex, treeItemPath));
	}

	/**
	 * Tree item with specified tree item index will be constructed
	 * @deprecated Please use {@link DefaultTree}{@link #getItems()}
	 * @param treeItemIndex
	 */
	public DefaultTreeItem(int treeItemIndex) {
		this(null, 0, treeItemIndex);
	}
	
	/**
	 * Tree item with specified tree item index inside given composite will be constructed
	 * @deprecated {@link ReferencedComposite} used to be used to locate the tree. 
	 * Please use {@link #DefaultTreeItem(Tree)} constructor directly. Will be removed in 1.0.0
	 * @param referencedComposite
	 * @param treeItemIndex
	 */
	public DefaultTreeItem(ReferencedComposite referencedComposite, int treeItemIndex) {
		this(referencedComposite, 0, treeItemIndex);
	}
	
	/**
	 * Tree item with specified tree and tree item index will be constructed
	 * @deprecated Index used to be used to locate the tree. 
	 * Please use {@link #DefaultTreeItem(Tree)} constructor directly. Will be removed in 1.0.0
	 * @param treeIndex
	 * @param treeItemIndex
	 */
	public DefaultTreeItem(int treeIndex, int treeItemIndex) {
		super(findTreeItem(null, treeIndex, treeItemIndex));
	}
	
	/**
	 * Tree item with specified tree and tree item index inside given composite will be constructed
	 * @deprecated {@link ReferencedComposite} used to be used to locate the tree. 
	 * Please use {@link #DefaultTreeItem(Tree)} constructor directly. Will be removed in 1.0.0
	 * @param referencedComposite
	 * @param treeIndex
	 * @param treeItemIndex
	 */
	public DefaultTreeItem(ReferencedComposite referencedComposite, int treeIndex, int treeItemIndex) {
		super(findTreeItem(referencedComposite, treeIndex, treeItemIndex));
	}

	/**
	 * Tree item with specified tree and path inside this tree will be constructed. Text from
	 * specified cell will be used instead of item's text.
	 * @deprecated If you need to filter path by different cell index, please use Matcher
	 * @param tree
	 * @param cellIndex
	 * @param treeItemPath
	 */
	public DefaultTreeItem(Tree tree, int cellIndex, String... treeItemPath) {
		super(findTreeItem(tree, cellIndex, treeItemPath));
	}

	private static Matcher<org.eclipse.swt.widgets.TreeItem>[] createMatchers(String[] treeItemPath) {
		Matcher<org.eclipse.swt.widgets.TreeItem>[] matchers = new Matcher[treeItemPath.length];
		for (int i = 0; i < treeItemPath.length; i++){
			matchers[i] = new TreeItemTextMatcher(treeItemPath[i]);
		}
		return matchers;
	}
	
	private static SWTLayerException createItemNotFoundException(List<TreeItem> items, int cellIndex, Matcher<String> pathItem, Matcher<String>[] treeItemPath, Integer treeItemIndex) {
		SWTLayerException exception = new SWTLayerException("No matching tree item found");

		if (treeItemPath != null) {
			StringBuffer sbPath = new StringBuffer("");
			for (Matcher<String> treeItem : treeItemPath) {
				if (sbPath.length() > 0)
					sbPath.append(" > ");
				sbPath.append(treeItem.toString());
			}

			exception.addMessageDetail("Tree Item Path: " + sbPath.toString());
		}

		if (treeItemIndex != null)
			exception.addMessageDetail("Tree Item Index: " + treeItemIndex);

		if (pathItem != null)
			exception.addMessageDetail("Unable to find path item with text: " + pathItem.toString());

		if (items != null) {
			exception.addMessageDetail("These Tree Items have been found at current level:");
			for (TreeItem treeItem : items)
				exception.addMessageDetail("  " + treeItem.getCell(cellIndex));
		}

		return exception;
	}
	
	/**
	 * Find tree item by its path
	 *
	 * @param tree Tree to search for item
	 * @param cellIndex Compare treeItemPath entries to cell specified by this argument. Remember, TreeItem.getText() is in fact doing TreeItem.getCell(0)
	 * @param treeItemPath
	 * @return
	 */
	private static org.eclipse.swt.widgets.TreeItem findTreeItem(Tree tree, int cellIndex, Matcher<String>... treeItemPath) {
		logger.debug(String.format("Search for tree item: cellIndex=%d, treeItemPath='%s'", cellIndex, treeItemPath.toString()));

		/*
		 * Walk down the treeItemPath array, and try to match each entry
		 * with existing tree items in the current level of tree. When
		 * treeItemPath entry matches a tree item, use such a item in
		 * the next iteration. Throw exception otherwise, also throw one
		 * if we run out of treeItemPath entries without matching any item.
		 *
		 * If constructor was called to create DefaultTreeItem using String
		 * array as treeItemPath, it's responsibility of caller of this method
		 * to convert each item from String to Matcher<String> (most probably
		 * using TextMatcher)
		 */

		new WaitUntil(new TreeHasChildren(tree));
		List<TreeItem> items = tree.getItems();

		for(int index = 0; index < treeItemPath.length; index++) {
			Matcher<String> pathItem = treeItemPath[index];

			logger.debug(String.format("  pathItem='%s'", pathItem.toString()));

			TreeItem tiItem = null;
			boolean isFound = false;

			Iterator<TreeItem> itTreeItem = items.iterator();
			while (itTreeItem.hasNext()) {
				tiItem = itTreeItem.next();
				logger.debug(String.format("    consider item '%s'", tiItem.getCell(cellIndex)));

				if (pathItem.matches(tiItem.getCell(cellIndex))) {
					logger.debug("      item matched!");

					isFound = true;
					break;
				}
			}

			/* None of items on this level matched */
			if (!isFound)
				throw createItemNotFoundException(items, cellIndex, pathItem, treeItemPath, null);

			/*
			 * If this is the last iteration of the loop, return the latest
			 * found item - we are at the end of treeItemPath
			 */
			if (index == treeItemPath.length - 1)
				return (org.eclipse.swt.widgets.TreeItem)tiItem.getSWTWidget();

			/* Dive one level deeper */
			new WaitUntil(new TreeItemHasMinChildren(tiItem, 1), TimePeriod.NORMAL, false);
			items = tiItem.getItems();
		}

		/* Out of treeItemPath entries and no item matched */
		throw createItemNotFoundException(items, cellIndex, null, treeItemPath, null);
	}

	/**
	 * Find tree item by its path. Item path is converted to array of TextMatcher,
	 * so its literal is used for matching.
	 *
	 * @param tree Tree to search for item
	 * @param cellIndex Compare treeItemPath entries to cell specified by this argument. Remember, TreeItem.getText() is in fact doing TreeItem.getCell(0)
	 * @param treeItemPath
	 * @return
	 */
	private static org.eclipse.swt.widgets.TreeItem findTreeItem(Tree tree, int cellIndex, String... treeItemPath) {
		WithTextMatcher treeItemPathMatchers[] = new WithTextMatcher[treeItemPath.length];

		for(int i = 0; i < treeItemPath.length; i++)
			treeItemPathMatchers[i] = new WithTextMatcher(treeItemPath[i]);

		return findTreeItem(tree, cellIndex, treeItemPathMatchers);
	}

	/**
	 * Find tree item by its index
	 *
	 * @param tree Tree to search for item
	 * @param treeItemIndex Index of desired item (zero-based)
	 * @return
	 */
	private static org.eclipse.swt.widgets.TreeItem findTreeItem(Tree tree, int treeItemIndex) {
		logger.debug(String.format("Search for tree item: treeItemIndex=%d", treeItemIndex));

		new WaitUntil(new TreeHasChildren(tree));

		List<TreeItem> items = tree.getItems();
		if (items.size() < treeItemIndex + 1)
			throw createItemNotFoundException(items, 0, null, null, treeItemIndex);

		return (org.eclipse.swt.widgets.TreeItem)tree.getItems().get(treeItemIndex).getSWTWidget();
	}

	private static org.eclipse.swt.widgets.TreeItem findTreeItem(ReferencedComposite referencedComposite, int treeIndex, int treeItemIndex) {
		return findTreeItem(new DefaultTree(referencedComposite, treeIndex), treeItemIndex);
	}

	private static org.eclipse.swt.widgets.TreeItem findTreeItem(ReferencedComposite referencedComposite, int treeIndex, String... treeItemPath) {
		return findTreeItem(new DefaultTree(referencedComposite, treeIndex), 0, treeItemPath);
	}

	private static org.eclipse.swt.widgets.TreeItem findTreeItem(ReferencedComposite referencedComposite, int treeIndex, Matcher<String>... treeItemPath) {
		return findTreeItem(new DefaultTree(referencedComposite, treeIndex), 0, treeItemPath);
	}
}