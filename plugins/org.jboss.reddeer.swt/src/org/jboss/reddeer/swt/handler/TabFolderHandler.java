package org.jboss.reddeer.swt.handler;

import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;

/**
 * TabFolder handler handles operations for SWT TabFolder instances
 * 
 * @author Andrej Podhradsky
 *
 */
public class TabFolderHandler {

	private static TabFolderHandler instance;

	/**
	 * Creates and returns instance of TabItemHandler class
	 * 
	 * @return
	 */
	public static TabFolderHandler getInstance() {
		if (instance == null) {
			instance = new TabFolderHandler();
		}
		return instance;
	}

	public TabItem[] getTabItems(final TabFolder tabFolder) {
		return Display.syncExec(new ResultRunnable<TabItem[]>() {
			@Override
			public TabItem[] run() {
				return tabFolder.getItems();
			}
		});
	}
}
