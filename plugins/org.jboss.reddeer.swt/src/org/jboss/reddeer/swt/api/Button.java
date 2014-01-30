package org.jboss.reddeer.swt.api;

import org.jboss.reddeer.swt.widgets.Widget;

/**
 * API For Button (Push, Radio, Toggle) manipulation
 * @author Jiri Peterka
 *
 */
public interface Button extends Widget{

	/**
	 * Performs click on a button
	 */
	void click();

	/**
	 * Return text on given Button
	 * @return
	 */
	String getText();
	/**
	 * Return tooltip of given Button
	 * @return
	 */
	String getToolTipText();
			
	boolean isEnabled();
	
	org.eclipse.swt.widgets.Button getSWTWidget();
}
