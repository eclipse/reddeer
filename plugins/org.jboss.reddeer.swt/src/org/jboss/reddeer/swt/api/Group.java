package org.jboss.reddeer.swt.api;

import org.jboss.reddeer.swt.reference.ReferencedComposite;

/**
 * API For Group manipulation
 * @author Jiri Peterka
 *
 */
public interface Group extends ReferencedComposite{
	
	/**
	 * @since 0.4
	 * @return Text of group
	 */
	String getText();

}
