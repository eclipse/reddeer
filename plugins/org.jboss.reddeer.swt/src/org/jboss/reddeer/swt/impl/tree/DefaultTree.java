package org.jboss.reddeer.swt.impl.tree;

import org.jboss.reddeer.junit.logging.Logger;
import org.jboss.reddeer.swt.lookup.WidgetLookup;
import org.jboss.reddeer.swt.reference.ReferencedComposite;
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
	 * Tree inside given composite
	 * @param referencedComposite
	 */
	public DefaultTree(ReferencedComposite referencedComposite) {
		this(referencedComposite, 0);
	}

	/**
	 * Tree with specified index will be constructed 
	 * 
	 * @param index
	 */
	public DefaultTree(int index) {
	  this(null,index);
	}
	
	
	/**
	 * Tree with specified index will be constructed inside given composite
	 * @param referencedComposite
	 * @param index
	 */
	public DefaultTree(ReferencedComposite referencedComposite, int index) {
	  super(WidgetLookup.getInstance().activeWidget(referencedComposite, org.eclipse.swt.widgets.Tree.class, index));
	}
	
}
