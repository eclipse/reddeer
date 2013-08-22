package org.jboss.reddeer.swt.impl.tree;

import org.apache.log4j.Logger;
import org.jboss.reddeer.swt.lookup.WidgetLookup;
/**
 * Default Tree implementation
 * 
 * @author jjankovi
 *
 */
public class DefaultTree extends AbstractTree {

	protected final Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * Default parameter-less constructor
	 */
	public DefaultTree() {
		this(0);
	}

	/**
	 * Tree with specified index will be constructed 
	 * 
	 * @param index
	 */
	public DefaultTree(int index) {
	  super(WidgetLookup.getInstance().activeWidget(org.eclipse.swt.widgets.Tree.class, index));
	}
	
}
