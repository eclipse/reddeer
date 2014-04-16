package org.jboss.reddeer.uiforms.api;

import org.jboss.reddeer.swt.widgets.Widget;

/**
 * Represents FormText @see{
 * 
 * @author rhopp
 *
 */

public interface FormText extends Widget{

	/**
	 * Returns whole text of this FormText
	 * 
	 * @return text
	 */
	public String getText();

	/**
	 * Returns currently selected text
	 * 
	 * @return selected text
	 */

	public String getSelectionText();

	/**
	 * Clicks on this formText (invokes linkActivated HyperlinkEvent)
	 * 
	 */
	public void click();

	/**
	 * 
	 * @return true if this FormText is focused
	 */
	public boolean hasFocus();

	/**
	 * Returns tooltip text of this FormText
	 * 
	 * @return
	 */
	public String getTooltipText();

	/**
	 * Sets focus to this widget.
	 */
	public void setFocus();
}
