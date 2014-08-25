package org.jboss.reddeer.swt.impl.styledtext;

import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.api.StyledText;
import org.jboss.reddeer.swt.matcher.WithTextMatcher;
import org.jboss.reddeer.swt.reference.ReferencedComposite;

/**
 * Default class for representing StyledText.
 * 
 * @author rhopp, rawagner
 * 
 */
public class DefaultStyledText extends AbstractStyledText implements StyledText {
	
	/**
	 * StyledText with index 0
	 */
	public DefaultStyledText() {
		this((ReferencedComposite) null);
	}
	
	/**
	 * StyledText inside given composite.
	 * @param referencedComposite
	 */
	public DefaultStyledText(final ReferencedComposite referencedComposite) {
		this(referencedComposite, 0);
	}

	/**
	 * StyledText with given text.
	 * 
	 * @param text
	 */
	public DefaultStyledText(final String text) {
		this(null, text);
	}
	
	/**
	 * StyledText with given text inside given composite.
	 * @param referencedComposite
	 * @param text
	 */
	public DefaultStyledText(final ReferencedComposite referencedComposite, final String text) {
		this(referencedComposite, 0, new WithTextMatcher(text));
	}

	/**
	 * StyledText matching given matchers.
	 * 
	 * @param matchers
	 */
	public DefaultStyledText(final Matcher<?>... matchers) {
		this(null, matchers);
	}
	
	/**
	 * StyledText matching given matchers inside given composite
	 * 
	 * @param matchers
	 */
	public DefaultStyledText(final ReferencedComposite referencedComposite, final Matcher<?>... matchers) {
		this(referencedComposite, 0, matchers);
	}
	
	/**
	 * StyledText with given index that matches given matchers
	 * 
	 * @param index
	 */
	public DefaultStyledText(int index, Matcher<?>... matchers) {
		this(null, index, matchers);
	}
	
	/**
	 * StyledText with given index that matches given matchers inside given composite
	 * @param referencedComposite
	 * @param index
	 */
	public DefaultStyledText(ReferencedComposite referencedComposite, int index, Matcher<?>... matchers) {
		super(referencedComposite, index, matchers);
	}
}
