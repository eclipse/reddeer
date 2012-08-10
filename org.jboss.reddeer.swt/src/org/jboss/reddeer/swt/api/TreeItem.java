package org.jboss.reddeer.swt.api;

/**
 * API for Tree manipulation
 * @author Jiri Peterka
 *
 */
public interface TreeItem {

	void select();
	
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

			
}
