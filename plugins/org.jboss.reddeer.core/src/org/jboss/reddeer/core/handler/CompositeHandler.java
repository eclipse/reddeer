/******************************************************************************* 
 * Copyright (c) 2017 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/
package org.jboss.reddeer.core.handler;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.jboss.reddeer.common.util.Display;
import org.jboss.reddeer.common.util.ResultRunnable;

/**
 * Contains methods for handling UI operations on {@link Composite} widgets.
 * 
 * @author rawagner
 *
 */
public class CompositeHandler extends ControlHandler{
	
	private static CompositeHandler instance;
	
	/**
	 * Gets instance of CompositeHandler.
	 * 
	 * @return instance of CompositeHandler
	 */
	public static CompositeHandler getInstance(){
		if(instance == null){
			instance = new CompositeHandler();
		}
		return instance;
	}
	
	/**
	 * Gets children of composite
	 * @param composite to handle
	 * @return array of children of specified composite
	 */
	public Control[] getChildren(Composite composite){
		return Display.syncExec(new ResultRunnable<Control[]>() {

			@Override
			public Control[] run() {
				return composite.getChildren();
			}
		});
	}

}
