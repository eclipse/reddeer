package org.jboss.reddeer.swt.impl.tree;


import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.core.lookup.TreeItemLookup;
import org.jboss.reddeer.core.matcher.TreeItemTextMatcher;
import org.jboss.reddeer.swt.api.Tree;

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

	private static Matcher<org.eclipse.swt.widgets.TreeItem>[] createMatchers(String[] treeItemPath) {
		Matcher<org.eclipse.swt.widgets.TreeItem>[] matchers = new Matcher[treeItemPath.length];
		for (int i = 0; i < treeItemPath.length; i++){
			matchers[i] = new TreeItemTextMatcher(treeItemPath[i]);
		}
		return matchers;
	}
}