package org.jboss.reddeer.swt.handler;

import org.eclipse.swt.custom.CTabFolder;
import org.jboss.reddeer.swt.util.Display;

/**
 * Contains methods for handling UI operations on {@link CTabFolder} widgets.
 * 
 * @author Lucia Jelinkova
 *
 */
public class CTabFolderHandler {

	private static CTabFolderHandler instance;

	private CTabFolderHandler() {

	}

	/**
	 * Gets instance of CTabFolderHandler.
	 * 
	 * @return instance of CTabFolderHandler
	 */
	public static CTabFolderHandler getInstance() {
		if (instance == null) {
			instance = new CTabFolderHandler();
		}
		return instance;
	}

	/**
	 * Sets focus on specified {@link CTabFolder}.
	 * 
	 * @param folder folder to handle
	 */
	public void setFocus(final CTabFolder folder) {
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				folder.forceFocus();
			}
		});
	}
}
