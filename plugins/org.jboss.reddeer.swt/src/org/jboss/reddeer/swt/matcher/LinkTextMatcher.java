package org.jboss.reddeer.swt.matcher;

import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Widget;
import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.handler.LinkHandler;

/**
 * Matcher matching {@link Link} with specified text. 
 * 
 * It is implemented as separated matcher since the retrieving of link's text is different to other widgets. 
 * @author Lucia Jelinkova
 *
 */
public class LinkTextMatcher extends WithTextMatcher {
	

	/**
	 * Matches link with given text.
	 * @param text Text to match.
	 */
	
	public LinkTextMatcher(String text) {
		super(text);
	}

	/**
	 * Matches link with given matcher.
	 * @param matcher Matcher to use against link text.
	 */
	
	public LinkTextMatcher(Matcher<String> matcher) {
		super(matcher);
	}
	
	@Override
	protected String extractWidgetText(Widget widget) {
		if (widget instanceof Link){
			return LinkHandler.getInstance().getText((Link) widget);	
		}
		return null;
	}
}
