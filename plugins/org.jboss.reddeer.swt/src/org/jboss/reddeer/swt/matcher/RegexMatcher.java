package org.jboss.reddeer.swt.matcher;

import org.eclipse.swt.widgets.Widget;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.jboss.reddeer.swt.handler.WidgetHandler;

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

		String text;
		if (item instanceof Widget) {
			text = WidgetHandler.getInstance().getText((Widget)item);
		}
		
		else if (item instanceof String) {
			text = (String)item;		
		} 
		else {
			return false;
		}
			
		
		return text.matches(regex);
	}

}
