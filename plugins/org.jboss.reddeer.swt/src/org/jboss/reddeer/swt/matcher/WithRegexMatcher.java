package org.jboss.reddeer.swt.matcher;

import org.hamcrest.Description;

/**
 * Matches widget with text that matches given regular expression.
 * 
 * @author Jiri Peterka
 * @author Radoslav Rabara
 * 
 * @deprecated in 0.6. Use plain {@link RegexMatcher} in combination with some
 *             other matcher (for example {@link WithTextMatcher} or
 *             {@link WithTooltipTextMatcher}).
 * 
 */
public class WithRegexMatcher extends AbstractWidgetWithTextMatcher {

	private String regex;
	
	/**
	 * Constructs matcher matching widget with text which matches
	 * specified <var>regex</var>
	 * 
	 * @param regex regular expression
	 */
	public WithRegexMatcher(String regex) {
		if(regex == null)
			throw new NullPointerException("regex");
		this.regex = regex;
	}	

	@Override
	protected boolean matches(String text) {
		return text.matches(regex);
	}
	
	@Override
	public void describeTo(Description description) {
		description.appendText("has text matching regex \""+regex+"\"");
	}
	
	@Override
	public String toString() {
		return "Matcher matching widget which text matches regex: "+regex;
	}
}
