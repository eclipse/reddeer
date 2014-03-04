package org.jboss.reddeer.swt.matcher;

import org.hamcrest.Matcher;


/**
 * Class for more comfortable matchers construction for further usage
 * 
 * Usage example: 
 * RegexMatchers m = new RegexMatchers("New.*","Project.*");
 * Menu m = new ContextMenu(m.getMatchers());
 * 
 * @author Jiri Peterka
 * 
 * @deprecated in 0.5, use {@link WithRegexMatchers}
 */
public class RegexMatchers {

	private Matcher<String>[] matchers;

	public RegexMatchers(String... regexes) {
		matchers = new RegexMatcher[regexes.length];
		for (int i = 0; i < regexes.length; i++) {
			matchers[i] = new RegexMatcher(regexes[i]);		
		}
	}
	
	public Matcher<String>[] getMatchers() {
		return matchers;
	}
}
