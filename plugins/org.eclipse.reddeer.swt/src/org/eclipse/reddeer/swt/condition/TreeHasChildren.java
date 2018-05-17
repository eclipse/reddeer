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
 * Condition is fulfilled when tree has children.
 *
 * @author Jaroslav Jankovic
 * @author Jiri Peterka
 *
 */
public class TreeHasChildren extends AbstractWaitCondition {
	
	private Logger log = Logger.getLogger(TreeHasChildren.class);
	private Tree tree;
	private List<TreeItem> resultChildren; 
	
	/**
	 * Construct tree has children condition. 
	 * @param tree given tree
	 */
	public TreeHasChildren(Tree tree) {
		super();
		this.tree = tree;
		this.resultChildren = new ArrayList<>();
	}

	@Override
	public boolean test() {
		this.resultChildren = tree.getItems();
		log.trace("Count of found tree items:" + this.resultChildren.size());
		if (this.resultChildren.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public String description() {
		return "tree has children";
	}
	
	@SuppressWarnings("unchecked")
	@Override 
	public List<TreeItem> getResult() {
		return this.resultChildren.isEmpty() ? null : this.resultChildren;
	}
}
