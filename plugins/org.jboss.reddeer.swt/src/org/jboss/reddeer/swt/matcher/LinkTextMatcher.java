package org.jboss.reddeer.swt.matcher;

import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Widget;
import org.jboss.reddeer.swt.handler.LinkHandler;

/**
 * Matcher matching {@link Link} with specified text. 
 * 
 * It is implemented as separated matcher since the retrieving of link's text is different to other widgets. 
 * @author Lucia Jelinkova
 *
 */
public class LinkTextMatcher extends WithTextMatcher {

	public LinkTextMatcher(String text) {
		super(text);
	}

	@Override
	protected String extractWidgetText(Widget widget) {
		if (widget instanceof Link){
			return LinkHandler.getInstance().getText((Link) widget);	
		}
		return null;
	}
}
