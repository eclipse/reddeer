package org.jboss.reddeer.core.reference;

import org.eclipse.swt.widgets.Control;

/**
 * Classes implementing this interface can be used
 * as parent composite widget from which children are located.
 * 
 * @author Jaroslav Jankovic
 * @author Jiri Peterka
 */
public interface ReferencedComposite {
	
	/**
	 * Gets control of referenced composite.
	 * 
	 * @return control
	 */
	Control getControl();
	
}
