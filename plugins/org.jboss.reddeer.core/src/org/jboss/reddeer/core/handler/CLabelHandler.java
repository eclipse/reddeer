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

import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Image;
import org.jboss.reddeer.common.util.Display;
import org.jboss.reddeer.common.util.ResultRunnable;

/**
 * Contains methods for handling UI operations on {@link CLabel} widgets.
 * 
 * @author rawagner
 *
 */
public class CLabelHandler {
	
	private static CLabelHandler instance;
	
	private CLabelHandler(){}
	
	/**
	 * Get instance of CLabelHander
	 * @return instance of CLabelHandler
	 */
	public static CLabelHandler getInstance(){
		if(instance == null){
			instance = new CLabelHandler();
		}
		return instance;
	}
	
	/**
	 * Gets text of clabel
	 * @param clabel to get text from
	 * @return text of specified clabel
	 */
	public String getText(CLabel clabel){
		return Display.syncExec(new ResultRunnable<String>(){

			@Override
			public String run() {
				return clabel.getText();
			}
		});
	}
	
	/**
	 * Gets alignment of clabel
	 * @param clabel to get alignment from
	 * @return alignment of specified clabel
	 */
	public int getAlignment(CLabel clabel){
		return Display.syncExec(new ResultRunnable<Integer>() {

			@Override
			public Integer run() {
				return clabel.getAlignment();
			}
		});
	}
	
	/**
	 * Gets image of clabel
	 * @param clabel to get image from
	 * @return image of specified clabel
	 */
	public Image getImage(CLabel clabel){
		return Display.syncExec(new ResultRunnable<Image>() {

			@Override
			public Image run() {
				return clabel.getImage();
			}
		});
	}

}
