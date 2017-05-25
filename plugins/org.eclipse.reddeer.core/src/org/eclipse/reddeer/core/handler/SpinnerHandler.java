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
package org.eclipse.reddeer.core.handler;

import org.eclipse.swt.widgets.Spinner;
import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.common.util.ResultRunnable;

/**
 * Contains methods for handling UI operations on {@link Spinner} widgets.
 * 
 * @author Lucia Jelinkova
 *
 */
public class SpinnerHandler extends ControlHandler{
	
	private static SpinnerHandler instance;
	
	/**
	 * Gets instance of SpinnerHandler.
	 * 
	 * @return instance of SpinnerHandler
	 */
	public static SpinnerHandler getInstance(){
		if(instance == null){
			instance = new SpinnerHandler();
		}
		return instance;
	}

	/**
	 * Gets current value of specified {@link Spinner}.
	 * 
	 * @param spinner spinner to handle
	 * @return current value of specified spinner
	 */
	public int getValue(final Spinner spinner) {
		return Display.syncExec(new ResultRunnable<Integer>() {

			@Override
			public Integer run() {
				return spinner.getSelection();
			}
		});
	}

	/**
	 * Sets value of specified {@link Spinner} to specified value.
	 * 
	 * @param spinner spinner to handle
	 * @param value value to set
	 */
	public void setValue(final Spinner spinner, final int value) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				spinner.setSelection(value);
			}
		});
	}
}
