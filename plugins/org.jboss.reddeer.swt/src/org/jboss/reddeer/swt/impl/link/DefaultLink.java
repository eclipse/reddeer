package org.jboss.reddeer.swt.impl.link;

import org.hamcrest.Matcher;
import org.jboss.reddeer.core.matcher.LinkTextMatcher;
import org.jboss.reddeer.core.reference.ReferencedComposite;

public class DefaultLink extends AbstractLink{
	
	/**
	 * Link with index 0
	 */
	public DefaultLink() {
		this((ReferencedComposite) null);
	}
	
	/**
	 * Link with index 0 inside given composite
	 * @param referencedComposite
	 */
	public DefaultLink(ReferencedComposite referencedComposite){
		this(referencedComposite, 0);
	}
	
	/**
	 * Link with given text
	 * @param text
	 */
	public DefaultLink(String text) {
		this(null, text);
	}
	
	/**
	 * Link with given text inside given composite
	 * @param referencedComposite
	 * @param text
	 */
	public DefaultLink(ReferencedComposite referencedComposite, String text) {
		this(referencedComposite, 0, new LinkTextMatcher(text));
	}
	
	/**
	 * Link that matches given matchers
	 * @param matchers
	 */
	public DefaultLink(Matcher<?>... matchers) {
		this(null, matchers);
	}
	
	/**
	 * Link that matches given matchers
	 * @param referencedComposite
	 * @param matchers
	 */
	public DefaultLink(ReferencedComposite referencedComposite, Matcher<?>... matchers) {
		this(referencedComposite, 0, matchers);
	}
	
	/**
	 * Link with given index that matches given matchers
	 * @param index
	 * @param matchers
	 */
	public DefaultLink(int index, Matcher<?>... matchers) {
		this(null, index, matchers);
	}
	
	/**
	 * Link with given index inside given composite that matches given matchers
	 * @param referencedComposite
	 * @param index
	 * @param matchers
	 */
	public DefaultLink(ReferencedComposite referencedComposite, int index, Matcher<?>... matchers) {
		super(referencedComposite, index, matchers);
	}
	
	/**
	 * Link with given text and index inside given composite
	 * @param referencedComposite
	 * @param index of link
	 * @param text of link
	 *  @deprecated Since 1.0.0 this is not a standard widget constructor
	 */
	public DefaultLink(ReferencedComposite referencedComposite, int index, String text){
		this(referencedComposite, index, new LinkTextMatcher(text));
	}
	
	/**
	 * Link with given text and index
	 * @param index of link
	 * @param text of link
	 *  @deprecated Since 1.0.0 this is not a standard widget constructor
	 */
	public DefaultLink(int index, String text){
		this(null, index, text);
	}

}
