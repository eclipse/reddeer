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
package org.eclipse.reddeer.jface.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.custom.StyleRange;
import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.common.util.ResultRunnable;
import org.eclipse.reddeer.jface.exception.JFaceLayerException;
import org.eclipse.reddeer.swt.api.Tree;
import org.eclipse.reddeer.swt.api.TreeItem;

/**
 * Represents handler for TreeViewer widget.
 * @since 0.6
 */
public class TreeViewerHandler {
	
	private static TreeViewerHandler instance;
	
	/**
	 * Gets instance of TreeViewerHandler.
	 * 
	 * @return instance of TreeViewerHandler
	 */
	public static TreeViewerHandler getInstance(){
		if(instance == null){
			instance = new TreeViewerHandler();
		}
		return instance;
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
		return getTreeItem(tree.getItems().iterator(), path);
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
		return getTreeItem(treeItem.getItems().iterator(), path);
	}
	
	private TreeItem getTreeItem(Iterator<TreeItem> iterator, String... path) {
		List<TreeItem> resultItems = getTreeItems(iterator, path);

		if (resultItems.size() > 1) {
			throw new JFaceLayerException("There are more items matching specified path so result is ambiguous. " +
					"To obtain all such items, please use method getTreeItems with same parameters");
		}
		
		return resultItems.get(0);
	}	
	
	/**
	 * Gets {@link TreeItem}s defined by a specified path placed under 
	 * a specified {@link Tree}. If there are no suitable items under a 
	 * specified tree, {@link JFaceLayerException} is thrown.
	 * 
	 * @param tree tree where to find tree items
	 * @param path path to tree items
	 * @return tree items placed under a specified tree
	 */
	public List<TreeItem> getTreeItems(Tree tree, String... path) {
		return getTreeItems(tree.getItems().iterator(), path);
	}
	
	/**
	 * Gets {@link TreeItem}s defined by a specified path placed under 
	 * a specified {@link TreeItem}. If there are no suitable items under a 
	 * specified tree item, {@link JFaceLayerException} is thrown.
	 * 
	 * @param item tree item where to find tree items
	 * @param path path to tree items
	 * @return list of tree items placed under a specified tree item
	 */
	public List<TreeItem> getTreeItems(TreeItem item, String... path) {
		return getTreeItems(item.getItems().iterator(), path);
	}
	
	private List<TreeItem> getTreeItems(Iterator<TreeItem> iterator, String... path) {
		if (path == null || path.length == 0) {
			throw new IllegalArgumentException("Path to a tree item cannot be null");
		}
		
		int index = 0;               
		Iterator<TreeItem> nestedItemsIterator = iterator;
		List<TreeItem> resultItems = new ArrayList<TreeItem>();
	
		while (index < path.length) {
			List<TreeItem> nestedItems = new ArrayList<TreeItem>();
			while (nestedItemsIterator.hasNext()) {
				TreeItem item = nestedItemsIterator.next();
				if (getNonStyledText(item).equals(path[index])) {
					if (index == path.length - 1) {
						resultItems.add(item);
					} else {
						nestedItems.addAll(item.getItems());
					}
				}
			}
			nestedItemsIterator = nestedItems.iterator();
			index++;
		}
		
		if (resultItems.size() == 0) {
			throw new JFaceLayerException("There is no tree item with path " + Arrays.toString(path));
		} 
		
		return resultItems;
	}
	
	private TreeItemTexts parseText(TreeItem item) {
		final org.eclipse.swt.widgets.TreeItem swtTreeItem = (org.eclipse.swt.widgets.TreeItem) item.getSWTWidget();
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
