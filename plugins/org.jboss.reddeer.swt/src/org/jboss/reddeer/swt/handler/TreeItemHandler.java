package org.jboss.reddeer.swt.handler;

import org.eclipse.swt.widgets.TreeItem;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;

/**
 * Contains methods that handle UI operations on {@link TreeItem} widgets. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class TreeItemHandler {

	private static TreeItemHandler instance;

	private TreeItemHandler() {

	}

	/**
	 * Creates and returns instance of TreeHandler class
	 * 
	 * @return
	 */
	public static TreeItemHandler getInstance() {
		if (instance == null) {
			instance = new TreeItemHandler();
		}
		return instance;
	}

	/**
	 * Gets text on given cell index 
	 * 
	 * @param treeItem given widget
	 * @Param cellIndex index of cell
	 * @return returns widget text
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
	 * Gets tooltip if supported widget type
	 * 
	 * @param widget
	 * @return widget text
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
	 * Checks if supported widget is expanded
	 * 
	 * @param item	given widget
	 * @return	true if widget is expanded
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
	 * Sets text on given cell index 
	 * 
	 * @param treeItem given widget
	 * @Param cellIndex index of cell
	 * @param text to set
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
