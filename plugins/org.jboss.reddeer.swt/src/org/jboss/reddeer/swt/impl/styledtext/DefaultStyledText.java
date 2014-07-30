package org.jboss.reddeer.swt.impl.styledtext;

import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.api.StyledText;
import org.jboss.reddeer.swt.lookup.StyledTextLookup;
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
	 * StyledText.
	 */
	public DefaultStyledText() {
		styledText = StyledTextLookup.getInstance().getStyledText(null, 0);
	}
	
	/**
	 * StyledText inside given composite.
	 * @param referencedComposite
	 */
	public DefaultStyledText(final ReferencedComposite referencedComposite) {
		styledText = StyledTextLookup.getInstance().getStyledText(referencedComposite, 0);
	}

	/**
	 * StyledText on given index.
	 * 
	 * @param index
	 */
	public DefaultStyledText(final int index) {
		styledText = StyledTextLookup.getInstance().getStyledText(null, index);
	}
	
	/**
	 * StyledText on given index inside given composite.
	 * @param referencedComposite
	 * @param index
	 */
	public DefaultStyledText(final ReferencedComposite referencedComposite, final int index) {
		styledText = StyledTextLookup.getInstance().getStyledText(referencedComposite, index);
	}

	/**
	 * StyledText with given text.
	 * 
	 * @param text
	 */
	public DefaultStyledText(final String text) {
		styledText = StyledTextLookup.getInstance().getStyledText(null, 0, new WithTextMatcher(text));
	}
	
	/**
	 * StyledText with given text inside given composite.
	 * @param referencedComposite
	 * @param text
	 */
	public DefaultStyledText(final ReferencedComposite referencedComposite, final String text) {
		styledText = StyledTextLookup.getInstance().getStyledText(referencedComposite, 0, new WithTextMatcher(text));
	}

	/**
	 * StyledText matching given matchers.
	 * 
	 * @param matchers
	 */
	@SuppressWarnings("rawtypes")
	public DefaultStyledText(final Matcher... matchers) {
		styledText = StyledTextLookup.getInstance().getStyledText(null, 0, matchers);
	}
	
	/**
	 * StyledText matching given matchers.
	 * 
	 * @param matchers
	 */
	@SuppressWarnings("rawtypes")
	public DefaultStyledText(final ReferencedComposite referencedComposite, final Matcher... matchers) {
		styledText = StyledTextLookup.getInstance().getStyledText(referencedComposite, 0, matchers);
	}
}
