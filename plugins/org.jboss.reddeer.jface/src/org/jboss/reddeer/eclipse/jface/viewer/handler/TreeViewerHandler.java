package org.jboss.reddeer.eclipse.jface.viewer.handler;

import java.util.Iterator;

import org.jboss.reddeer.eclipse.jface.exception.JFaceLayerException;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.junit.logging.Logger;

public class TreeViewerHandler {
	
	private final Logger logger = Logger.getLogger(this.getClass());
	private static TreeViewerHandler instance;
	
	public TreeViewerHandler() {
	}
	
	/**
	 * Returns instance of TreeViewerHandler class. If instance is not 
	 * existing yet, new instance is created and returned.
	 * 
	 * @return instance of class TreeViewerHandler
	 */
	public static TreeViewerHandler getInstance() {
		if (instance == null) {
			instance = new TreeViewerHandler();
		}
		return instance;
	}
	
	/**
	 * Get tree item specified by a name without decorators - styled text
	 * 
	 * @param treeItem parent item of desired item 
	 * @param name of desired item
	 * @return tree item with specified name
	 */
	public TreeItem getTreeItem(TreeItem treeItem, String name) {
		Iterator<TreeItem> iterator = treeItem.getItems().iterator();
		while (iterator.hasNext()) {
			TreeItem item = iterator.next();
			if (item.getNonStyledText().equals(name)) {
				logger.info("Tree item with non styled text " + name +
						" has been found");
				return item;
			}
		}
		
		throw new JFaceLayerException("Tree item with text "
				+ treeItem.getNonStyledText() + " (without decorators)"
				+ " has no tree item with text " + name + " (without decorators).");
	}
	
	/**
	 * Get tree item specified by path without decorators.
	 * 
	 * @param treeItem
	 * @param path
	 * @return
	 */
	public TreeItem getTreeItem(TreeItem treeItem, String... path) {
		TreeItem item = treeItem;
		int index = 0;
		
		while (index < path.length - 1) {
			item = getTreeItem(item, path[index]);
			if (!item.isExpanded()) {
				item.expand();
			}
			index++;
		}
		
		return getTreeItem(item, path[index]);
	}
}
