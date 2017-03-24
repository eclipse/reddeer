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
package org.jboss.reddeer.uiforms.handler;

import org.eclipse.ui.forms.widgets.Form;
import org.jboss.reddeer.common.util.Display;
import org.jboss.reddeer.common.util.ResultRunnable;

/**
 * Contains methods for handling UI operations on {@link Form} widget
 * @author rawagner
 *
 */
public class FormHandler {
	
	private static FormHandler instance;
	
	private FormHandler(){}
	
	/**
	 * Gets instance of FormHandler
	 * @return instance of FormHandler
	 */
	public static FormHandler getInstance(){
		if(instance == null){
			instance = new FormHandler();
		}
		return instance;
	}
	
	/**
	 * Gets text of form
	 * @param form to handle
	 * @return text of specified form
	 */
	public String getText(final Form form){
		return Display.syncExec(new ResultRunnable<String>() {

			@Override
			public String run() {
				return form.getText();
			}
		});
	}

}
