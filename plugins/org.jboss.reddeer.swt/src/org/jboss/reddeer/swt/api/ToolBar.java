package org.jboss.reddeer.swt.api;

import org.jboss.reddeer.swt.reference.ReferencedComposite;
import org.jboss.reddeer.swt.widgets.Widget;

/**
 * API for tool bar manipulation.
 * 
 * @author Jiri Peterka
 *
 */
public interface ToolBar extends Widget, ReferencedComposite{
	
	org.eclipse.swt.widgets.ToolBar getSWTWidget();

}
