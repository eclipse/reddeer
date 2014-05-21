package org.jboss.reddeer.gef.impl.editpart;

import org.jboss.reddeer.gef.matcher.IsEditPartOfInstance;

/**
 * EditPart implementation which is looking for a given instance of edit part.
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 *
 */
public class DefaultEditPart extends AbstractEditPart {

	/**
	 * Finds an edit part with a given instance name.
	 * 
	 * @param label
	 *            Instance name
	 */
	public DefaultEditPart(String instance) {
		this(instance, 0);
	}

	/**
	 * Finds an edit part with a given instance name at the specified index.
	 * 
	 * @param instance
	 *            Instance name
	 * @param index
	 *            Index
	 */
	public DefaultEditPart(String instance, int index) {
		super(new IsEditPartOfInstance(instance), index);
	}

}
