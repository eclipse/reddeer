package org.jboss.reddeer.swt.api;

import org.jboss.reddeer.swt.reference.ReferencedComposite;
import org.jboss.reddeer.swt.wait.TimePeriod;
import org.jboss.reddeer.swt.widgets.Widget;

/**
 * API For ExpandBarItem manipulation
 * @author Vlado Pakan
 *
 */
public interface ExpandBarItem extends ReferencedComposite, Widget{
	/**
	 * Returns the text of expand bar item
	 * 
	 * @return
	 */
	String getText();
	
	/**
	 * Returns the tool tip text of expand bar item
	 * 
	 * @return
	 */
	String getToolTipText();
	/**
	 * Expands expand bar
	 */
	void expand();
	/**
	 * Expands expand bar and wait for specified time period
	 */
	void expand(TimePeriod timePeriod);
	/**
	 * Collapse expand bar
	 */
	void collapse();
	/**
	 * Returns enclosing Expand Bar
	 * @return
	 */
	org.eclipse.swt.widgets.ExpandBar getSWTParent();
	
	/**
	 * Returns Expand Bar
	 * @return
	 */
	ExpandBar getParent();

	/**
	 * Returns true when Expand Bar Item is expanded 
	 * @return
	 */
	boolean isExpanded();
	 
	org.eclipse.swt.widgets.ExpandItem getSWTWidget();
}
