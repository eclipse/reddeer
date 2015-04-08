package org.jboss.reddeer.swt.impl.clabel;

import org.hamcrest.Matcher;
import org.jboss.reddeer.core.matcher.WithTextMatcher;
import org.jboss.reddeer.core.reference.ReferencedComposite;

/**
 * Implements default CLabel widget
 * @author Jiri Peterka, Vlado Pakan, Lucia Jelinkova
 *
 */
public class DefaultCLabel extends AbstractCLabel {
	
	/**
	 * CLabel with index 0
	 */
	public DefaultCLabel() {
		this((ReferencedComposite) null);
	}
	
	/**
	 * CLabel with index 0 inside given composite
	 * @param referencedComposite
	 */
	public DefaultCLabel(ReferencedComposite referencedComposite){
		this(referencedComposite, 0);
	}
	
	/**
	 * CLabel with given text
	 * @param text
	 */
	public DefaultCLabel(String text) {
		this(null, text);
	}
	
	/**
	 * CLabel with given text inside given composite
	 * @param referencedComposite
	 * @param text
	 */
	public DefaultCLabel(ReferencedComposite referencedComposite, String text) {
		this(referencedComposite, 0, new WithTextMatcher(text));
	}
	
	/**
	 * CLabel that matches given matchers
	 * @param matchers
	 */
	public DefaultCLabel(Matcher<?>... matchers) {
		this(null, matchers);
	}
	
	/**
	 * CLabel that matches given matchers
	 * @param referencedComposite
	 * @param matchers
	 */
	public DefaultCLabel(ReferencedComposite referencedComposite, Matcher<?>... matchers) {
		this(referencedComposite, 0, matchers);
	}
	
	/**
	 * CLabel with given index that matches given matchers
	 * @param index
	 * @param matchers
	 */
	public DefaultCLabel(int index, Matcher<?>... matchers) {
		this(null, index, matchers);
	}
	
	/**
	 * CLabel with given index inside given composite that matches given matchers
	 * @param referencedComposite
	 * @param index
	 * @param matchers
	 */
	public DefaultCLabel(ReferencedComposite referencedComposite, int index, Matcher<?>... matchers) {
		super(referencedComposite, index, matchers);
	}
}
