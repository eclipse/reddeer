package org.jboss.reddeer.swt.impl.tab;

import org.hamcrest.Matcher;
import org.jboss.reddeer.core.reference.ReferencedComposite;

/**
 * Default TabFolder implementation
 * 
 * @author Andrej Podhradsky
 * 
 */
public class DefaultTabFolder extends AbstractTabFolder {

	/**
	 * TabFolder with index 0
	 */
	public DefaultTabFolder() {
		this((ReferencedComposite) null);
	}
	
	/**
	 * TabFolder with index 0 inside given composite
	 * @param referencedComposite
	 */
	public DefaultTabFolder(ReferencedComposite referencedComposite){
		this(referencedComposite, 0);
	}
	
	/**
	 * TabFolder that matches given matchers
	 * @param matchers
	 */
	public DefaultTabFolder(Matcher<?>... matchers) {
		this(null, matchers);
	}
	
	/**
	 * TabFolder that matches given matchers
	 * @param referencedComposite
	 * @param matchers
	 */
	public DefaultTabFolder(ReferencedComposite referencedComposite, Matcher<?>... matchers) {
		this(referencedComposite, 0, matchers);
	}
	
	/**
	 * TabFolder with given index that matches given matchers
	 * @param index
	 * @param matchers
	 */
	public DefaultTabFolder(int index, Matcher<?>... matchers) {
		this(null, index, matchers);
	}
	
	/**
	 * TabFolder with given index inside given composite that matches given matchers
	 * @param referencedComposite
	 * @param index
	 * @param matchers
	 */
	public DefaultTabFolder(ReferencedComposite referencedComposite, int index, Matcher<?>... matchers) {
		super(referencedComposite, index, matchers);
	}
}
