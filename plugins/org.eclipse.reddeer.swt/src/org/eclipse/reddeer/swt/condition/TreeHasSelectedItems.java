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
package org.eclipse.reddeer.swt.condition;


import org.eclipse.reddeer.common.condition.AbstractWaitCondition;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.swt.api.Tree;

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
	
	/**
	 * Construct tree has selected items condition. 
	 * @param tree given tree
	 */
	public TreeHasSelectedItems(Tree tree) {
		super();
		this.tree = tree;
		this.numSelectedItems = 1;
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
	}

	@Override
	public boolean test() {
		int count = tree.getSelectedItems().size();
		log.trace("Count of selected tree items:" + count);
		if (count == numSelectedItems) {
			log.trace(tree.getSelectedItems().get(0).getText());
			return true;
		}
		return false;
	}

	@Override
	public String description() {
		return "tree has " + numSelectedItems + " selected items";
	}
}
