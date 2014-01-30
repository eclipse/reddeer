package org.jboss.reddeer.swt.api;

import org.jboss.reddeer.swt.reference.ReferencedComposite;
import org.jboss.reddeer.swt.widgets.Widget;

/**
 * API For Group manipulation
 * @author Jiri Peterka
 *
 */
public interface Group extends ReferencedComposite, Widget{
	
	/**
	 * @since 0.4
	 * @return Text of group
	 */
	String getText();
	
	org.eclipse.swt.widgets.Group getSWTWidget();

}
