package org.jboss.reddeer.swt.matcher;

import org.eclipse.swt.widgets.Button;
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
 * Matches {@link Widget} with described label.
 * 
 * @author Rastislav Wagner
 * @author Radoslav Rabara
 * 
 */
public class WithLabelMatcher extends BaseMatcher<String> {

	private Matcher<String> matcher;
	
	/**
	 * Constructs matcher that matches {@link Widget}
	 * with label which text is equal to the specified <var>text</var>
	 * 
	 * @param text The {@link String} to compare {@link Widget}'s label against
	 * 
	 */
	public WithLabelMatcher(String text) {
		this(Is.<String>is(text));
	}
	
	/**
	 * Constructs matcher that matches {@link Widget}
	 * with label which text is matched by given matcher
	 * 
	 * @param matcher The {@link Matcher<String>} used to evaluate {@link Widget}'s label
	 * 
	 */
	public WithLabelMatcher(Matcher<String> matcher) {
		if(matcher == null)
			throw new NullPointerException("matcher");
		this.matcher = matcher;
	}
	
	@Override
	public void describeTo(Description description) {
		description.appendText("with label ").appendDescriptionOf(matcher);
	}

	/**
	 * Matches given object
	 * 
	 * @returns true if object's label is matching
	 * 
	 */
	@Override
	public boolean matches(Object item) {		
		if ((item instanceof List) || (item instanceof Text) || (item instanceof Button)
				|| (item instanceof Combo) || (item instanceof Spinner)) {
			String widgetLabel = WidgetHandler.getInstance().getLabel((Widget)item);
			if (widgetLabel != null && matcher.matches(widgetLabel)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "Matcher matching widget with label:\n" + matcher.toString();
	}
}
