package org.jboss.reddeer.core.matcher;

import org.eclipse.swt.widgets.Widget;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.jboss.reddeer.core.exception.SWTLayerException;
import org.jboss.reddeer.core.handler.WidgetHandler;

/**
 * This class provides a skeletal implementation for all matchers
 * that matches {@link Widget}s with specified {@link String}.<br/>
 * 
 * To implement a matcher matching {@link Widget} with specified string,
 * the programmer needs only to extend this class and provide implementation for
 * the {@link #matches(String)} and {@link #describeTo(Description)} methods:
 * 
 * <ul>
 * <li>Method {@link #matches(String)} is intended to evaluate matching
 * with extracted text that is given as parameter</li>
 * <li>Method {@link #describeTo(Description)} provides description
 * of the matcher</li>
 * <ul>
 * 
 * Method {@link #matches(Object)} is final and can't be overridden
 * because all evaluation of extracted text must be in
 * {@link #matches(String)} method.<br/>
 * 
 * The programmer should not forget to override {@link #toString()} method,
 * which provides information about the matcher.
 * 
 * @author Radoslav Rabara
 * 
 */
public abstract class AbstractWidgetWithTextMatcher extends BaseMatcher<String> {

	/**
	 * Evaluates the matcher for argument <var>item</var>.
	 *  
	 * @param item the object against which the matcher is evaluated
	 * @return <code>true</code> if <var>item</var> is a widget with text
	 * and text matches, otherwise <code>false</code>
	 * 
	 * @see org.hamcrest.BaseMatcher#matches(Object) 
	 */
	@Override
	public final boolean matches(Object item) {
		String text = extractStrings(item);
		if (text == null)
			return false;
		return matches(text);
	}
	
	/**
	 * Evaluates the matcher for <var>text</var> extracted from {@link Widget}.
	 * Argument <var>text</var> is never null.
	 * 
	 * @param text text extracted from {@link Widget}
	 * @return <code>true</code> if <var>text</var> matches, otherwise <code>false</code>
	 */
	protected abstract boolean matches(String text);
	
	private String extractStrings(Object item) {
		if(item instanceof String) {
			return (String) item;
		}
		if (item instanceof Widget) {
			return extractWidgetText((Widget) item);
		}
		return null;
	}
	
	protected String extractWidgetText(Widget widget) {
		try {
			return WidgetHandler.getInstance().getText(widget);
		} catch (SWTLayerException ex) {
			return null;
		}
	}
}
