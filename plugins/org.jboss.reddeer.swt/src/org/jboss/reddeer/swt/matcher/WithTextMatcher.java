package org.jboss.reddeer.swt.matcher;

import org.eclipse.swt.widgets.Widget;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;
import org.jboss.reddeer.swt.handler.WidgetHandler;

/**
 * Matcher matching {@link Widget}s with the specified text.<br/>
 * 
 * It accepts {@link Widget}s supported by {@link WidgetHandler#getText(Widget)}
 * 
 * @author Jiri Peterka
 * @author Radoslav Rabara
 * 
 */
public class WithTextMatcher extends AbstractWidgetWithTextMatcher {

	private Matcher<String> matcher;
	
	/**
	 * Constructs matcher comparing {@link Widget}'s text
	 * with the specified <var>text</var>
	 * 
	 * @param text The {@link String} to compare {@link Widget}'s text against
	 * 
	 */
	public WithTextMatcher(String text) {
		this(new IsEqual<String>(text));
	}

	/**
	 * Constructs matcher matching {@link Widget}'s text
	 * with the specified <var>matcher</var>
	 * 
	 * @param matcher The {@link Matcher<String>} used to evaluate {@link Widget}'s text
	 * 
	 */
	public WithTextMatcher(Matcher<String> matcher) {
		if (matcher == null)
			throw new NullPointerException("matcher is null");
		this.matcher = matcher;
	}
	
	@Override
	protected boolean matches(String text) {
		return matcher.matches(text);
	}
	
	@Override
	public void describeTo(Description description) {
		description.appendText("with text ").appendDescriptionOf(matcher);
	}
	
	@Override
	public String toString() {
		return "Matcher matching widget which text matches: "+matcher.toString();
	}
}
