package org.jboss.reddeer.swt.reference;

import org.eclipse.swt.widgets.Control;

/**
 * Classes implementing this interface can be used
 * as composite widget parent from which children are
 * located
 * @author Jaroslav Jankovic
 * @author Jiri Peterka
 * @deprecated since 0.8, use {@link #org.jboss.reddeer.core.reference}
 */
@Deprecated
public interface ReferencedComposite {
	
	Control getControl();
	
}
