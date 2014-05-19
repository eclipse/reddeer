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
	
	/**
	 * Returns position of given text in this StyledText.
	 * This could be helpful when one want to insert text on specific location, determined by some text.
	 * 
	 * @param text
	 * @return returns position of first character of first occurence of given text or -1 when text is not found
	 */
	
	public int getPositionOfText(String text);
	
	/**
	 * Select specified text in styled text
	 * @param text to select
	 */
	void selectText(String text);
	
	
	/**
	 * Select position
	 * @param position to select
	 */
	void selectPosition(int position);
	
	/**
	 * Get selected text
	 * @return selected text
	 */
	String getSelectionText();
	
	org.eclipse.swt.custom.StyledText getSWTWidget();
}
