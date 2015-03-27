package org.jboss.reddeer.swt.api;

import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.swt.widgets.Widget;

/**
 * API for expand bar item manipulation.
 * 
 * @author Vlado Pakan
 *
 */
public interface ExpandBarItem extends ReferencedComposite, Widget {
	
	/**
	 * Returns the text of the expand bar item.
	 * 
	 * @return text on the expand bar item
	 */
	String getText();

	/**
	 * Returns the ToolTip text of expand bar item.
	 * 
	 * @return ToolTip text on the expand bar item.
	 */
	String getToolTipText();

	/**
	 * Expands the expand bar item.
	 */
	void expand();

	/**
	 * Expands expand bar item and wait for specific time period.
	 * 
	 * @param timePeriod time period to wait
	 */
	void expand(TimePeriod timePeriod);

	/**
	 * Collapses expand bar item.
	 */
	void collapse();

	/**
	 * Returns encapsulated parent expand bar. Parent expand bar is
	 * Eclipse implementation of expand bar. 
	 * 
	 * @return parent Eclipse expand bar 
	 */
	org.eclipse.swt.widgets.ExpandBar getSWTParent();

	/**
	 * Returns parent expand bar.
	 * 
	 * @return parent RedDeer expand bar
	 */
	ExpandBar getParent();

	/**
	 * Finds out whether expand bar item is collapsed or not.
	 * 
	 * @return true if expand bar item is collapsed, false otherwise
	 */
	boolean isExpanded();

	org.eclipse.swt.widgets.ExpandItem getSWTWidget();
}
