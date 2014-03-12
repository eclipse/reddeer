package org.jboss.reddeer.swt.handler;

import org.eclipse.swt.custom.CTabFolder;
import org.jboss.reddeer.swt.util.Display;


/**
 * Contains methods that handle UI operations on {@link CTabFolderHandler} widgets. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class CTabFolderHandler {

	private static CTabFolderHandler instance;

	private CTabFolderHandler() {

	}

	/**
	 * Creates and returns instance of ComboHandler class
	 * 
	 * @return
	 */
	public static CTabFolderHandler getInstance() {
		if (instance == null) {
			instance = new CTabFolderHandler();
		}
		return instance;
	}

	public void setFocus(final CTabFolder folder) {
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				folder.forceFocus(); 
			}
		});
	}
}
