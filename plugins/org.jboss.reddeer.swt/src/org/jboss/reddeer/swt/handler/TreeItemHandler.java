package org.jboss.reddeer.swt.handler;

import org.eclipse.swt.widgets.TreeItem;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;

/**
 * Contains methods for handling UI operations on {@link TreeItem} widgets. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class TreeItemHandler {

	private static TreeItemHandler instance;

	private TreeItemHandler() {

	}

	/**
	 * Gets instance of TreeItemHandler.
	 * 
	 * @return instance of TreeItemHandler
	 */
	public static TreeItemHandler getInstance() {
		if (instance == null) {
			instance = new TreeItemHandler();
		}
		return instance;
	}

	/**
	 * Gets text from cell of specified {@link TreeItem} on the position specified by index.
	 * 
	 * @param treeItem tree item to handle
	 * @param cellIndex index of cell to get text
	 * @return text of the cell
	 */
	public String getText(final TreeItem treeItem, final int cellIndex) {
		String text = Display.syncExec(new ResultRunnable<String>() {
			@Override
			public String run() {
				return treeItem.getText(cellIndex);
			}
		});
		return text;
	}

	/**
	 * Gets tool tip of specified {@link TreeItem}.
	 * 
	 * @param item item to handle
	 * @return tool tip text of specified tree item
	 */
	public String getToolTipText(final TreeItem item) {
		String text = Display.syncExec(new ResultRunnable<String>() {
			@Override
			public String run() {
				return item.getParent().getToolTipText();
			}
		});
		return text;
	}

	/**
	 * Finds out whether specified {@link TreeItem} is checked or not.
	 * 
	 * @param item item to handle
	 * @return true if specified tree item is expanded, false otherwise
	 */
	public boolean isExpanded(final TreeItem item) {
		return Display.syncExec(new ResultRunnable<Boolean>() {

			@Override
			public Boolean run() {
				return item.getExpanded();
			}
		});
	}
	
	
	/**
	 * Sets specified text to column on the position specified 
	 * by index in specified {@link TreeItem}. 
	 * 
	 * @param treeItem tree item to handle
	 * @param cellIndex index of cell to set text
	 * @param text text to set
	 */
	public void setText(final TreeItem treeItem, final int cellIndex, final String text) {
		Display.syncExec(new Runnable() {
			
			@Override
			public void run() {
				treeItem.setText(cellIndex, text);
			}
		});
	}
}
