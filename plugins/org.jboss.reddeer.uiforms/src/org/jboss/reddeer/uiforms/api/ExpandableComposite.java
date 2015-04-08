package org.jboss.reddeer.uiforms.api;

import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.swt.widgets.Widget;

/**
 * Represents {@link org.eclipse.ui.forms.widgets.ExpandableComposite} object. 
 * 
 * @author Radoslav Rabara
 *
 */
public interface ExpandableComposite extends ReferencedComposite, Widget {

	/**
	 * Returns the title string.
	 * @return the title string
	 */
	String getText();

	/**
	 * Sets the expansion state of the composite.
	 * 
	 * @param expanded the new expanded state
	 */
	void setExpanded(boolean expanded);

	/**
	 * Returns the expansion state of the composite.
	 * @return <code>true</code> if expanded, <code>false</code> if collapsed.
	 */
	boolean isExpanded();
}
