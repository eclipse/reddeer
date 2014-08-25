package org.jboss.reddeer.swt.impl.label;

import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.matcher.WithTextMatcher;
import org.jboss.reddeer.swt.reference.ReferencedComposite;

/**
 * DefaultLabel implementation represents most common Label widget type
 * and provide API for basic operation needed in UI tests
 * @author Jiri Peterka
 *
 */
public class DefaultLabel extends AbstractLabel {

	/**
	 * Label with index 0
	 */
	public DefaultLabel() {
		this((ReferencedComposite) null);
	}
	
	/**
	 * Label with index 0 inside given composite
	 * @param referencedComposite
	 */
	public DefaultLabel(ReferencedComposite referencedComposite){
		this(referencedComposite, 0);
	}
	
	/**
	 * Label with given text
	 * @param text
	 */
	public DefaultLabel(String text) {
		this(null, text);
	}
	
	/**
	 * Label with given text inside given composite
	 * @param referencedComposite
	 * @param text
	 */
	public DefaultLabel(ReferencedComposite referencedComposite, String text) {
		this(referencedComposite, 0, new WithTextMatcher(text));
	}
	
	/**
	 * Label that matches given matchers
	 * @param matchers
	 */
	public DefaultLabel(Matcher<?>... matchers) {
		this(null, matchers);
	}
	
	/**
	 * Label that matches given matchers
	 * @param referencedComposite
	 * @param matchers
	 */
	public DefaultLabel(ReferencedComposite referencedComposite, Matcher<?>... matchers) {
		this(referencedComposite, 0, matchers);
	}
	
	/**
	 * Label with given index that matches given matchers
	 * @param index
	 * @param matchers
	 */
	public DefaultLabel(int index, Matcher<?>... matchers) {
		this(null, index, matchers);
	}
	
	/**
	 * Label with given index inside given composite that matches given matchers
	 * @param referencedComposite
	 * @param index
	 * @param matchers
	 */
	public DefaultLabel(ReferencedComposite referencedComposite, int index, Matcher<?>... matchers) {
		super(referencedComposite, index, matchers);
	}
	
	/**
	 * Create DefaultLabel instance matching given text and index
	 * @param text
	 * @param index
	 * @deprecated Since 1.0.0 this is not a standard widget constructor
	 */
	public DefaultLabel(String text, int index) {
		this(null, text, index);		
	}
	
	/**
	 * Create DefaultLabel instance matching given text and index inside given composite
	 * @param referencedComposite
	 * @param text
	 * @param index
	 * @deprecated Since 1.0.0 this is not a standard widget constructor
	 */
	public DefaultLabel(ReferencedComposite referencedComposite, String text, int index) {
		this(referencedComposite, index, new WithTextMatcher(text));		
	}
}
