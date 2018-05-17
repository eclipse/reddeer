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
package org.eclipse.reddeer.core.handler;

import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.common.util.ResultRunnable;

/**
 *	Contains methods that handle UI operations on {@link ProgressBar} widgets. 
 * 
 * @author rhopp
 *
 */
public class ProgressBarHandler extends ControlHandler{
	
	private static ProgressBarHandler instance;
	
	/**
	 * Gets instance of ProgressBarHandler.
	 * 
	 * @return instance of ProgressBarHandler
	 */
	public static ProgressBarHandler getInstance(){
		if(instance == null){
			instance = new ProgressBarHandler();
		}
		return instance;
	}
	
	/**
	 * Returns state of {@link ProgressBar}.
	 *
	 * @param widget the widget
	 * @return the state
	 */
	
	public int getState(final ProgressBar widget){
		return Display.syncExec(new ResultRunnable<Integer>() {
			@Override
			public Integer run() {
				return widget.getState();
			}
		});
	}
	
	/**
	 * Returns maximum of {@link ProgressBar}.
	 *
	 * @param widget the widget
	 * @return the maximum
	 */
	
	public int getMaximum(final ProgressBar widget){
		return Display.syncExec(new ResultRunnable<Integer>() {
			@Override
			public Integer run() {
				return widget.getMaximum();
			}
		});
	}
	
	/**
	 * Returns minimum of {@link ProgressBar}.
	 *
	 * @param widget the widget
	 * @return the minimum
	 */
	
	public int getMinimum(final ProgressBar widget){
		return Display.syncExec(new ResultRunnable<Integer>() {
			@Override
			public Integer run() {
				return widget.getMinimum();
			}
		});
	}
	
	/**
	 * Returns selection of {@link ProgressBar}.
	 *
	 * @param widget the widget
	 * @return the selection
	 */
	
	public int getSelection(final ProgressBar widget){
		return Display.syncExec(new ResultRunnable<Integer>() {
			@Override
			public Integer run() {
				return widget.getSelection();
			}
		});
	}

}
