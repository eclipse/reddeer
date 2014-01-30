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
	
	org.eclipse.swt.widgets.Text getSWTWidget();
}
