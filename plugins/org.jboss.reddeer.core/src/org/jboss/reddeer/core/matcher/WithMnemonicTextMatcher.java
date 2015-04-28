package org.jboss.reddeer.core.matcher;

import org.eclipse.swt.widgets.Widget;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.core.Is;

/**
 * Matcher matching mnemonic text of widgets.<br/>
 * 
 * Removes all ampersands and shortcuts from input text before matching.
 * Should be used for menu item label matching.
 * 
 * @author Vlado Pakan
 * @author Radoslav Rabara
 * 
 */
public class WithMnemonicTextMatcher extends AbstractWidgetWithTextMatcher {

	private Matcher<String> matcher;
	
	/**
	 * Constructs new WithMnemonicTextMatcher matcher matching mnemonic text of {@link Widget} 
	 * to specified text.
	 * 
	 * @param text text to match text of widget without mnemonic
	 */
	public WithMnemonicTextMatcher(String text) {
		this(Is.is(text));
	}
	
	/**
	 * Constructs new WithMnemonicTextMatcher matcher matching mnemonic text of {@link Widget} 
	 * to specified text matcher.
	 * 
	 * @param matcher text matcher to match text of widget without mnemonic
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
