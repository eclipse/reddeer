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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.hamcrest.Matcher;
import org.eclipse.reddeer.common.exception.RedDeerException;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.swt.api.Tree;
import org.eclipse.reddeer.swt.api.TreeItem;
import org.eclipse.reddeer.core.handler.TreeHandler;
import org.eclipse.reddeer.core.handler.TreeItemHandler;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.swt.widgets.AbstractControl;

public abstract class AbstractTree extends AbstractControl<org.eclipse.swt.widgets.Tree> implements Tree {

	private static final Logger logger = Logger.getLogger(AbstractTree.class);


	protected AbstractTree(ReferencedComposite refComposite, int index, Matcher<?>... matchers){
		super(org.eclipse.swt.widgets.Tree.class, refComposite, index, matchers);
	}
	
	protected AbstractTree(org.eclipse.swt.widgets.Tree tree){
		super(tree);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.swt.api.Tree#getAllItems()
	 */
	public List<TreeItem> getAllItems() {
		List<TreeItem> list = new LinkedList<TreeItem>();
		list.addAll(getAllItemsRecursive(getItems()));
		return list;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.swt.api.Tree#getItems()
	 */
	public List<TreeItem> getItems() {
		LinkedList<TreeItem> items = new LinkedList<TreeItem>();
		List<org.eclipse.swt.widgets.TreeItem> eclipseItems = TreeHandler.getInstance().getSWTItems(swtWidget);
		for (org.eclipse.swt.widgets.TreeItem swtTreeItem : eclipseItems) {
			DefaultTreeItem item = null;
			try{
				item = new DefaultTreeItem(swtTreeItem);
				items.addLast(item);
			} catch (RedDeerException e) {
				if(TreeHandler.getInstance().isDisposed(swtTreeItem)){
					continue;
				} 
				throw e;
			} 
		}
		return items;
	}
	
	public TreeItem getItem(String... itemPath){
		return new DefaultTreeItem(this, itemPath);
	}
	
	public TreeItem getItem(Matcher<org.eclipse.swt.widgets.TreeItem>... itemMatchers){
		return new DefaultTreeItem(this, itemMatchers);
	}

	public void selectItems(final TreeItem... treeItems) {
		// Tree items should be logged, however, the names needs to 
		// be retrieved in UI thread so it might be a performance issue
		logger.info("Select specified tree items");
		org.eclipse.swt.widgets.TreeItem[] items = new org.eclipse.swt.widgets.TreeItem[treeItems.length];
		for (int i=0; i < treeItems.length; i++) {
			items[i] = treeItems[i].getSWTWidget();
		}
		TreeItemHandler.getInstance().selectItems(items);
	}

	@Override
	public List<TreeItem> getSelectedItems() {
		List<TreeItem> selectedItems = new ArrayList<TreeItem>();
		for (org.eclipse.swt.widgets.TreeItem swtItem : TreeHandler.getInstance().getSelection(swtWidget)) {
			DefaultTreeItem item = null;
			try{
				item = new DefaultTreeItem(swtItem);
				selectedItems.add(item);
			} catch (RedDeerException e) {
				if(TreeHandler.getInstance().isDisposed(swtItem)){
					continue;
				} 
				throw e;
			}
		}
		return selectedItems;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.swt.api.Tree#setFocus()
	 */
	public void setFocus() {
		TreeHandler.getInstance().setFocus(swtWidget);
	}

	/**
	 * Gets the column count.
	 *
	 * @return the column count
	 * @see Tree#getColumnCount()
	 */
	public int getColumnCount() {
		return TreeHandler.getInstance().getColumnCount(swtWidget);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.swt.api.Tree#getHeaderColumns()
	 */
	public List<String> getHeaderColumns() {
		return TreeHandler.getInstance().getHeaderColumns(swtWidget);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.swt.api.Tree#unselectAllItems()
	 */
	public void unselectAllItems() {
		logger.info("Unselect all tree items");
		TreeHandler.getInstance().unselectAllItems(swtWidget);
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
