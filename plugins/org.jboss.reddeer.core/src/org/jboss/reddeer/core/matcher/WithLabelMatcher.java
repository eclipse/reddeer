package org.jboss.reddeer.core.matcher;

import org.eclipse.swt.custom.CCombo;
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
import org.jboss.reddeer.core.handler.WidgetHandler;

/**
 * Matcher matching text to label of {@link Widget}.
 * 
 * @author Rastislav Wagner
 * @author Radoslav Rabara
 * 
 */
public class WithLabelMatcher extends BaseMatcher<String> {

	private Matcher<String> matcher;
	
	/**
	 * Constructs new WithLabelMatcher matching specified text to label of {@link Widget}
	 * on exact match.
	 * 
	 * @param text text to match label
	 * 
	 */
	public WithLabelMatcher(String text) {
		this(Is.<String>is(text));
	}
	
	/**
	 * Constructs new WithLabelMatcher matching specified text matcher to label of {@link Widget}
	 * on exact match.
	 * 
	 * @param matcher text matcher to match label
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
	 * Matches specified object to text or text matcher provided in constructor of this object.
	 * 
	 * @returns true if label of specified object is matching text or 
	 * text matcher of this object, false otherwise
	 * 
	 */
	@Override
	public boolean matches(Object item) {		
		if ((item instanceof List) || (item instanceof Text) || (item instanceof Button)
				|| (item instanceof Combo) || (item instanceof CCombo) || (item instanceof Spinner)) {
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

