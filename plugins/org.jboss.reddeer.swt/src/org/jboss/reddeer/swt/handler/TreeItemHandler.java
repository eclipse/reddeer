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
	 * Creates and returns instance of ComboHandler class
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
	 * @param tableItem given widget
	 * @Param cellIndex index of cell
	 * @return returns widget text
	 */
	public String getText(final TreeItem tableItem, final int cellIndex) {
		String text = Display.syncExec(new ResultRunnable<String>() {
			@Override
			public String run() {
				return tableItem.getText(cellIndex);
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
}
