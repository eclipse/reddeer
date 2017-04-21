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

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.common.util.ResultRunnable;
import org.eclipse.reddeer.core.exception.CoreLayerException;

/**
 * Contains methods for handling UI operations on {@link Text} widgets.
 * 
 * @author Lucia Jelinkova
 *
 */
public class TextHandler extends ControlHandler{
	
	private static TextHandler instance;
	
	/**
	 * Gets instance of TextHandler.
	 * 
	 * @return instance of TextHandler
	 */
	public static TextHandler getInstance(){
		if(instance == null){
			instance = new TextHandler();
		}
		return instance;
	}

	/**
	 * Sets specified text into {@link Text}.
	 * 
	 * @param textWidget text widge to handle
	 * @param text text to be set
	 */
	public void setText(final Text textWidget, final String text) {
		checkModalShells(textWidget);
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				Text textField = (Text) textWidget;
				if (!textField.getEditable()) {
					throw new CoreLayerException("Text field is not editable");
				}
				textField.setText(text);
			}
		});
	}
	
	/**
	 * Gets text of text widget
	 * @param textWidget to handle
	 * @return text of specified text widget
	 */
	public String getText(final Text textWidget){
		return Display.syncExec(new ResultRunnable<String>() {

			@Override
			public String run() {
				return textWidget.getText();
			}
		});
	}

	/**
	 * Gets text from specified {@link Text} widget.
	 * 
	 * @param textWidget text widget to handle
	 * @return text from the text widget
	 */
	public String getMessage(final Text textWidget) {
		return Display.syncExec(new ResultRunnable<String>() {

			@Override
			public String run() {
				return textWidget.getMessage();
			}
		});
	}

	/**
	 * Finds out whether specified {@link Text} widget is read-only or not.
	 * Readability means whether is widget editable or not.
	 * 
	 * @param textWidget text widget to handle
	 * @return true if text widget is read only, false otherwise
	 */
	public boolean isReadOnly(final Text textWidget) {
		return Display.syncExec(new ResultRunnable<Boolean>() {

			@Override
			public Boolean run() {
				return (textWidget.getStyle() & SWT.READ_ONLY) != 0;
			}
		});
	}
}
