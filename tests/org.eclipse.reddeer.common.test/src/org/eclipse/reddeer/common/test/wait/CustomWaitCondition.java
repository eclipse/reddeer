/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.common.test.wait;

import org.eclipse.reddeer.common.condition.AbstractWaitCondition;
import org.eclipse.reddeer.common.util.Display;

/**
 * Customizable wait condition
 * @author odockal
 *
 */
public class CustomWaitCondition extends AbstractWaitCondition {

	private Runnable runnable = null;
	private int counter = 0;
	private int treshold = 5;
	private boolean initialState = false;
	private boolean conditionChanged = false;
	
	public CustomWaitCondition(boolean initialState, int treshold, Runnable runnable) {
		this.runnable = runnable;
		this.treshold = treshold;
		this.initialState = initialState;
	}
	
	public CustomWaitCondition(boolean initialState, int treshold) {
		this(initialState, treshold, null);
	}
	
	@Override
	public boolean test() {
		if (runnable != null) {
			runnable.run();
		}
		counter++;
		if ( counter >= treshold ) {
			this.conditionChanged = true; 
			return !initialState;
		} else {
			return initialState;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Boolean getResult() {
		return conditionChanged;
	}

	public static void sleep(long milliseconds) {
		org.eclipse.swt.widgets.Display display = Display.getDisplay();
		if(display != null && Thread.currentThread().equals(display.getThread())) {
			throw new RuntimeException("Tried to execute sleep in UI thread!");
		}
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			throw new RuntimeException("Sleep interrupted", e);
		}
	}
	
}
