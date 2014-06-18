package org.jboss.reddeer.swt.api;

import org.jboss.reddeer.swt.reference.ReferencedComposite;
import org.jboss.reddeer.swt.widgets.Widget;

/**
 * API for group manipulation.
 * 
 * @author Jiri Peterka
 *
 */
public interface Group extends ReferencedComposite, Widget {
	
	/**
	 * Gets text of the group.
	 * 
	 * @since 0.4
	 * @return text of the group
	 */
	String getText();
	
	org.eclipse.swt.widgets.Group getSWTWidget();

}
