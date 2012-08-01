package org.jboss.reddeer.swt.util;

import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;

/**
 * Bot provider for all redder
 * @author Jiri Peterka
 *
 */
public class Bot {
	/**
	 * Workbench bot provider
	 */
	static SWTWorkbenchBot bot = new SWTWorkbenchBot();
	
	public static SWTWorkbenchBot get() {
		return bot;		
	}
	
	public static SWTWorkbenchBot getNew() {
		bot = new SWTWorkbenchBot();
		return get();
	}
	
}
