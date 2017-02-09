/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.swt.api;

import org.jboss.reddeer.swt.widgets.Widget;

/** 
 * API for text manipulation.
 * 
 * @author Jiri Peterka
 *
 */
public interface Text extends Widget<org.eclipse.swt.widgets.Text> {

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
	 * Gets ToolTip of the text widget.
	 * 
	 * @return ToolTip text of the text widget
	 */
	String getToolTipText();
	
	/**
	 * Sets focus on the text widget.
	 */
	void setFocus();
	
	/**
	 * Types text using @link(org.jboss.reddeer.swt.keyboard.Keyboard).
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
