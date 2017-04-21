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
package org.eclipse.reddeer.gef.spy;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.TreeNode;

/**
 * Extended TreeNode by implementing children as collection instead of array.
 * 
 * @author apodhrad
 *
 */
public class TreeNodeExt extends TreeNode {

	private List<TreeNode> children;

	/**
	 * Constructs a new instance of <code>TreeNodeExt</code>.
	 * 
	 * @param value
	 *            The value held by this node; may be anything.
	 */
	public TreeNodeExt(Object value) {
		super(value);
		children = new ArrayList<TreeNode>();
	}

	/**
	 * Adds a child node.
	 * 
	 * @param node
	 *            Child node
	 */
	public void addChild(TreeNode node) {
		children.add(node);
	}

	@Override
	public TreeNode[] getChildren() {
		return (TreeNode[]) children.toArray(new TreeNode[children.size()]);
	}

	@Override
	public boolean hasChildren() {
		return !children.isEmpty();
	}

}
