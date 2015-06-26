package org.jboss.reddeer.swt.api;

import org.jboss.reddeer.swt.widgets.Widget;

/**
 * API for CTab item manipulation.
 * 
 * @author Vlado Pakan
 *
 */
public interface CTabItem extends Widget {

	/**
	 * Activates CTab item.
	 */
	void activate();

	/**
	 * Returns the text of the CTab item.
	 * 
	 * @return text on the CTab item
	 */
	String getText();

	/**
	 * Returns the ToolTip text of the CTab item.
	 * 
	 * @return ToolTip text of the CTab item
	 */
	String getToolTipText();

	/**
	 * Closes CTabItem.
	 */
	void close();

	/**
	 * Find outs whether the close button should be shown or not. 
	 * 
	 * @return true if the close button should be shown
	 */
	boolean isShowClose();
	
	/**
	 * Returns true if the tab is visible
	 * @return
	 */
	boolean isShowing();

	org.eclipse.swt.custom.CTabItem getSWTWidget();
	
	/**
	 * Returns parent folder {@link CTabFolder}
	 * @return
	 */
	CTabFolder getFolder();
}
