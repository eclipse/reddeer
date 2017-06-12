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
