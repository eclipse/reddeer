package org.jboss.reddeer.swt.matcher;

import org.eclipse.swt.widgets.Widget;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.handler.WidgetHandler;

/**
 * It allows string matchers to be used on {@link String}s and {@link Widget}s in uniform way.
 * 
 * @author jniederm
 */
public class ObjectToStingMatcherDecorator extends BaseMatcher<String> {
	
	private final Matcher<String> innerMatcher;

	public ObjectToStingMatcherDecorator(Matcher<String> innerMatcher) {
		this.innerMatcher = innerMatcher;
	}

	@Override
	public boolean matches(Object item) {
		Object stringExtractedItem = extractStringsIfPossible(item);
		boolean matches = innerMatcher.matches(stringExtractedItem);
		return matches;
	}

	@Override
	public void describeTo(Description description) {
		innerMatcher.describeTo(description);
	}

	private Object extractStringsIfPossible(Object item) {
		if (item instanceof Widget) {
			return extractWidgetText((Widget) item);
		}
		return item;
	}
	
	private Object extractWidgetText(Widget widget) {
		try {
			return WidgetHandler.getInstance().getText(widget);
		} catch (SWTLayerException ex) {
			return widget;
		}
	}
}