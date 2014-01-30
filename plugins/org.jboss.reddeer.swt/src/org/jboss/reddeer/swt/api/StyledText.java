package org.jboss.reddeer.swt.api;

import org.jboss.reddeer.swt.widgets.Widget;

/**
 * API For StyledText manipulation
 * @author Jiri Peterka
 *
 */
public interface StyledText extends Widget{

	/**
	 * 
	 * @return text of this StyledText
	 */
	
	public String getText();
	
	/**
	 * Sets text of this StyledText
	 */
	
	public void setText(String text);

	
	/**
	 * 
	 * @return Tooltip text of this StyledText
	 */
	
	public String getToolTipText();
	

	/**
	 * Insert text into styledtext
	 * @param line given line 
	 * @param column given column 
	 * @param text to insert
	 */
	public void insertText(int line, int column, String text);
	
	org.eclipse.swt.custom.StyledText getSWTWidget();
}
