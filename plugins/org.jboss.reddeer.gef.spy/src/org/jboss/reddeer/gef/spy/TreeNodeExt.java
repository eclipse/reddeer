package org.jboss.reddeer.gef.spy;

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
