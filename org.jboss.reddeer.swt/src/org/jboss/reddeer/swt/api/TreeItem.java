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
	/**
	 * Expands item
	 */
	public void expand();
	/**
	 * Double Click on item
	 */
	public void doubleClick();
	/**
	 * Returns true when Tree Item is selected
	 * @return
	 */
	public boolean isSelected();
			
}
