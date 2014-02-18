package org.jboss.reddeer.swt.impl.tab;

import org.jboss.reddeer.swt.lookup.TabFolderLookup;
import org.jboss.reddeer.swt.reference.ReferencedComposite;

/**
 * Default TabFolder implementation
 * 
 * @author Andrej Podhradsky
 * 
 */
public class DefaultTabFolder extends AbstractTabFolder {

	/**
	 * Default parameter-less constructor
	 */
	public DefaultTabFolder() {
		this(0);
	}

	/**
	 * TabFolder with specified index will be constructed
	 * 
	 * @param index
	 */
	public DefaultTabFolder(int index) {
		this(null, index);
	}

	/**
	 * TabFolder inside given composite
	 * 
	 * @param referencedComposite
	 */
	public DefaultTabFolder(ReferencedComposite referencedComposite) {
		this(referencedComposite, 0);
	}

	/**
	 * TabFolder inside a given composite and at specified index
	 * 
	 * @param referencedComposite
	 * @param index
	 */
	public DefaultTabFolder(ReferencedComposite referencedComposite, int index) {
		super(TabFolderLookup.getInstance().getTabFolder(referencedComposite, index));
	}
}
