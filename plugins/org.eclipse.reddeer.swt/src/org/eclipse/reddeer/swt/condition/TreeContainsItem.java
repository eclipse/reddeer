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

import java.util.Arrays;

import org.eclipse.reddeer.common.condition.AbstractWaitCondition;
import org.eclipse.reddeer.swt.api.Tree;
import org.eclipse.reddeer.swt.impl.tree.DefaultTreeItem;

public class TreeContainsItem extends AbstractWaitCondition {

	private Tree tree;
	private String[] itemPath;

	/**
	 * Constructs TreeContainsItem wait condition. Condition is met when the
	 * specified tree contains the tree item with specified text.
	 * 
	 * @param tree tree where to look for an item
	 * @param itemPath path to an item
	 */
	public TreeContainsItem(Tree tree, String... itemPath) {
		this.tree = tree;
		this.itemPath = itemPath;
	}

	@Override
	public boolean test() {
		try{
			new DefaultTreeItem(tree, itemPath);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public String description() {
		return "tree contains item '" + Arrays.toString(itemPath);
	}

}
