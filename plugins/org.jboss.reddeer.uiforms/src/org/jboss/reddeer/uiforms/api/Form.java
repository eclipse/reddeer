package org.jboss.reddeer.uiforms.api;

import org.jboss.reddeer.swt.reference.ReferencedComposite;
import org.jboss.reddeer.swt.widgets.Widget;

/**
 * Represents Eclipse Form. This class will be used mainly for its children discovering
 * 
 * @author Lucia Jelinkova
 *
 */
public interface Form extends ReferencedComposite, Widget {

	/**
	 * Returns form's title. 
	 * @return
	 */
	String getText();
}
