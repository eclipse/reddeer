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
import org.eclipse.reddeer.swt.api.TreeItem;

/**
 * Condition is met when tree item has at least specified count of children.
 * 
 * @author Vlado Pakan
 */
public class TreeItemHasMinChildren extends AbstractWaitCondition {
	
	private final TreeItem treeItem;
	
	private final int minItemsCount;

	/**
	 * Constructs TreeItemHasMinChildren wait condition. Condition is met
	 * when specified tree item has at least specified minimal count of children.
	 * 
	 * @param treeItem tree item to find out count of children
	 * @param minItemsCount minimal count of children which should tree item have
	 * to met condition
	 */
	public TreeItemHasMinChildren(TreeItem treeItem, int minItemsCount) {
		this.treeItem = treeItem;
		this.minItemsCount = minItemsCount;
	}

	@Override
	public boolean test() {
		return treeItem.getItems().size() >= this.minItemsCount;
	}

	@Override
	public String description() {
		return "treeItem '" + treeItem.getText() + "' has " + this.minItemsCount
				+ " children or more";
	}
}