/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.jface.viewer.handler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.custom.StyleRange;
import org.jboss.reddeer.jface.exception.JFaceLayerException;
import org.jboss.reddeer.swt.api.Tree;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.core.util.Display;
import org.jboss.reddeer.core.util.ResultRunnable;

/**
 * Represents handler for TreeViewer widget.
 * @since 0.6
 */
public class TreeViewerHandler {
	
	private static TreeViewerHandler instance;
	
	private TreeViewerHandler() {
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
	 * Gets {@link TreeItem} with specified name placed under 
	 * specified {@link Tree}. If there are more items with specified name
	 * then method throws JFaceLayerException.
	 * 
	 * @param tree tree where to find tree item
	 * @param name name of the tree item to find
	 * @return tree item with specified name placed under specified tree
	 */
	public TreeItem getTreeItem(Tree tree, String name) {
		Iterator<TreeItem> iterator = tree.getItems().iterator();
		List<TreeItem> foundItems = new ArrayList<TreeItem>();
		while (iterator.hasNext()) {
			TreeItem item = iterator.next();
			if (getNonStyledText(item).equals(name)) {
				foundItems.add(item);
			}
		}

		if (foundItems.size() == 0) {
			throw new JFaceLayerException("There is no item with name " + name + " placed " +
				"under specified tree");
		}
		
		if (foundItems.size() > 1) {
			throw new JFaceLayerException("Cannot choose specific tree item with name " + name + ""
					+ " because there are more items with specified name placed under specified tree."); 
		}
		
		return foundItems.get(0);
	}
	
	/**
	 * Gets {@link TreeItem} defined by specified path placed under 
	 * specified {@link Tree}.  If there are more items with specified name
	 * then method throws JFaceLayerException.
	 * 
	 * @param tree tree where to find tree item
	 * @param path path to the tree item
	 * @return tree item placed under specified tree
	 */
	public TreeItem getTreeItem(Tree tree, String... path) {
		TreeItem item;
		
		TreeItem parentItem = getTreeItem(tree, path[0]);
		item = parentItem;
	
		if (path.length > 1) {
			String[] pathToItem = new String[path.length-1];
			System.arraycopy(path, 1, pathToItem, 0, path.length - 1);
			item = getTreeItem(parentItem, pathToItem);
		}
		
		return item;
	}
	
	
	/**
	 * Gets tree item specified by a name without decorators - styled text.
	 * If there are more items with specified name then method throws JFaceLayerException.
	 * 
	 * @param treeItem parent item of desired item 
	 * @param name of desired item
	 * @return tree item with specified name
	 */
	public TreeItem getTreeItem(TreeItem treeItem, String name) {
		Iterator<TreeItem> iterator = treeItem.getItems().iterator();
		List<TreeItem> foundItems = new ArrayList<TreeItem>();
		while (iterator.hasNext()) {
			TreeItem item = iterator.next();
			if (getNonStyledText(item).equals(name)) {
				foundItems.add(item);
			}
		}

		if (foundItems.size() == 0) {
			throw new JFaceLayerException("There is no item with name " + name + " placed "
				+ "under specified tree item");
		}
		
		if (foundItems.size() > 1) {
			throw new JFaceLayerException("Cannot choose specific tree item with name " + name 
					+ " because there are more items with specified name"
					+ " placed under specified tree item."); 
		}
		
		return foundItems.get(0);
	}
	
	/**
	 * Gets tree item specified by path without decorators.
	 * If there are more items with specified name then method throws JFaceLayerException.
	 *
	 * @param treeItem the tree item
	 * @param path the path
	 * @return the tree item
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
	
	private TreeItemTexts parseText(TreeItem item) {
		final org.eclipse.swt.widgets.TreeItem swtTreeItem = item.getSWTWidget();
		return Display.syncExec(new ResultRunnable<TreeViewerHandler.TreeItemTexts>() {

			@Override
			public TreeViewerHandler.TreeItemTexts run() {
				String nonStyledText = null;
				String[] styledTexts = null;
				StyleRange[] styleRanges = getStyleRanges(swtTreeItem);
				
				if (styleRanges == null) {
					// Everything is ok, there is no styled texts
					nonStyledText = swtTreeItem.getText().trim();
					styledTexts = null;
				} else {
					// Here it goes. There are some styled texts
					String rawText = swtTreeItem.getText();
					String[] tmpStyledTexts = new String[styleRanges.length];
					String tmpNonStyledText = null;
					int currentTextIndex = 0;
					int i = 0;
					
					for (StyleRange range: getStyleRanges(swtTreeItem)) {
						// At some point there is a non-styled text
						if (range.start > currentTextIndex) {
							tmpNonStyledText = rawText.substring(currentTextIndex, 
									range.start).trim();
						}
						
						tmpStyledTexts[i] = rawText.substring(range.start, range.start +
								range.length).trim();
						currentTextIndex = range.start + range.length;
						i++;
					}
					
					if (tmpNonStyledText == null) {
						tmpNonStyledText = rawText.substring(currentTextIndex).trim();
					}
					
					nonStyledText = tmpNonStyledText;
					styledTexts = tmpStyledTexts;
				}
				
				return new TreeItemTexts(nonStyledText, styledTexts);
			}
		});
	}

	// Should be run inside Display.syncExec method - in parseText method
	// it is also expected, that styled in array are sequential
	private StyleRange[] getStyleRanges(org.eclipse.swt.widgets.TreeItem swtTreeItem) {
		Object data = swtTreeItem.getData("org.eclipse.jfacestyled_label_key_0");

		if (data == null) {
			return null;
		}

		if (!(data instanceof StyleRange[])) {
			throw new JFaceLayerException(
					"Cannot parse tree item label. Data for key "
					+ "'org.eclipse.jfacestyled_label_key_0' are "
					+ "expected to be of type "	+ StyleRange[].class
					+ " but are " + data.getClass());
		}

		StyleRange[] styles = (StyleRange[]) data;
		
		if (styles.length == 0) {
			return null;
		}

		return styles;
	}


	/**
	 * Gets non-styled text of the tree item.
	 *
	 * @param item the item
	 * @return non-styled text (without decorators) of the tree item
	 */
	public String getNonStyledText(TreeItem item) {
		return parseText(item).getNonStyledText();
	}
	
	/**
	 * Gets styled texts on the tree item. There could be more than 1
	 * styled texts.
	 *
	 * @param item the item
	 * @return styled texts of the tree item or null if there are not styled texts
	 */
	public String[] getStyledTexts(TreeItem item) {
		return parseText(item).getStyledTexts();
	}
	
	class TreeItemTexts {
		private String nonStyledText;
		private String[] styledTexts;
		
		/**
		 * Instantiates a new tree item texts.
		 *
		 * @param nonStyledText the non styled text
		 * @param styledTexts the styled texts
		 */
		public TreeItemTexts(String nonStyledText, String[] styledTexts) {
			this.nonStyledText = nonStyledText;
			this.styledTexts = styledTexts;
		}
		
		/**
		 * Gets the non styled text.
		 *
		 * @return the non styled text
		 */
		public String getNonStyledText() {
			return nonStyledText;
		}
		
		/**
		 * Gets the styled texts.
		 *
		 * @return the styled texts
		 */
		public String[] getStyledTexts() {
			return styledTexts;
		}
	}
}
