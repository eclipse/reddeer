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

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Label;
import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.common.util.ResultRunnable;

/**
 * Contains methods for handling UI operations on {@link Label} widgets.
 * 
 * @author rawagner
 *
 */
public class LabelHandler extends ControlHandler{
	
	private static LabelHandler instance;
	
	/**
	 * Gets instance of LabelHandler.
	 * 
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
	/**
	 * Returns label image
	 * @param swtLabel label to get image from
	 * @return label image
	 */
	public Image getImage(final Label swtLabel){
		return Display.syncExec(new ResultRunnable<Image>() {

			@Override
			public Image run() {
				return swtLabel.getImage();
			}
		});
	}

}
