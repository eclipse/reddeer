package org.jboss.reddeer.swt.handler;

import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;

/**
 * Contains methods for handling UI operations on {@link TabFolder} widgets.
 * 
 * @author Andrej Podhradsky
 *
 */
public class TabFolderHandler {

	private static TabFolderHandler instance;

	/**
	 * Gets instance of TabItemHandler.
	 * 
	 * @return instance of TabFolderHandler
	 */
	public static TabFolderHandler getInstance() {
		if (instance == null) {
			instance = new TabFolderHandler();
		}
		return instance;
	}

	/**
	 * Gets tab items nested in specified {@link TabFolder}.
	 * 
	 * @param tabFolder tab folder to handle
	 * @return tab items nested in specified tab folder
	 */
	public TabItem[] getTabItems(final TabFolder tabFolder) {
		return Display.syncExec(new ResultRunnable<TabItem[]>() {
			@Override
			public TabItem[] run() {
				return tabFolder.getItems();
			}
		});
	}

	/**
	 * Sets focus on specified {@link TabFolder}.
	 * 
	 * @param folder folder to handle
	 */
	public void setFocus(final TabFolder folder) {
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				folder.forceFocus();
			}
		});
	}
}
