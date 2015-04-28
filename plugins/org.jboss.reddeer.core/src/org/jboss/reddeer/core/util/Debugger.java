package org.jboss.reddeer.core.util;

import org.jboss.reddeer.core.exception.CoreLayerException;

/**
 * Debugger class provides methods useful for test debugging.
 * 
 * @author Jiri Peterka
 *
 */
public class Debugger {

	private static boolean paused = false;
	private static final int time = 1000;
	
	/**
	 * Pause running tests.
	 */
	public static void pause() {
		paused = true;
		while (paused) {
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				e.printStackTrace();
				throw new CoreLayerException("Interrupted exception during sleep", e);
			}
		}
	}
	
	/**
	 * Play (unpause) paused tests.
	 */
	public static void unpause() {
		paused = false;
	}
	
}
