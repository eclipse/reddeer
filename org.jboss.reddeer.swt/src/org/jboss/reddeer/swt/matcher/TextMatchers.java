package org.jboss.reddeer.swt.matcher;

import org.hamcrest.Matcher;


/**
 * Class for more confortable matchers construction for further usage
 * Usage example: 
 * RegexMatchers m = new TextMatchers("New","Project...");
 * Menu m = new ContextMenu(m.getMatchers());
 * @author Jiri Peterka
 *
 */
public class TextMatchers {


	Matcher<String>[] matchers;

	public TextMatchers(String... texts) {
	
		matchers = new TextMatcher[texts.length];
		for (int i = 0; i < texts.length; i++) {
			matchers[i] = new TextMatcher(texts[i]);		
		}
	
	}
	
	public Matcher<String>[] getMatchers() {
		return matchers;
	}
}
