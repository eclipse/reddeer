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
package org.eclipse.reddeer.swt.api;

/** 
 * API for text manipulation.
 * 
 * @author Jiri Peterka
 *
 */
public interface Text extends Control<org.eclipse.swt.widgets.Text> {

	/**
	 * Sets text to the text widget.
	 * 
	 * @param text to set
	 */
	void setText(String text);
	
	/**
	 * Gets text of the text widget.
	 * 
	 * @return text of the text widget
	 */
	String getText();
	
	/**
	 * Gets message of the text widget.
	 * 
	 * @return message of the text widget
	 */
	String getMessage();
	
	/**
	 * Types text using @link(org.eclipse.reddeer.swt.keyboard.Keyboard).
	 * 
	 * @param text to type
	 */
	void typeText(String text);
	
	/**
	 * Finds out whether the text it read only or not.
	 * 
	 * @return true if the text is read only, false otherwise
	 */
	boolean isReadOnly();
}
