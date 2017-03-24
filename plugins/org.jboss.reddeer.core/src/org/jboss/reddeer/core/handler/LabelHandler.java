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

import org.eclipse.swt.widgets.Label;
import org.jboss.reddeer.common.util.Display;
import org.jboss.reddeer.common.util.ResultRunnable;

/**
 * Contains methods for handling UI operations on {@link Label} widget
 * @author rawagner
 *
 */
public class LabelHandler {
	
	private static LabelHandler instance;
	
	private LabelHandler(){}
	
	/**
	 * Gets instance of LabelHandler
	 * @return instance of LabelHandler
	 */
	public static LabelHandler getInstance(){
		if(instance == null){
			instance = new LabelHandler();
		}
		return instance;
	}
	
	/**
	 * Gets text of label
	 * @param label to handle
	 * @return text of specified label
	 */
	public String getText(Label label){
		return Display.syncExec(new ResultRunnable<String>() {

			@Override
			public String run() {
				return label.getText();
			}
		});
	}

}
