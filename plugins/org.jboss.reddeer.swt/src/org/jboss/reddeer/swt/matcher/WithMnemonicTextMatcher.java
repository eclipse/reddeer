package org.jboss.reddeer.swt.matcher;

import org.eclipse.swt.widgets.Widget;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.core.Is;

/**
 * Provides safe {@link Widget} matching with text that contains Mnemonic.<br/>
 * 
 * Removes all ampersands and shortcuts from input text before comparing.
 * Should be used for menu item label matching.
 * 
 * @author Vlado Pakan
 * @author Radoslav Rabara
 * @deprecated since 0.8, use {@link #org.jboss.reddeer.core.matcher.WithMnemonicTextMatcher}
 */
@Deprecated
public class WithMnemonicTextMatcher extends AbstractWidgetWithTextMatcher {

	private Matcher<String> matcher;
	
	/**
	 * Constructs matcher comparing {@link Widget}'s text without Mnemonic with
	 * the specified <var>text</var>
	 * 
	 * @param text
	 *            The {@link String} to compare {@link Widget}'s text without
	 *            Mnemonic against
	 */
	public WithMnemonicTextMatcher(String text) {
		this(Is.is(text));
	}
	
	/**
	 * Constructs matcher matching {@link Widget}'s text without Mnemonic with
	 * the specified <var>matcher</var>
	 * 
	 * @param matcher
	 *            The {@link Matcher<String>} used to evaluate {@link Widget}'s text
	 *            without Mnemonic
	 */
	public WithMnemonicTextMatcher(Matcher<String> matcher) {
		if (matcher == null)
			throw new NullPointerException("matcher is null");
		this.matcher = matcher;
	}
	
	@Override
	protected boolean matches(String extractedText) {
		String textToMatch = extractedText.replaceAll("&", "").split("\t")[0];
		return matcher.matches(textToMatch);
	}
	
	@Override
	public void describeTo(Description description) {
		description.appendText("has text that without mnenomic ").appendDescriptionOf(matcher);
	}

	@Override
	public String toString() {
		return "Matcher matching widgets with text that without mnenomic matches: " + matcher;
	}
}
