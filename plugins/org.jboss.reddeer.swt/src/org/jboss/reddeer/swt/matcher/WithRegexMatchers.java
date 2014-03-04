package org.jboss.reddeer.swt.matcher;

import org.hamcrest.Matcher;


/**
 * Class for more comfortable matchers construction for further usage<br/>
 * 
 * Usage example:
 * <code> 
 * RegexMatchers m = new RegexMatchers("New.*","Project.*");
 * Menu m = new ContextMenu(m.getMatchers());
 * </code>
 * 
 * @author Jiri Peterka
 * @author Radoslav Rabara
 */
public class WithRegexMatchers {

	private Matcher<String>[] matchers;

	/**
	 * Constructs holder for array of {@link WithRegexMatcher}
	 * created from strings <var>regexes</var>
	 * 
	 * @param regexes
	 *            regular expressions that will be passed to
	 *            {@link WithRegexMatcher#WithRegexMatcher(String)

	 */
	public WithRegexMatchers(String... regexes) {
		if(regexes == null)
			throw new NullPointerException("regexes");
		matchers = new WithRegexMatcher[regexes.length];
		for (int i = 0; i < regexes.length; i++) {
			matchers[i] = new WithRegexMatcher(regexes[i]);		
		}
	}
	
	/**
	 * Get all matchers
	 * @return returns all matchers
	 */
	public Matcher<String>[] getMatchers() {
		return matchers;
	}
}
