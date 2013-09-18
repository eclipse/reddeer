package org.jboss.reddeer.swt.util;

import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;

/**
 * SWTBot provider for RedDeer
 * @author Jiri Peterka
 * @deprecated please avoid using this instance because it will be removed in 0.4
 * if you need SWTBot in your test, please instantiate it yourself (new SWTWorkbenchBot()) 
 * or clone this class. RedDeer code should avoid using this class at all. 
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
