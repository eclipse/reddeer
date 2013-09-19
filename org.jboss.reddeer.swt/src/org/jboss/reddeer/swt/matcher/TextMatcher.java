package org.jboss.reddeer.swt.matcher;

import org.eclipse.swt.widgets.Widget;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.handler.WidgetHandler;

/**
 * String matcher
 * @author Jiri Peterka
 * 
 */
public class TextMatcher extends BaseMatcher<String> {

	private String text;
	
	/**
	 * Creates text matcher
	 * @param text given text for matcher usage
	 */
	public TextMatcher(String text ) {
		this.text = text;
	}
	
	@Override
	public void describeTo(Description description) {
		// TODO Auto-generated method stub

	}

	/**
	 * Matches given object
	 * @returns true if object is matching given text
	 */
	@Override
	public boolean matches(Object item) {
		
		if (item instanceof String) {
			String textToMatch = (String)item;
			if (textToMatch.equals(text)) {
				return true;
			}
		} else  if (item instanceof Widget) {
			try {
				String widgetText = WidgetHandler.getInstance().getText((Widget)item);
				if (widgetText != null){
					return widgetText.equals(text); 
				}
				else{
					return text == null;
				}					
			} catch (SWTLayerException sle) {
				// object is not supported by widget handler mechanism 'getText' 
			}
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		return "Matcher matching text \"" + text +"\"";
	}

}
