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

import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.common.util.ResultRunnable;

/**
 * Contains methods for handling UI operations on {@link CLabel} widgets.
 * 
 * @author rawagner
 *
 */
public class CLabelHandler extends ControlHandler{
	
	private static CLabelHandler instance;
	
	/**
	 * Gets instance of CLabelHandler.
	 * 
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
