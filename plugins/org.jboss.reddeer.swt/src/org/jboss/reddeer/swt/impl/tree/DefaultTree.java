package org.jboss.reddeer.swt.impl.tree;

import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.reference.ReferencedComposite;
/**
 * Default Tree implementation
 * 
 * @author jjankovi
 *
 */
public class DefaultTree extends AbstractTree {
	
	/**
	 * Tree with index 0
	 */
	public DefaultTree() {
		this((ReferencedComposite) null);
	}
	
	/**
	 * Tree with index 0 inside given composite
	 * @param referencedComposite
	 */
	public DefaultTree(ReferencedComposite referencedComposite){
		this(referencedComposite, 0);
	}
	
	/**
	 * Tree that matches given matchers
	 * @param matchers
	 */
	public DefaultTree(Matcher<?>... matchers) {
		this(null, matchers);
	}
	
	/**
	 * Tree that matches given matchers
	 * @param referencedComposite
	 * @param matchers
	 */
	public DefaultTree(ReferencedComposite referencedComposite, Matcher<?>... matchers) {
		this(referencedComposite, 0, matchers);
	}
	
	/**
	 * Tree with given index that matches given matchers
	 * @param index
	 * @param matchers
	 */
	public DefaultTree(int index, Matcher<?>... matchers) {
		this(null, index, matchers);
	}
	
	/**
	 * Tree with given index inside given composite that matches given matchers
	 * @param referencedComposite
	 * @param index
	 * @param matchers
	 */
	public DefaultTree(ReferencedComposite referencedComposite, int index, Matcher<?>... matchers) {
		super(referencedComposite, index, matchers);
	}
}
