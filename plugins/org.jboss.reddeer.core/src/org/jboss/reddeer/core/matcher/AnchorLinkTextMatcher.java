package org.jboss.reddeer.core.matcher;

import java.util.List;

import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Widget;
import org.jboss.reddeer.core.handler.LinkHandler;
import org.jboss.reddeer.core.matcher.WithTextMatcher;

/**
 * Matcher matching {@link Link} with specified anchor text. 
 *  
 * @author rawagner
 *
 */
public class AnchorLinkTextMatcher extends WithTextMatcher {
	
	private String text;
	
	public AnchorLinkTextMatcher(String text) {
		super(text);
		this.text=text;
	}

	@Override
	protected String extractWidgetText(Widget widget) {
		if (widget instanceof Link){
				List<String> links = LinkHandler.getInstance().getAnchorTexts((Link) widget);
				if(links.size() > 0){
					for(String t: LinkHandler.getInstance().getAnchorTexts((Link) widget)){
						if(text.equals(t)){
							return t; 
						}
					}
				}
		}
		return null;
	}
}