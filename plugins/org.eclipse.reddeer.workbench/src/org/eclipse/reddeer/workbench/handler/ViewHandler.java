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
package org.eclipse.reddeer.workbench.handler;

/**
 * View Handler handles operations for view instances
 * @author rawagner
 *
 */
public class ViewHandler extends WorkbenchPartHandler {
	
	public static ViewHandler instance;
	
	/**
	 * Returns ViewHandler instance
	 * @return ViewHandler instance
	 */
	public static ViewHandler getInstance(){
		if(instance == null){
			instance = new ViewHandler();
		}
		return instance;
	}
	
	/**
	 * Closes specified view
	 * @param view to close
	 */
	/*
	public void close(IViewPart view){
		Display.syncExec(new Runnable() {
			
			@Override
			public void run() {
				view.getViewSite().getPage().hideView(view);
			}
		});
	}
	*/

}
