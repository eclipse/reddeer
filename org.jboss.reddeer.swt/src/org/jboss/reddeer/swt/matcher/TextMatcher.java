package org.jboss.reddeer.swt.matcher;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

/**
 * String matcher
 * @author Jiri Peterka
 * 
 */
public class TextMatcher extends BaseMatcher<String> {

	private String text;
	
	public TextMatcher(String text ) {
		this.text = text;
	}
	
	@Override
	public void describeTo(Description description) {
		// TODO Auto-generated method stub

	}

	/**
	 * Text matches
	 */
	@Override
	public boolean matches(Object item) {
		
		if (item instanceof String) {
			String textToMatch = (String)item;
			if (textToMatch.equals(text)) {
				return true;
			}
			
		}
		return false;
	}

}
