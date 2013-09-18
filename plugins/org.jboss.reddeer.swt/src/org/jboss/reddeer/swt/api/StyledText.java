package org.jboss.reddeer.swt.api;

/**
 * API For StyledText manipulation
 * @author Jiri Peterka
 *
 */
public interface StyledText {

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
}
