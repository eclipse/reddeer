package org.jboss.reddeer.eclipse.jface.viewer.handler;

import java.util.Iterator;

import org.eclipse.swt.custom.StyleRange;
import org.jboss.reddeer.eclipse.jface.exception.JFaceLayerException;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;
import org.jboss.reddeer.common.logging.Logger;

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
			if (getNonStyledText(item).equals(name)) {
				logger.info("Tree item with non styled text " + name +
						" has been found");
				return item;
			}
		}
		
		throw new JFaceLayerException("Tree item with text "
				+ getNonStyledText(treeItem) + " (without decorators)"
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
						currentTextIndex = range.start + range.length + 1;
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
	 * @return non-styled text (without decorators) of the tree item
	 */
	public String getNonStyledText(TreeItem item) {
		return parseText(item).getNonStyledText();
	}
	
	/**
	 * Gets styled texts on the tree item. There could be more than 1
	 * styled texts.
	 *
	 * @return styled texts of the tree item or null if there are not styled texts
	 */
	public String[] getStyledTexts(TreeItem item) {
		return parseText(item).getStyledTexts();
	}
	
	class TreeItemTexts {
		private String nonStyledText;
		private String[] styledTexts;
		
		public TreeItemTexts(String nonStyledText, String[] styledTexts) {
			this.nonStyledText = nonStyledText;
			this.styledTexts = styledTexts;
		}
		
		public String getNonStyledText() {
			return nonStyledText;
		}
		
		public String[] getStyledTexts() {
			return styledTexts;
		}
	}
}
