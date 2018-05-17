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
package org.eclipse.reddeer.swt.condition;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.reddeer.common.condition.AbstractWaitCondition;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.swt.api.Tree;
import org.eclipse.reddeer.swt.api.TreeItem;

/**
 * Condition is fulfilled when tree has selected specified amount of items.
 *
 * @author Vlado Pakan
 *
 */
public class TreeHasSelectedItems extends AbstractWaitCondition {
	
	private Logger log = Logger.getLogger(TreeHasSelectedItems.class);
	private Tree tree;
	private int numSelectedItems;
	private List<TreeItem> resultItems;
	
	/**
	 * Construct tree has selected items condition. 
	 * @param tree given tree
	 */
	public TreeHasSelectedItems(Tree tree) {
		this(tree, 1);
	}
	/**
	 * Construct tree has selected items condition. 
	 * @param tree tree to check for selected items
	 * @param numSelectedItems number of selected items
	 */
	public TreeHasSelectedItems(Tree tree , int numSelectedItems) {
		super();
		this.numSelectedItems = numSelectedItems;
		this.tree = tree;
		this.resultItems = new ArrayList<>();
	}

	@Override
	public boolean test() {
		this.resultItems = tree.getSelectedItems();
		log.trace("Count of selected tree items:" + this.resultItems.size());
		if (this.resultItems.size() == numSelectedItems) {
			log.trace(tree.getSelectedItems().get(0).getText());
			return true;
		}
		return false;
	}

	@Override
	public String description() {
		return "tree has " + numSelectedItems + " selected items";
	}
	
	@SuppressWarnings("unchecked")
	@Override 
	public List<TreeItem> getResult() {
		return this.resultItems.isEmpty() ? null : this.resultItems;
	}
}
