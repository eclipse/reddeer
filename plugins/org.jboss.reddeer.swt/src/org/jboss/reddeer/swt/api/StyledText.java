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
	
}
