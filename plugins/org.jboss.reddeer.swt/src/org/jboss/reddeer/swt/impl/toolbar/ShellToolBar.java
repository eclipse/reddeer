package org.jboss.reddeer.swt.impl.toolbar;

import org.jboss.reddeer.swt.lookup.ToolBarLookup;

/**
 * ShellToolBar implementation. It expects shell where toolbar should be found on
 * @author jjankovi
 *
 */
public class ShellToolBar extends AbstractToolBar {

	/**
	 * Lookup for ShellToolBar
	 */
	public ShellToolBar() {
		toolBar = new ToolBarLookup().getShellToolBar();
	}
	
}
