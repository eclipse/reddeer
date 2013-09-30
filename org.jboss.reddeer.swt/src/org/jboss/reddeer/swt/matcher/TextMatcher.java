package org.jboss.reddeer.swt.matcher;

import org.eclipse.swt.widgets.Widget;
import org.hamcrest.core.IsEqual;
import org.jboss.reddeer.swt.handler.WidgetHandler;

/**
 * String matcher
 * <br/> It accepts Strings and {@link Widget}s supported by {@link WidgetHandler#getText(Widget)}
 * @author Jiri Peterka
 * @author jniederm
 * 
 */
public class TextMatcher extends ObjectToStingMatcherDecorator {

	public TextMatcher(String text) {
		super(new IsEqual<String>(text));
	}
}
