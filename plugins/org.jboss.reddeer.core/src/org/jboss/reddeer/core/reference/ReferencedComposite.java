package org.jboss.reddeer.core.reference;

import org.eclipse.swt.widgets.Control;

/**
 * Classes implementing this interface can be used
 * as composite widget parent from which children are
 * located
 * @author Jaroslav Jankovic
 * @author Jiri Peterka
 */
public interface ReferencedComposite {
	
	Control getControl();
	
}
