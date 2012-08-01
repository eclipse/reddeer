package org.jboss.reddeer.swt.util;

import org.eclipse.swtbot.swt.finder.utils.SWTUtils;

/**
 * Reddeer display provider
 * @author Jiri Peterka
 *
 */
public class Display {
	
	public static void syncExec(Runnable runnable) {
		SWTUtils.display().syncExec(runnable);
	}

	public static void asyncExec(Runnable runnable) {
		SWTUtils.display().asyncExec(runnable);
	}
	
	public static org.eclipse.swt.widgets.Display getDisplay() {
		return SWTUtils.display();
	}

	
}
