package org.jboss.reddeer.swt.matcher;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

/**
 * Regular expression matcher
 * @author Jiri Peterka
 * 
 */

public class RegexMatcher extends BaseMatcher<String> {

	private String regex;
	
	public RegexMatcher(String regex ) {
		this.regex = regex;
	}
	
	@Override
	public void describeTo(Description description) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean matches(Object item) {
		
		if (item instanceof String) {
			String text = (String)item;
			return text.matches(regex);			
		}
		return false;
	}

}
