package org.jboss.reddeer.core.matcher;

import org.eclipse.swt.widgets.Widget;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;

/**
 * Matcher matching text of {@link Widget}.
 *  
 * @author Jiri Peterka
 * @author Radoslav Rabara
 * 
 */
public class WithTextMatcher extends AbstractWidgetWithTextMatcher {

	private Matcher<String> matcher;
	
	/**
	 * Constructs new WithTextMatcher matching text of {@link Widget} to specified text.
	 * 
	 * @param text text to match text of {@link Widget}
	 * 
	 */
	public WithTextMatcher(String text) {
		this(new IsEqual<String>(text));
	}

	/**
	 * Constructs new WithTextMatcher matching text of {@link Widget} with specified text matcher.
	 * 
	 * @param matcher text matcher to match text of {@link Widget}
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
