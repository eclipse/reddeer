package org.jboss.reddeer.swt.impl.expandbar;

import org.jboss.reddeer.swt.lookup.impl.ExpandBarLookup;

/**
 * Default Expand Bar implementation
 * @author Vlado Pakan
 *
 */
public class DefaultExpandBar extends AbstractExpandBar {

	/**
	 * Default parameter-less constructor
	 */
	public DefaultExpandBar() {
		this(0);
	}
	/**
	 * Expand Bar with specified index will be constructed 
	 * 
	 * @param index
	 */
	public DefaultExpandBar(int index) {
		super(ExpandBarLookup.getInstance().getExpandBar(index));
	}
}
