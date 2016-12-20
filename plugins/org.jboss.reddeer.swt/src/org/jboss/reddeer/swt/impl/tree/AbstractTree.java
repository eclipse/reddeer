/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.swt.impl.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.api.Tree;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.core.handler.TreeHandler;
import org.jboss.reddeer.core.handler.TreeItemHandler;
import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.swt.widgets.AbstractWidget;

public abstract class AbstractTree extends AbstractWidget<org.eclipse.swt.widgets.Tree> implements Tree {

	private static final Logger logger = Logger.getLogger(AbstractTree.class);

	private TreeHandler treeHandler = TreeHandler.getInstance();
	private TreeItemHandler treeItemHandler = TreeItemHandler.getInstance();

	protected AbstractTree(ReferencedComposite refComposite, int index, Matcher<?>... matchers){
		super(org.eclipse.swt.widgets.Tree.class, refComposite, index, matchers);
	}
	
	protected AbstractTree(org.eclipse.swt.widgets.Tree tree){
		super(tree);
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Tree#getAllItems()
	 */
	public List<TreeItem> getAllItems() {
		List<TreeItem> list = new LinkedList<TreeItem>();
		list.addAll(getAllItemsRecursive(getItems()));
		return list;
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Tree#getItems()
	 */
	public List<TreeItem> getItems() {
		LinkedList<TreeItem> items = new LinkedList<TreeItem>();
		List<org.eclipse.swt.widgets.TreeItem> eclipseItems = treeHandler.getSWTItems(swtWidget);
		for (org.eclipse.swt.widgets.TreeItem swtTreeItem : eclipseItems) {
			items.addLast(new DefaultTreeItem(swtTreeItem));
		}
		return items;
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Tree#selectItems(org.jboss.reddeer.swt.api.TreeItem[])
	 */
	public void selectItems(final TreeItem... treeItems) {
		// Tree items should be logged, however, the names needs to 
		// be retrieved in UI thread so it might be a performance issue
		logger.info("Select specified tree items");
		org.eclipse.swt.widgets.TreeItem[] items = new org.eclipse.swt.widgets.TreeItem[treeItems.length];
		for (int i=0; i < treeItems.length; i++) {
			items[i] = treeItems[i].getSWTWidget();
		}
		treeItemHandler.selectItems(items);
	}

	@Override
	public List<TreeItem> getSelectedItems() {
		List<TreeItem> selectedItems = new ArrayList<TreeItem>();
		for (org.eclipse.swt.widgets.TreeItem swtItem : treeHandler.getSelection(swtWidget)) {
			selectedItems.add(new DefaultTreeItem(swtItem));
		}
		return selectedItems;
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Tree#setFocus()
	 */
	public void setFocus() {
		treeHandler.setFocus(swtWidget);
	}

	/**
	 * Gets the column count.
	 *
	 * @return the column count
	 * @see Tree#getColumnCount()
	 */
	public int getColumnCount() {
		return treeHandler.getColumnCount(swtWidget);
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Tree#getHeaderColumns()
	 */
	public List<String> getHeaderColumns() {
		return treeHandler.getHeaderColumns(swtWidget);
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Tree#unselectAllItems()
	 */
	public void unselectAllItems() {
		logger.info("Unselect all tree items");
		treeHandler.unselectAllItems(swtWidget);
	}
	
	private List<TreeItem> getAllItemsRecursive(List<TreeItem> parentItems) {
		List<TreeItem> list = new LinkedList<TreeItem>();

		for (TreeItem item : parentItems) {
			list.add(item);
			list.addAll(getAllItemsRecursive(item.getItems()));
		}
		return list;
	}
}
