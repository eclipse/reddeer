package org.jboss.reddeer.swt.impl.link;

import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.api.Link;
import org.jboss.reddeer.core.matcher.AnchorLinkTextMatcher;
import org.jboss.reddeer.core.reference.ReferencedComposite;

public class AnchorLink extends AbstractLink implements Link{
	
	/**
	 * Link with index 0
	 */
	public AnchorLink() {
		this((ReferencedComposite) null);
	}
	
	/**
	 * Link with index 0 inside given composite
	 * @param referencedComposite
	 */
	public AnchorLink(ReferencedComposite referencedComposite){
		this(referencedComposite, 0);
	}
	
	/**
	 * Link with given text
	 * @param text
	 */
	public AnchorLink(String text) {
		this(null, text);
	}
	
	/**
	 * Link with given text inside given composite
	 * @param referencedComposite
	 * @param text
	 */
	public AnchorLink(ReferencedComposite referencedComposite, String text) {
		this(referencedComposite, 0, new AnchorLinkTextMatcher(text));
	}
	
	/**
	 * Link that matches given matchers
	 * @param matchers
	 */
	public AnchorLink(Matcher<?>... matchers) {
		this(null, matchers);
	}
	
	/**
	 * Link that matches given matchers
	 * @param referencedComposite
	 * @param matchers
	 */
	public AnchorLink(ReferencedComposite referencedComposite, Matcher<?>... matchers) {
		this(referencedComposite, 0, matchers);
	}
	
	/**
	 * Link with given index that matches given matchers
	 * @param index
	 * @param matchers
	 */
	public AnchorLink(int index, Matcher<?>... matchers) {
		this(null, index, matchers);
	}
	
	/**
	 * Link with given index inside given composite that matches given matchers
	 * @param referencedComposite
	 * @param index
	 * @param matchers
	 */
	public AnchorLink(ReferencedComposite referencedComposite, int index, Matcher<?>... matchers) {
		super(referencedComposite, index, matchers);
	}
}
