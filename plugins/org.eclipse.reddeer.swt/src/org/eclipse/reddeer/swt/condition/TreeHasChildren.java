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
 * Condition is fulfilled when tree has children.
 *
 * @author Jaroslav Jankovic
 * @author Jiri Peterka
 *
 */
public class TreeHasChildren extends AbstractWaitCondition {
	private Logger log = Logger.getLogger(TreeHasChildren.class);
	private Tree tree;
	
	/**
	 * Construct tree has children condition. 
	 * @param tree given tree
	 */
	public TreeHasChildren(Tree tree) {
		super();
		this.tree = tree;
	}

	@Override
	public boolean test() {
		int count = tree.getItems().size();
		log.trace("Count of found tree items:" + count);
		if (count > 0) {
			return true;
		}
		return false;
	}

	@Override
	public String description() {
		return "tree has children";
	}
}
