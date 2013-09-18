package org.jboss.reddeer.swt.impl.expandbar;

import org.jboss.reddeer.swt.lookup.ExpandBarLookup;
import org.jboss.reddeer.swt.reference.ReferencedComposite;

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
	 * ExpandBar inside given composite
	 * @param referencedComposite
	 */
	public DefaultExpandBar(ReferencedComposite referencedComposite) {
		this(referencedComposite, 0);
	}
	
	/**
	 * Expand Bar with specified index will be constructed 
	 * 
	 * @param index
	 */
	public DefaultExpandBar(int index) {
		super(ExpandBarLookup.getInstance().getExpandBar(null, index));
	}
	
	/**
	 * Expand Bar with specified index inside given composite will be constructed 
	 * @param referencedComposite
	 * @param index
	 */
	public DefaultExpandBar(ReferencedComposite referencedComposite, int index) {
		super(ExpandBarLookup.getInstance().getExpandBar(referencedComposite, index));
	}
}
