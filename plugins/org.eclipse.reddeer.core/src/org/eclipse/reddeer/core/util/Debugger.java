/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.core.util;

import org.eclipse.reddeer.core.exception.CoreLayerException;

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
