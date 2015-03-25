package org.jboss.reddeer.swt.util;

import org.jboss.reddeer.swt.exception.SWTLayerException;

/**
 * Debugger class providing methods useful for test debugging
 * @author Jiri Peterka
 * @deprecated since 0.8, use {@link #org.jboss.reddeer.core.util.Debugger}
 */
@Deprecated
public class Debugger {

	private static boolean paused = false;
	private static final int time = 1000;
	
	public static void pause() {
		paused = true;
		while (paused) {
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				e.printStackTrace();
				throw new SWTLayerException("Interrupted exception during sleep", e);
			}
		}
	}
	
	public static void unpause() {
		paused = false;
	}
	
}
