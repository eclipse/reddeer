/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.swt.impl.tree;


import org.hamcrest.Matcher;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.core.lookup.TreeItemLookup;
import org.eclipse.reddeer.core.matcher.TreeItemTextMatcher;
import org.eclipse.reddeer.swt.api.Tree;

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
	 * Tree item with specified path will be constructed .
	 *
	 * @param treeItemPath the tree item path
	 */
	public DefaultTreeItem(String... treeItemPath) {
		this(new DefaultTree(), treeItemPath);
	}
	
	public DefaultTreeItem(org.eclipse.swt.widgets.TreeItem widget) {
		super(widget);
	}
	
	/**
	 * Tree item in specified tree with specified path will be constructed .
	 *
	 * @param tree the tree
	 * @param treeItemPath the tree item path
	 */
	public DefaultTreeItem(Tree tree, String... treeItemPath) {
		super(TreeItemLookup.getInstance().getTreeItem(tree.getSWTWidget(),
				0, createMatchers(treeItemPath)));
	}
	
	/**
	 * Tree item with specified path will be constructed.
	 *
	 * @param treeItemPath the tree item path
	 */
	public DefaultTreeItem(Matcher<org.eclipse.swt.widgets.TreeItem>... treeItemPath) {
		this(new DefaultTree(), treeItemPath);
	}

	/**
	 * Tree item in specified tree with specified path will be constructed.
	 *
	 * @param tree the tree
	 * @param treeItemPath the tree item path
	 */
	public DefaultTreeItem(Tree tree, Matcher<org.eclipse.swt.widgets.TreeItem>... treeItemPath) {
		super(TreeItemLookup.getInstance().getTreeItem(tree.getSWTWidget(),
				0, treeItemPath));
	}
	
	/**
	 * Tree item with specified index and path will be constructed. 
	 *
	 * @param index the index
	 * @param treeItemPath the tree item path
	 */
	public DefaultTreeItem(int index, Matcher<org.eclipse.swt.widgets.TreeItem>... treeItemPath) {
		this(new DefaultTree(), index, treeItemPath);
	}

	/**
	 * Tree item in specified tree with specified index and path will be constructed.
	 *
	 * @param tree the tree
	 * @param index the index
	 * @param treeItemPath the tree item path
	 */
	public DefaultTreeItem(Tree tree, int index, Matcher<org.eclipse.swt.widgets.TreeItem>... treeItemPath) {
		super(TreeItemLookup.getInstance().getTreeItem(tree.getSWTWidget(),
				index, treeItemPath));
	}

	private static Matcher<org.eclipse.swt.widgets.TreeItem>[] createMatchers(String[] treeItemPath) {
		Matcher<org.eclipse.swt.widgets.TreeItem>[] matchers = new Matcher[treeItemPath.length];
		for (int i = 0; i < treeItemPath.length; i++){
			matchers[i] = new TreeItemTextMatcher(treeItemPath[i]);
		}
		return matchers;
	}
}