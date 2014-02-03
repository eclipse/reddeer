package org.jboss.reddeer.swt.matcher;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.core.Is;
import org.jboss.reddeer.swt.handler.WidgetHandler;

/**
 * Label matcher
 * @author Rastislav Wagner, Radoslav Rabara
 * 
 */
public class LabelMatcher extends BaseMatcher<String> {

	private Matcher<String> matcher;
	
	/**
	 * Creates label matcher
	 * @param label given label for matcher usage
	 */
	public LabelMatcher(String label) {
		this(Is.<String>is(label));
	}
	
	/**
	 * Creates label matcher from string matcher
	 * @param matcher given matcher used to match label
	 */
	public LabelMatcher(Matcher<String> matcher) {
		if(matcher == null)
			throw new NullPointerException("matcher");
		this.matcher = matcher;
	}
	
	@Override
	public void describeTo(Description description) {
		description.appendText("label ").appendDescriptionOf(matcher);
	}

	/**
	 * Matches given object
	 * @returns true if object's label is matching
	 */
	@Override
	public boolean matches(Object item) {
		
		if ((item instanceof List) || (item instanceof Text) || (item instanceof Combo) || (item instanceof Spinner)) {
			String widgetLabel = WidgetHandler.getInstance().getLabel((Widget)item);
			if (widgetLabel != null && matcher.matches(widgetLabel)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "Matcher matching label:\n" + matcher.toString();
	}
	
}
