/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributor:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.jboss.reddeer.core.test.condition.shells;

import org.jboss.reddeer.core.util.InstanceValidator;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.impl.tree.DefaultTree;
import org.jboss.reddeer.swt.test.impl.tree.AbstractTreeTest;

import java.util.Iterator;
import java.util.Stack;

/**
 *
 * @author Jan Novak <jnovak@redhat.com>
 */
public class TreeShell extends AbstractTreeTest {

	private static DefaultTree tree;

	public TreeShell() {
		super();
		init();
	}

	private void init() {
		this.setUp();
		tree = new DefaultTree();
		InstanceValidator.checkNotNull(tree, "tree");
		createTreeItems(tree.getSWTWidget());
	}

	public void close() {
		if (tree != null) {
			this.cleanup();
			tree = null;
		}
	}

	/**
	 * Depth-first search on the tree
	 * 
	 * @return data in Iterable
	 */
	public Iterable<TreeItem> dfs() {
		return () -> new Iterator<TreeItem>() {
			Stack<TreeItem> stack = new Stack<>();
			{
				stack.addAll(tree.getItems());
			}

			@Override
			public boolean hasNext() {
				return !stack.isEmpty();
			}

			@Override
			public TreeItem next() {
				TreeItem pop = stack.pop();
				stack.addAll(pop.getItems());

				pop.expand();
				return pop;
			}
		};
	}

}