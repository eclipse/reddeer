package org.jboss.reddeer.swt.api;

import org.jboss.reddeer.swt.widgets.Widget;


/** 
 * API for Text manipulation
 * @author Jiri Peterka
 *
 */
public interface Text extends Widget{

	/**
	 * Set text to Text widget
	 * @param text
	 */
	void setText(String text);
	
	/**
	 * Gets text of the widget
	 * @return
	 */
	String getText();
	
	/**
	 * Get tooltip of the
	 * @return
	 */
	String getToolTipText();
	
	/**
	 * Sets focus to text
	 */
	void setFocus();
	
	/**
	 * Types text using @link(org.jboss.reddeer.swt.keyboard.Keyboard)
	 * @param text
	 */
	
	void typeText(String text);
	
	org.eclipse.swt.widgets.Text getSWTWidget();
}
